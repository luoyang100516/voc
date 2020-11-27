package com.lizhen.web.controller.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.response.DataResponse;
import com.lizhen.common.util.PageUtils;
import com.lizhen.crm.api.entity.DeClassLesson;
import com.lizhen.crm.api.entity.MerchantBase;
import com.lizhen.crm.api.service.DeClassLessonService;
import com.lizhen.crm.api.service.MgClassChapterService;
import com.lizhen.crm.api.service.MgClassLessonService;
import com.lizhen.web.util.UserUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 *
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-18 23:04:52
 */
@RestController
@RequestMapping("/class")
public class DeClassController {
    @Reference
    private MgClassLessonService mgClassLessonService;
    @Reference
    private DeClassLessonService deClassLessonService;
    @Reference
    private MgClassChapterService mgClassChapterService;

    /**
     * 选择课程列表
     */
    @RequestMapping("/searchLesson")
    public DataResponse searchLesson(RequestBase requestBase){
        requestBase.setStatus(1);
        PageUtils page = mgClassLessonService.queryPage(requestBase);
        return new DataResponse().setData(page);
    }
    /**
     * 选择课程
     */
    @RequestMapping("/selectLesson")
    public DataResponse selectLesson(RequestBase requestBase){
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        requestBase.setMerchantId(merchantBase.getId());
        deClassLessonService.addMerchantLesson(requestBase);
        return new DataResponse();
    }
    /**
     * 课程详情
     */
    @RequestMapping("/getLessonDetail")
    public DataResponse getLessonDetail(RequestBase requestBase){
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        requestBase.setMerchantId(merchantBase.getId());
        DeClassLesson deClassLesson = deClassLessonService.getLessonDetail(requestBase);
        return new DataResponse().setData(deClassLesson);
    }
    /**
     * 章节列表
     */
    @RequestMapping("/chapterList")
    public DataResponse chapterList(RequestBase requestBase){
        requestBase.setId(requestBase.getLessonId());
        PageUtils page = mgClassChapterService.queryPage(requestBase);
        return new DataResponse().setData(page);
    }
    /**
     * 企业课程列表/项目选课
     */
    @RequestMapping("/lessonList")
    public DataResponse lessonList(RequestBase requestBase){
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        requestBase.setMerchantId(merchantBase.getId());
        PageUtils pageUtils = deClassLessonService.queryPage(requestBase);
        return new DataResponse().setData(pageUtils);
    }
    /**
     * 编辑企业课程
     */
    @RequestMapping("/updateLesson")
    public DataResponse updateLesson(DeClassLesson deClassLesson){
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        deClassLesson.setMerchantId(merchantBase.getId());
        deClassLessonService.updateLesson(deClassLesson);
        return new DataResponse();
    }
    /**
     * 删除企业课程
     */
    @RequestMapping("/deleteLesson")
    public DataResponse deleteLesson(DeClassLesson deClassLesson){
        deClassLesson.setStatus(2);
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        deClassLesson.setMerchantId(merchantBase.getId());
        deClassLessonService.updateLesson(deClassLesson);
        return new DataResponse();
    }

}
