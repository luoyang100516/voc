package com.lizhen.crm.api.service;
import com.lizhen.common.response.DataResponse;

import java.text.ParseException;
import java.util.Map;

/**
 * Created by xsj on 2019/9/10.
 */

public interface ScheduleTaskService {
    public void startScheduleTask  ( ) throws ParseException;
    public void startTaskStorage  ( ) throws ParseException;
}
