package com.lizhen.crm.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.util.PageUtils;
import com.lizhen.crm.api.entity.StViewRecord;

import java.util.Map;

/**
 * 
 *
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-09-11 20:57:36
 */
public interface StViewRecordService extends IService<StViewRecord> {

    PageUtils queryPage(Map<String, Object> params);

    StViewRecord getViewRecord(RequestBase requestBase);
}

