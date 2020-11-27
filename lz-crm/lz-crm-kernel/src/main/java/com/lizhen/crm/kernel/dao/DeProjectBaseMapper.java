package com.lizhen.crm.kernel.dao;

import com.lizhen.crm.api.entity.DeProjectBase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 企业项目基础表
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-09-06 16:22:25
 */
@Mapper
public interface DeProjectBaseMapper extends BaseMapper<DeProjectBase> {

    String getUpdateKey();

    int insertPaperRelation(Integer id, List<Integer> ids);
    List<Integer> getPaperRelation(@Param("id")Integer id);
    int deletePaperRelation(@Param("id")Integer id);

    int insertLessonRelation(Integer id, List<Integer> ids);
    List<Integer> getLessonRelation(@Param("id")Integer id);
    int deleteLessonRelation(@Param("id")Integer id);

    int insertLessonPaperRelation(Integer projectId, Integer lessonId, List<Integer> ids);
    List<Integer> getLessonPaperRelation(Integer projectId, Integer lessonId);
    int deleteLessonPaperRelation(Integer projectId, Integer lessonId);

    int insertStaffRelation(Integer id, List<Integer> ids);
    List<Integer> getStaffRelation(@Param("id")Integer id);
    int deleteStaffRelation(@Param("id")Integer id);


    List<Integer> getProjectIds(@Param("id")Integer id);
    List<Integer> getPaperIds(Integer id);
}
