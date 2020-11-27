package com.lizhen.crm.kernel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lizhen.common.request.RequestBase;
import com.lizhen.crm.api.dto.ExamResultDTO;
import com.lizhen.crm.api.dto.StaffExamQuestionDTO;
import com.lizhen.crm.api.dto.StaffExamResultDTO;
import com.lizhen.crm.api.entity.StPaperAnswer;
import com.lizhen.crm.api.entity.TestPaper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 试卷表
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-23 17:02:49
 */
@Mapper
public interface TestPaperMapper extends BaseMapper<TestPaper> {

    int getScore(@Param("ids")List<Integer> ids);

    int insertRelation(Integer id, List<Integer> ids);

    int deleteRelation(@Param("id")Integer id);

    List<Integer> getQuestionId(@Param("id")Integer id);

    List<TestPaper> getStaffPaperList(@Param("staffId")Integer staffId);

    List<ExamResultDTO> getExamList(RequestBase requestBase);

    List<StaffExamResultDTO> getProjectPaperResult(StaffExamResultDTO resultDTO);

    List<StaffExamResultDTO> getProjectClassPaperResult(StaffExamResultDTO resultDTO);

    List<LinkedHashMap<String, Object>> getExportProjectPaperResult(StaffExamResultDTO resultDTO);

    List<LinkedHashMap<String, Object>> getExportProjectClassPaperResult(StaffExamResultDTO resultDTO);

    List<StaffExamQuestionDTO> getPaperQuestionList(StaffExamResultDTO resultDTO);

    StPaperAnswer getPaperAnswer(StaffExamResultDTO resultDTO);


}
