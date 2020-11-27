package com.lizhen.crm.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lizhen.common.response.DataResponse;
import com.lizhen.common.util.PageUtils;
import com.lizhen.crm.api.dto.StaffNFC;
import com.lizhen.crm.api.entity.StClockInRecord;

import java.util.Map;

/**
 * 学员打卡记录表
 *
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-09-11 20:57:37
 */
public interface StClockInRecordService extends IService<StClockInRecord> {

    PageUtils queryPage(Map<String, Object> params);

    void addViewRecord();

    DataResponse addNFCSignRecord(StaffNFC staffNFC);
}

