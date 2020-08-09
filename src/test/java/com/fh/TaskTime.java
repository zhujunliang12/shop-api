package com.fh;

import com.fh.common.MailUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@Component
@EnableScheduling
public class TaskTime {

    @Autowired
    private MailUtils mailUtils;

    @Test
    @Scheduled(cron = "0/5 * * * * ? ") // 间隔5秒执行
    public void taskTime() throws Exception {
        System.out.println(new Date( ));
        mailUtils.sendMail("260006856@qq.com", "每5分发送一次", "测试成功了吗？");
    }


}
