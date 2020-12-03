package com.lizhen.crm.kernel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lizhen.crm.api.dto.StProjectDTO;
import com.lizhen.crm.api.dto.StaffNFC;
import com.lizhen.crm.api.entity.StClockInRecord;
import com.lizhen.crm.api.entity2.MgClassChapter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学员打卡记录表
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-09-11 20:57:37
 */
@Mapper
public interface StClockInRecordMapper extends BaseMapper<StClockInRecord> {

    Integer getStaffByNFC(StaffNFC staffNFC);

    void saveStaffNFC(StClockInRecord record);

    List<StProjectDTO> getStaffProject();

    List<StProjectDTO> getStaffProject2();

    List<MgClassChapter> getProjectChapter(@Param("projectId")Integer projectId);

    Integer getStaffLastChapter(@Param("staffId")Integer staffId);
}
