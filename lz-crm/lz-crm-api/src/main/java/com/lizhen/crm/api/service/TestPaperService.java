package com.lizhen.crm.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.util.PageUtils;
import com.lizhen.crm.api.dto.ExamResultDTO;
import com.lizhen.crm.api.dto.StaffExamQuestionDTO;
import com.lizhen.crm.api.dto.StaffExamResultDTO;
import com.lizhen.crm.api.entity.TestPaper;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 试卷表
 *
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-23 17:02:49
 */
public interface TestPaperService extends IService<TestPaper> {

    PageUtils queryPage(RequestBase requestBase);

    void addPaper(TestPaper testPaper);

    void updatePaper(TestPaper testPaper);

    TestPaper getPaper(RequestBase requestBase);

    List<ExamResultDTO> getExamList(RequestBase requestBase);

    List<StaffExamResultDTO> getExamResultList(StaffExamResultDTO resultDTO);

    List<LinkedHashMap<String, Object>> getExportExamResultList(StaffExamResultDTO resultDTO);

    List<StaffExamQuestionDTO>  getStaffExamResultDetail(StaffExamResultDTO resultDTO);
}

