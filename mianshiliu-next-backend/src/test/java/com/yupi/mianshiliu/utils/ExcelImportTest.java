package com.yupi.mianshiliu.utils;

import com.alibaba.excel.EasyExcel;
import com.yupi.mianshiliu.model.entity.User;
import com.yupi.mianshiliu.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional // 测试后自动回滚
public class ExcelImportTest {

    @Autowired
    private UserService userService;

    @Test
    void testMassiveDataImport() throws Exception {
        // 1. 准备测试文件路径
        ClassPathResource resource = new ClassPathResource("百万用户数据导出.xlsx");
        String filePath = resource.getFile().getAbsolutePath();



        // 2. 创建带有批量处理的监听器
        UserDataListener2 listener = new UserDataListener2(userService);
        // 添加时间统计
        long startTime = System.currentTimeMillis();
        // 3. 使用读取模板读取Excel（2000条批量提交）
        EasyExcel.read(filePath, User.class, listener)
                .sheet()
                .headRowNumber(1) // 跳过标题行
                .doRead();
        long endTime = System.currentTimeMillis();
        System.out.printf("数据导入完成，共耗时 %.2f 秒%n", (endTime - startTime)/1000.0);

        // 4. 验证数据总量
//        Assertions.assertEquals(1048575, userService.count(), "数据总量校验失败");
    }
}
