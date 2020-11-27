package com.lizhen.crm.kernel.dao;

import com.lizhen.crm.api.dto.ViewRecordDTO;
import com.lizhen.crm.api.entity.StViewRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-09-11 20:57:36
 */
@Mapper
public interface StViewRecordMapper extends BaseMapper<StViewRecord> {
    List<ViewRecordDTO> selectRecordList(@Param("staffId") Integer staffId);
    StViewRecord selectRecord(Integer staffId,Integer chapterId);
    int insertBatch(List<StViewRecord> list);
}
