package com.lizhen.crm.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.response.DataResponse;
import com.lizhen.common.util.PageUtils;
import com.lizhen.crm.api.dto.PasswordDto;
import com.lizhen.crm.api.dto.StClockInRecordDTO;
import com.lizhen.crm.api.dto.StLearnRecordProjectDTO;
import com.lizhen.crm.api.entity.DeDepartment;
import com.lizhen.crm.api.entity.DeStaff;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 员工表
 *
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-27 17:04:21
 */
public interface DeStaffService extends IService<DeStaff> {

    PageUtils queryPage(RequestBase requestBase);
    DeStaff getInfo(DeStaff deStaff);
    void updateStaff(DeStaff deStaff);
    void updateStaffs(DeDepartment deDepartment);
    List<DeStaff> searchStaff(DeStaff deStaff);


    DataResponse login(DeStaff deStaff) throws Exception;
    DataResponse wxLogin(DeStaff deStaff) throws Exception;

    Boolean updatePassword(DeStaff deStaff,PasswordDto passwordDto) throws Exception;

    DataResponse getSignList(RequestBase requestBase);
    /**
     * 导出签到
     * */
    List<LinkedHashMap<String, Object>> getSignList(StClockInRecordDTO recordDTO);
    /**
     * 学习记录
     * */
    DataResponse getLearnList(RequestBase requestBase);
    /**
     * 获取学员项目学习详情
     * */
    DataResponse getStaffLearnList(RequestBase requestBase);
    /**
     * 导出学习汇总-项目
     * */
    List<LinkedHashMap<String, Object>> getLearnList(StLearnRecordProjectDTO recordProjectDTO);
    /**
     * 导出学习详情-课程
     * */
    List<LinkedHashMap<String, Object>> getLearnDetailList(Integer merchantId);

}

