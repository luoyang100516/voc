package com.lizhen.crm.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.util.PageUtils;
import com.lizhen.crm.api.entity.DeClassLesson;
import com.lizhen.crm.api.entity.TestPaper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 企业选课程表
 *
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-09-05 01:04:43
 */
public interface DeClassLessonService extends IService<DeClassLesson> {

    PageUtils queryPage(RequestBase requestBase);

    void addMerchantLesson(RequestBase requestBase);

    DeClassLesson getLessonDetail(RequestBase requestBase);

    void updateLesson(DeClassLesson deClassLesson);

    Map<String,Object> getStaffLessonDetail(RequestBase requestBase);

    String getChapterVideo(RequestBase requestBase) throws IOException;

//    PageUtils paperList(RequestBase requestBase);

    List<TestPaper> paperList(Integer staffId);
}

