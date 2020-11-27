package com.lizhen.crm.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lizhen.common.request.RequestBase;
import com.lizhen.crm.api.entity.DeDepartment;

import java.util.List;

/**
 * 企业部门表
 *
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-27 17:04:21
 */
public interface DeDepartmentService extends IService<DeDepartment> {

    List<DeDepartment> getAll(RequestBase requestBase);
    List<DeDepartment> getSortList(RequestBase requestBase);
    void updateDepartment(DeDepartment deDepartment);
}

