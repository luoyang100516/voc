package com.lizhen.web.controller.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.response.DataResponse;
import com.lizhen.crm.api.dto.StaffExamQuestionDTO;
import com.lizhen.crm.api.dto.StaffExamResultDTO;
import com.lizhen.crm.api.entity.*;
import com.lizhen.crm.api.service.*;
import com.lizhen.web.util.UserUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lesson")
public class LessonController {


    @Reference
    private DeProjectBaseService deProjectBaseService;
    @Reference
    private DeClassLessonService deClassLessonService;
    @Reference
    private StViewRecordService stViewRecordService;
    @Reference
    private StPaperAnswerService stPaperAnswerService;
    @Reference
    private TestPaperService testPaperService;

    /**
     * 项目列表
     */
    @RequestMapping("/projectList")
    public DataResponse getProjectList(){
        DeStaff staff = UserUtil.getCurrentStaff();
        List<DeProjectBase> projectList = deProjectBaseService.getProjectList(staff);
        return  new DataResponse().setData(projectList);
    }

    /**
     * 课程详情
     */
    @RequestMapping("/lessonDetail")
    public DataResponse getLessonDetail(RequestBase requestBase){
        DeStaff staff = UserUtil.getCurrentStaff();
        requestBase.setMerchantId(staff.getMerchantId());
        requestBase.setStaffId(staff.getId());
        Map<String,Object> lessonDetail = deClassLessonService.getStaffLessonDetail(requestBase);
        return  new DataResponse().setData(lessonDetail);
    }

    @RequestMapping("/getVideo")
    public DataResponse getVideo(RequestBase requestBase) throws IOException {
        String url = deClassLessonService.getChapterVideo(requestBase);
        return  new DataResponse().setData(url);
    }

    @RequestMapping("/paperList")
    public DataResponse paperList(){
        DeStaff staff = UserUtil.getCurrentStaff();
        List<TestPaper> papers = deClassLessonService.paperList(staff.getId());
        return  new DataResponse().setData(papers);
    }


//    @RequestMapping("/paperList")
////    public DataResponse paperList(RequestBase requestBase){
////        DeStaff staff = UserUtil.getCurrentStaff();
////        requestBase.setId(staff.getId());
////        PageUtils page = deClassLessonService.paperList(requestBase);
////        return  new DataResponse().setData(page);
////    }


    @RequestMapping("/addViewRecord")
    public DataResponse addViewRecord(RequestBase requestBase){
        DeStaff staff = UserUtil.getCurrentStaff();
        StViewRecord record = new StViewRecord();
        record.setStaffId(staff.getId());
        record.setChapterId(requestBase.getId());
        record.setVideoTime(requestBase.getVideoTime());
        stViewRecordService.save(record);
        return  new DataResponse();
    }
    @RequestMapping("/getViewRecord")
    public DataResponse getViewRecord(RequestBase requestBase){
        DeStaff staff = UserUtil.getCurrentStaff();
        requestBase.setStaffId(staff.getId());
        StViewRecord viewRecord = stViewRecordService.getViewRecord(requestBase);
        return  new DataResponse().setData(viewRecord);
    }


    @RequestMapping("/getPaperQuestions")
    public DataResponse getPaperQuestions(RequestBase requestBase){
        TestPaper paper = testPaperService.getPaper(requestBase);
        return  new DataResponse().setData(paper);
    }

    @RequestMapping("/addPaperAnswer")
    public DataResponse addPaperAnswer(StPaperAnswer stPaperAnswer){
        DeStaff staff = UserUtil.getCurrentStaff();
        stPaperAnswer.setStaffId(staff.getId());
        stPaperAnswerService.addPaperAnswer(stPaperAnswer);
        return  new DataResponse();
    }

//    @RequestMapping("/updatePaperAnswer")
//    public DataResponse updatePaperAnswer(StPaperAnswer stPaperAnswer){
//        DeStaff staff = UserUtil.getCurrentStaff();
//        stPaperAnswer.setStaffId(staff.getId());
//        stPaperAnswerService.addPaperAnswer(stPaperAnswer);
//        return  new DataResponse();
//    }
    /**
     * 获取员工答题详情
     */
    @RequestMapping("/getExamResultDetail")
    public DataResponse getExamResultDetail(StaffExamResultDTO requestBase){
        DeStaff staff = UserUtil.getCurrentStaff();
        requestBase.setStaffId(staff.getId());
        List<StaffExamQuestionDTO> list = testPaperService.getStaffExamResultDetail(requestBase);
        return new DataResponse().setData(list);
    }

}
