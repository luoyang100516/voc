package com.lizhen.web.WebConfig;

/**
 * Created by xsj on 2019/8/27.
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {
//    @Reference
//    private ScheduleTaskService scheduleTaskService;
////    @Scheduled(cron = "0 0 0 */1 * ?")
//    @Scheduled(cron = "0 0/2 * * * ?")
//    //@Scheduled(cron = "0/20 * * * * ? ")
//    private void configureTasks() {
//        try {
//            System.err.println("执行RFid定时任务时间: " + LocalDateTime.now());
//             scheduleTaskService.startScheduleTask();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
////    @Scheduled(cron = "0 0 0 */1 * ?")
//    @Scheduled(cron = "0 0/2 * * * ?")
//    //@Scheduled(cron = "0/20 * * * * ? ")
//    private void startTaskStorage() throws ParseException {
//         System.err.println("执行盘库定时任务时间: " + LocalDateTime.now());
//         scheduleTaskService.startTaskStorage();
//    }

}
