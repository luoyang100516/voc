package com.lizhen.crm.kernel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lizhen.common.request.RequestBase;
import com.lizhen.crm.api.dto.StClockInRecordDTO;
import com.lizhen.crm.api.dto.StLearnRecordLessonDTO;
import com.lizhen.crm.api.dto.StLearnRecordProjectDTO;
import com.lizhen.crm.api.entity.DeStaff;
import org.apache.ibatis.annotations.Mapper;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 员工表
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-27 17:04:21
 */
@Mapper
public interface DeStaffMapper extends BaseMapper<DeStaff> {

    List<StClockInRecordDTO> getSignList(RequestBase requestBase);
    int getSignListCount(RequestBase requestBase);
    List<LinkedHashMap<String, Object>> getExportSignList(StClockInRecordDTO recordDTO);
    List<StLearnRecordLessonDTO> getStaffLearnList(RequestBase requestBase);

    //学员学习记录-项目维度
    List<StLearnRecordProjectDTO> getLearnList(RequestBase requestBase);
    int getLearnListCount(RequestBase requestBase);
    List<LinkedHashMap<String, Object>> getExportLearnList(StLearnRecordProjectDTO recordDTO);

    List<LinkedHashMap<String, Object>> getLearnDetailList(Integer merchantId);

}
