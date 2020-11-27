package com.lizhen.crm.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.util.PageUtils;
import com.lizhen.crm.api.entity2.MgClassLesson;

import java.util.List;

/**
 * 
 *
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-18 23:04:52
 */
public interface MgClassLessonService extends IService<MgClassLesson> {

    PageUtils queryPage(RequestBase requestBase);

    List<MgClassLesson> searchLesson(RequestBase requestBase);

    void addLesson(MgClassLesson mgClassLesson);

    void updateLesson(MgClassLesson mgClassLesson);

    MgClassLesson getClassLesson(Integer id);
}

