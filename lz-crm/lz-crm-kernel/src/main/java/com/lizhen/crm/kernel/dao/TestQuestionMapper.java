package com.lizhen.crm.kernel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lizhen.crm.api.entity.TestQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 试卷表
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-23 17:02:49
 */
@Mapper
public interface TestQuestionMapper extends BaseMapper<TestQuestion> {

//    int insertRelation(Integer id, List<Integer> ids);
//
//    int deleteRelation(Integer id);
//
//    List<Integer> getQuestionIds(Integer id);

    List<Integer> selectIds(Integer id);
}
