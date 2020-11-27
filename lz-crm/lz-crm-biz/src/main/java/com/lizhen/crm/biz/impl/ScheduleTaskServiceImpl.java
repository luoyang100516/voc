package com.lizhen.crm.biz.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lizhen.crm.api.service.ScheduleTaskService;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xsj on 2019/9/10.
 */

@Service
public class ScheduleTaskServiceImpl implements ScheduleTaskService {


    /**
     *  当前日期是星期几
     */
    public String getWeekOfDate( Date date ) {
        String[] weekDays = { "7", "1", "2", "3", "4", "5", "6" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }


    @Transactional
    @Override
    public void startScheduleTask() throws ParseException {
//        List<RfidWorkBase> RfidWorkBaseList = rfidWorkBaseMapper.getRfidWorkList( new HashedMap() );
//        for (RfidWorkBase WorkBase : RfidWorkBaseList){
//            String workId = WorkBase.getId().toString();
//            Map map = new BeanMap( WorkBase );
//            Integer clearStatus = WorkBase.getClearStatus();
//            String[] clearTimes = WorkBase.getClearTime()!=null ? WorkBase.getClearTime().split(","): new String[1];
//            String[] weekTimes  = WorkBase.getClearWeek()!=null? WorkBase.getClearWeek().split(","): new String[1];
//            String[] monthTimes  = WorkBase.getClearMonth()!=null? WorkBase.getClearMonth().split(","): new String[1];
//            Map<String,Object> mapInfo = new HashedMap();
//            mapInfo.put("factoryName",WorkBase.getFactoryName());
//            mapInfo.put("dlrName",WorkBase.getDealerName());
//            mapInfo.put("status",2);
//            List<RfidBase>   rfidBaseList =  rfidBaseMapper.selectRfidArray(mapInfo);
//            if(WorkBase.getStatus()==1){//启用
//                map.put("status",2);
//                if( clearStatus==1 ){  // 每日清点
//                    if( clearTimes.length >0 ){
//                        updateClearWork(clearTimes ,map ,rfidBaseList,WorkBase ,workId );
//                    }
//                }else if( clearStatus==2){ // 每周清点
//                    String week = getWeekOfDate( new Date() );
//                    if( Arrays.asList(weekTimes).contains(week) ){
//                        if( clearTimes.length >0 ){
//                            updateClearWork(clearTimes ,map ,rfidBaseList,WorkBase , workId);
//                        }
//                    }
//                }else if( clearStatus==3){ //每月清点
//                    Calendar c = Calendar.getInstance();
//                    int datenum=c.get( Calendar.DATE );
//                    if( Arrays.asList(monthTimes).contains(String.valueOf(datenum))){
//                        if( clearTimes.length >0 ){
//                            updateClearWork(clearTimes ,map ,rfidBaseList,WorkBase , workId);
//                        }
//                    }
//                }
//            }
//        }
    }
    @Transactional
    @Override
    public void startTaskStorage() throws ParseException {
//        Map<String,Object> map = new HashedMap();
//        map.put("taskStatus",2);
//        List<TaskBase>  taskBaseList  = taskBaseMapper.getTaskList( map );
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
//        String time = dateFormat.format(new Date());
//     for(TaskBase t : taskBaseList ){
//         Map<String,Object> mapInfo = new HashedMap();
//         Map<String,Object> mapInfo2 = new HashedMap();
//         mapInfo.put("factoryName",t.getFactoryName());
//         mapInfo.put("dlrName",t.getDlrName());
//         mapInfo.put("status",1);
//         mapInfo.put("dataType",1);
//         String time2 = dateFormat.format(t.getTaskDate());
//         if(time.equals(time2)){
//             mapInfo2.put("taskId",t.getId());
//             taskDataBaseMapper.delTaskData(mapInfo2);
//             List<VehicleStock > list = vehicleStockMapper.getVehicleByTime( mapInfo );
//             updateTaskData(list,t);
//             mapInfo.put("dataType",2);
//             List<VehicleStock > list2 = vehicleStockMapper.getVehicleByTime( mapInfo );
//             updateTaskData(list2,t);
//         }
//     }
    }


}
