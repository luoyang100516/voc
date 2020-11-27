package com.lizhen.web.controller.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.response.DataResponse;
import com.lizhen.common.util.JxlExcelUtils;
import com.lizhen.common.util.UploadUtil;
import com.lizhen.crm.api.dto.StClockInRecordDTO;
import com.lizhen.crm.api.dto.StLearnRecordProjectDTO;
import com.lizhen.crm.api.dto.StaffExamResultDTO;
import com.lizhen.crm.api.entity.MerchantBase;
import com.lizhen.crm.api.service.DeStaffService;
import com.lizhen.crm.api.service.TestPaperService;
import com.lizhen.web.util.UserUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping( "/export" )
public class ExportController {
    /**
     * 服务器生成Excel地址
     */
    @Value("${qn.fileLocalUrl}")
    public String fileLocalUrl;

    static List<String> signHeads;

    static List<String> learnHeads;

    static List<String> learnDetailHeads;

    static List<String> examResultHeads;

    @Reference
    private DeStaffService deStaffService;
    @Reference
    private TestPaperService testPaperService;


    /**
     * 学员签到列表
     */
    @RequestMapping("/signList")
    public DataResponse signList(RequestBase requestBase){
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        requestBase.setMerchantId(merchantBase.getId());
        return deStaffService.getSignList(requestBase);
    }
    /**
     * 导出学员签到列表
     * @return
     */
    @RequestMapping("/exportSignList")
    public DataResponse exportSignList(HttpServletResponse response, StClockInRecordDTO recordDTO) throws IOException {
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        recordDTO.setMerchantId(merchantBase.getId());
        List<LinkedHashMap<String, Object>> lis = deStaffService.getSignList(recordDTO);
        String fileName = "学员签到列表"+System.currentTimeMillis();
        JxlExcelUtils.exportexcle(response, fileName, lis, "sheet1", getSignHead(),fileLocalUrl);
        String name = UploadUtil.uploadFile(fileLocalUrl+fileName+".xls",fileName+".xls");
        return new DataResponse().setData(name);
    }

    /**
     * 学员学习记录-项目
     */
    @RequestMapping("/learnList")
    public DataResponse learnList(RequestBase requestBase){
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        requestBase.setMerchantId(merchantBase.getId());
        return deStaffService.getLearnList(requestBase);
    }

    /**
     * 导出学员学习记录-项目
     * @return
     */
    @RequestMapping("/exportLearnList")
    public DataResponse exportLearnList(HttpServletResponse response, StLearnRecordProjectDTO recordDTO) throws IOException {
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        recordDTO.setMerchantId(merchantBase.getId());
        List<LinkedHashMap<String, Object>> lis = deStaffService.getLearnList(recordDTO);
        String fileName = "学员学习记录-项目"+System.currentTimeMillis();
        JxlExcelUtils.exportexcle(response, fileName, lis, "sheet1", getLearnHead(),fileLocalUrl);
        String name = UploadUtil.uploadFile(fileLocalUrl+fileName+".xls",fileName+".xls");
        return new DataResponse().setData(name);
    }

    /**
     * 学员学习记录-课程
     */
    @RequestMapping("/staffLearnList")
    public DataResponse staffLearnList(RequestBase requestBase){
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        requestBase.setMerchantId(merchantBase.getId());
        return deStaffService.getStaffLearnList(requestBase);
    }
    /**
     * 导出学员学习记录-课程
     * @return
     */
    @RequestMapping("/exportLearnDetailList")
    public DataResponse exportLearnDetailList(HttpServletResponse response) throws IOException {
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        List<LinkedHashMap<String, Object>> lis = deStaffService.getLearnDetailList(merchantBase.getId());
        String fileName = "学员学习记录-课程"+System.currentTimeMillis();
        JxlExcelUtils.exportexcle(response, fileName, lis, "sheet1", getLearnDetailHead(),fileLocalUrl);
        String name = UploadUtil.uploadFile(fileLocalUrl+fileName+".xls",fileName+".xls");
        return new DataResponse().setData(name);
    }
    /**
     * 导出考试成绩
     * @return
     */
    @RequestMapping("/exportExamResultList")
    public DataResponse exportExamResultList(HttpServletResponse response,StaffExamResultDTO requestBase) throws IOException {
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        requestBase.setMerchantId(merchantBase.getId());
        List<LinkedHashMap<String, Object>> lis = testPaperService.getExportExamResultList(requestBase);
        String fileName = "学员成绩清单"+System.currentTimeMillis();
        JxlExcelUtils.exportexcle(response, fileName, lis, "sheet1", getExamResultHead(),fileLocalUrl);
        String name = UploadUtil.uploadFile(fileLocalUrl+fileName+".xls",fileName+".xls");
        return new DataResponse().setData(name);
    }


    public static List<String> getSignHead() {
        if (signHeads == null) {
            String signHeadsStr = "部门,员工,手机号,社保号,身份证,打卡日期";
            signHeads = new ArrayList<>(Arrays.asList(signHeadsStr.split(",")));
        }
        return signHeads;
    }

    public static List<String> getLearnHead() {
        if (learnHeads == null) {
            String signHeadsStr = "项目名称,学员姓名,手机号,社保号,身份证,学习总时长,获得总课时,学习开始时间,学习完成时间";
            learnHeads = new ArrayList<>(Arrays.asList(signHeadsStr.split(",")));
        }
        return learnHeads;
    }

    public static List<String> getLearnDetailHead() {
        if (learnDetailHeads == null) {
            String signHeadsStr = "项目名称,姓名,手机号,社保号,身份证,课程名称,学时,已学时长（分）,课程完成率,完成状态,学习开始时间,学习结束时间";
            learnDetailHeads = new ArrayList<>(Arrays.asList(signHeadsStr.split(",")));
        }
        return learnDetailHeads;
    }

    public static List<String> getExamResultHead() {
        if (examResultHeads == null) {
            String signHeadsStr = "试卷名称,学员编号,学员姓名,身份证号,手机号,社保号,考试成绩,性别,部门,完成考试时间";
            examResultHeads = new ArrayList<>(Arrays.asList(signHeadsStr.split(",")));
        }
        return examResultHeads;
    }
}
