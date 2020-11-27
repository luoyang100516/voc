package com.lizhen.crm.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lizhen.common.util.PageUtils;
import com.lizhen.common.request.RequestBase;
import com.lizhen.crm.api.entity2.MgClassLecturer;

import java.util.List;

/**
 * 
 *
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-18 23:04:52
 */
public interface MgClassLecturerService extends IService<MgClassLecturer> {

    PageUtils queryPage(RequestBase requestBase);

    List<MgClassLecturer> getAllLecturer();

    void addLecturer(MgClassLecturer mgClassLecturer);
}

