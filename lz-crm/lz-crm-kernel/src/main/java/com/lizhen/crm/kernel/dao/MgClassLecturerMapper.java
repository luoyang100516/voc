package com.lizhen.crm.kernel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lizhen.crm.api.entity2.MgClassLecturer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-18 23:04:52
 */
@Mapper
public interface MgClassLecturerMapper extends BaseMapper<MgClassLecturer> {

    List<MgClassLecturer> getAllLecturer();

    String getLastCode();
}
