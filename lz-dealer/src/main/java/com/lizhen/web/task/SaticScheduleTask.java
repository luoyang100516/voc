package com.lizhen.web.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lizhen.crm.api.service.StClockInRecordService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {
    @Reference
    private StClockInRecordService clockInRecordService;
//    @Scheduled(cron = "0/20 * * * * ? ")
    @Scheduled(cron = "0 0 23 * * ?")
    private void startTaskStorage() {
        clockInRecordService.addViewRecord();
    }

}
