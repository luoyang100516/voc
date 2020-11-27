package com.lizhen.crm.kernel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lizhen.crm.api.entity.DeClassLesson;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * 企业选课程表
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-09-05 01:04:43
 */
@Mapper
public interface DeClassLessonMapper extends BaseMapper<DeClassLesson> {
    Set<Integer> selectLessonIds(@Param("merchantId") Integer merchantId);
}
