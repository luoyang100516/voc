package com.lizhen.web.controller.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.response.DataResponse;
import com.lizhen.crm.api.dto.ExamResultDTO;
import com.lizhen.crm.api.dto.StaffExamQuestionDTO;
import com.lizhen.crm.api.dto.StaffExamResultDTO;
import com.lizhen.crm.api.entity.MerchantBase;
import com.lizhen.crm.api.service.TestPaperService;
import com.lizhen.crm.api.service.TestQuestionService;
import com.lizhen.web.util.UserUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 企业考试管理
 *
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-23 17:02:49
 */
@RestController
@RequestMapping("/exam")
public class ExamController {
    @Reference
    private TestPaperService testPaperService;
    @Reference
    private TestQuestionService testQuestionService;

    /**
     * 获取统计数据
     */
    @RequestMapping("/getExamList")
    public DataResponse getExamList(RequestBase requestBase){
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        requestBase.setMerchantId(merchantBase.getId());
        List<ExamResultDTO> list = testPaperService.getExamList(requestBase);
        return new DataResponse().setData(list);
    }
    /**
     * 获取试卷成绩列表
     */
    @RequestMapping("/getExamResultList")
    public DataResponse getExamResultList(StaffExamResultDTO requestBase){
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        requestBase.setMerchantId(merchantBase.getId());
        List<StaffExamResultDTO> list = testPaperService.getExamResultList(requestBase);
        return new DataResponse().setData(list);
    }
    /**
     * 获取员工答题详情
     */
    @RequestMapping("/getExamResultDetail")
    public DataResponse getExamResultDetail(StaffExamResultDTO requestBase){
//        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
//        requestBase.setMerchantId(merchantBase.getId());
        List<StaffExamQuestionDTO> list = testPaperService.getStaffExamResultDetail(requestBase);
        return new DataResponse().setData(list);
    }


}
