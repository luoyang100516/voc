package com.lizhen.web.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lizhen.crm.api.service.StClockInRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Slf4j
public class SaticScheduleTask {
    @Reference
    private StClockInRecordService clockInRecordService;
//    @Scheduled(cron = "0/20 * * * * ? ")
    @Scheduled(cron = "0 0 15 * * ?")
    private void startTaskStorage() {
        log.info("--------------进入定时任务抓取打卡员工-----------");
        clockInRecordService.addViewRecord();
        log.info("--------------完成定时任务----------------------");
    }

    @Scheduled(cron = "0 0 21 * * ?")
    private void startTaskStorage2() {
        log.info("--------------进入定时任务抓取打卡员工-----------");
        clockInRecordService.addViewRecord2();
        log.info("--------------完成定时任务----------------------");
    }

}
