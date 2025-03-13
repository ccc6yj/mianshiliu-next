package com.yupi.mianshiliu.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.yupi.mianshiliu.model.entity.User;
import com.yupi.mianshiliu.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

public class UserDataListener2 extends AnalysisEventListener<User> {

    private static final int BATCH_SIZE = 5000;
    private final UserService userService;
    private final Queue<User> buffer = new ConcurrentLinkedQueue<>(); // 改为线程安全队列

    private final List<User> currentBuffer = new ArrayList<>(BATCH_SIZE);
    private final ExecutorService executor = Executors.newFixedThreadPool(16); // 新增线程池
    private final List<CompletableFuture<Void>> futures = new CopyOnWriteArrayList<>(); // 跟踪异步任务

    public UserDataListener2(UserService userService) {
        this.userService = userService;
    }
    
    @Override
    public void invoke(User user, AnalysisContext context) {
        buffer.add(user);
        if (buffer.size() >= BATCH_SIZE) {
            submitBatch();
        }
    }

    private void submitBatch() {
        List<User> batch = new ArrayList<>(BATCH_SIZE);
        synchronized (buffer) { // 仅在此处同步
            while (batch.size() < BATCH_SIZE && !buffer.isEmpty()) {
                batch.add(buffer.poll());
            }
        }
        if (!batch.isEmpty()) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                userService.saveBatch(batch);
            }, executor).exceptionally(ex -> {
                System.err.println("批量保存失败: " + ex.getMessage());
                return null;
            });
            futures.add(future);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 处理剩余数据
        submitBatch();

        // 等待所有异步任务完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .exceptionally(ex -> {
                    System.err.println("最终处理异常: " + ex.getMessage());
                    return null;
                })
                .thenRun(() -> {
                    executor.shutdown();
                    try {
                        if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
                            System.err.println("线程池强制关闭");
                            executor.shutdownNow();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
    }

}
