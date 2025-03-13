package com.yupi.mianshiliu.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.yupi.mianshiliu.model.entity.User;
import com.yupi.mianshiliu.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserDataListener1 extends AnalysisEventListener<User> {

    private static final int BATCH_SIZE = 5000;
    private final UserService userService;
    private final List<User> cachedDataList = new ArrayList<>(BATCH_SIZE);

    public UserDataListener1(UserService userService) {
        this.userService = userService;
    }
    
    @Override
    public void invoke(User user, AnalysisContext context) {
        cachedDataList.add(user);
        if (cachedDataList.size() >= BATCH_SIZE) {
            userService.saveBatch(cachedDataList); // 同步保存
            cachedDataList.clear();
        }
    }



    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (!cachedDataList.isEmpty()) {
            userService.saveBatch(cachedDataList); // 同步保存尾部数据
        }
    }

}
