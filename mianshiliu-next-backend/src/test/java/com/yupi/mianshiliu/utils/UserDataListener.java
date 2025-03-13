package com.yupi.mianshiliu.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.yupi.mianshiliu.model.entity.User;
import com.yupi.mianshiliu.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UserDataListener extends AnalysisEventListener<User> {
    
    private static final int BATCH_SIZE = 5000;
    private final UserService userService;
    private final List<User> currentBuffer = new ArrayList<>(BATCH_SIZE);
    private final ExecutorService executor = Executors.newFixedThreadPool(16); // 新增线程池

    public UserDataListener(UserService userService) {
        this.userService = userService;
    }
    
    @Override
    public void invoke(User user, AnalysisContext context) {
        synchronized (currentBuffer) {
            currentBuffer.add(user);
            if (currentBuffer.size() >= BATCH_SIZE) {
                // 提交当前批次并清理缓冲区
                submitBatch(new ArrayList<>(currentBuffer));
                currentBuffer.clear();
            }
        }
    }

    private void submitBatch(List<User> batch) {
        executor.execute(() -> {
            try {
                userService.saveBatch(batch);
            } catch (Exception e) {
                // 记录失败批次，建议重试或日志记录
                System.err.println("批次保存失败，失败数量：" + batch.size());
                e.printStackTrace();
            }
        });

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
            if (!currentBuffer.isEmpty()) userService.saveBatch(currentBuffer);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
