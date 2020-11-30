package com.lizhen.crm.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.util.PageUtils;
import com.lizhen.crm.api.entity.DeProjectBase;
import com.lizhen.crm.api.entity.DeStaff;
import com.lizhen.crm.api.entity.TestPaper;

import java.util.List;

/**
 * 企业项目基础表
 *
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-09-06 16:22:25
 */
public interface DeProjectBaseService extends IService<DeProjectBase> {

    void addProject(DeProjectBase projectBase);

    boolean checkKey(String updateKey);

    void updateProject(DeProjectBase projectBase);

    DeProjectBase getProjectDetail(RequestBase requestBase);

    PageUtils projectList(RequestBase requestBase);

    void addProjectPaper(RequestBase requestBase);

//    void updateProjectPaper(RequestBase requestBase);

    List<DeProjectBase> getProjectList(DeStaff deStaff);

    List<TestPaper> queryProjectPaper(RequestBase requestBase);

    List<TestPaper> queryLessonPaper(RequestBase requestBase);
}

