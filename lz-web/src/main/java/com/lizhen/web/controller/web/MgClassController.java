package com.lizhen.web.controller.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.response.DataResponse;
import com.lizhen.common.util.PageUtils;
import com.lizhen.crm.api.entity.VideoPrefix;
import com.lizhen.crm.api.entity2.MgClassChapter;
import com.lizhen.crm.api.entity2.MgClassLabel;
import com.lizhen.crm.api.entity2.MgClassLecturer;
import com.lizhen.crm.api.entity2.MgClassLesson;
import com.lizhen.crm.api.service.MgClassChapterService;
import com.lizhen.crm.api.service.MgClassLabelService;
import com.lizhen.crm.api.service.MgClassLecturerService;
import com.lizhen.crm.api.service.MgClassLessonService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 
 *
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-18 23:04:52
 */
@RestController
@RequestMapping("/class")
public class MgClassController {
    @Reference
    private MgClassChapterService mgClassChapterService;
    @Reference
    private MgClassLessonService mgClassLessonService;
    @Reference
    private MgClassLabelService mgClassLabelService;
    @Reference
    private MgClassLecturerService mgClassLecturerService;

    /**
     * 课程列表
     */
    @RequestMapping("/lessonList")
    public DataResponse lessonList(RequestBase requestBase){
        PageUtils page = mgClassLessonService.queryPage(requestBase);
        return new DataResponse().setData(page);
    }
    /**
     * 搜索课程
     */
    @RequestMapping("/searchLesson")
    public DataResponse searchLesson(RequestBase requestBase){
        List<MgClassLesson> list = mgClassLessonService.searchLesson(requestBase);
        return new DataResponse().setData(list);
    }

    /**
     * 添加课程
     */
    @RequestMapping("/addLesson")
    public DataResponse addLesson(MgClassLesson mgClassLesson){
        mgClassLessonService.addLesson(mgClassLesson);
        return new DataResponse();
    }

    /**
     * 编辑课程
     */
    @RequestMapping("/updateLesson")
    public DataResponse updateLesson(MgClassLesson mgClassLesson){
        mgClassLessonService.updateLesson(mgClassLesson);
        return new DataResponse();
    }

    /**
     * 获取课程
     */
    @RequestMapping("/getLesson")
    public DataResponse getLesson(MgClassLesson mgClassLesson){
        MgClassLesson res = mgClassLessonService.getClassLesson(mgClassLesson.getId());
        return new DataResponse().setData(res);
    }
    /**
     * 章节列表
     */
    @RequestMapping("/chapterList")
    public DataResponse chapterList(RequestBase requestBase){
        PageUtils page = mgClassChapterService.queryPage(requestBase);
        DataResponse response = new DataResponse();
        response.setData(page);
        return response;
    }

    /**
     * 添加章节
     */
    @RequestMapping("/addChapter")
    public DataResponse addChapter(MgClassChapter mgClassChapter){
        mgClassChapterService.save(mgClassChapter);
        return new DataResponse();
    }
    /**
     * 讲师列表
     */
    @RequestMapping("/lecturerList")
    public DataResponse lecturerList(RequestBase requestBase){
        PageUtils page = mgClassLecturerService.queryPage(requestBase);
        return new DataResponse().setData(page);
    }
    /**
     * 所有讲师列表
     */
    @RequestMapping("/getAllLecturer")
    public DataResponse getAllLecturer(){
        DataResponse response = new DataResponse();
        List<MgClassLecturer> list = mgClassLecturerService.getAllLecturer();
        response.setData(list);
        return response;
    }
    /**
     * 讲师详情
     */
    @RequestMapping("/getLecturer")
    public DataResponse getLecturer(MgClassLecturer mgClassLecturer){
        MgClassLecturer res = mgClassLecturerService.getById(mgClassLecturer.getId());
        return new DataResponse().setData(res);
    }
    /**
     * 添加讲师
     */
    @RequestMapping("/addLecturer")
    public DataResponse addLecturer(MgClassLecturer mgClassLecturer){
        mgClassLecturerService.addLecturer(mgClassLecturer);
        return new DataResponse();
    }
    /**
     * 编辑讲师
     */
    @RequestMapping("/updateLecturer")
    public DataResponse updateLecturer(MgClassLecturer mgClassLecturer){
        mgClassLecturerService.updateById(mgClassLecturer);
        return new DataResponse();
    }
    /**
     * 删除讲师
     */
    @RequestMapping("/deleteLecturer")
    public DataResponse deleteLecturer(MgClassLecturer mgClassLecturer){
        mgClassLecturer.setStatus(2);
        mgClassLecturerService.updateById(mgClassLecturer);
        return new DataResponse();
    }

    /**
     * 标签列表
     */
    @RequestMapping("/labelList")
    public DataResponse labelList(MgClassLabel mgClassLabel){
        List<MgClassLabel> list = mgClassLabelService.getLabelList(mgClassLabel);
        DataResponse response = new DataResponse();
        response.setData(list);
        return response;
    }
    /**
     * 添加标签
     */
    @RequestMapping("/addLabel")
    public DataResponse addLabel(MgClassLabel mgClassLabel){
        mgClassLabelService.save(mgClassLabel);
        return new DataResponse();
    }
    /**
     * 删除标签
     */
    @RequestMapping("/deleteLabel")
    public DataResponse deleteLabel(MgClassLabel mgClassLabel){
        mgClassLabel.setStatus(2);
        mgClassLabelService.updateById(mgClassLabel);
        return new DataResponse();
    }

    /**
     * 获取视频前缀
     */
    @RequestMapping("/getVideoPrefix")
    public DataResponse getVideoPrefix(){
        List<VideoPrefix> list =  mgClassChapterService.getVideoPrefix();
        return new DataResponse().setData(list);
    }






}
