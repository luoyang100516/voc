package com.lizhen.web.controller.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.response.DataResponse;
import com.lizhen.crm.api.entity.DeDepartment;
import com.lizhen.crm.api.entity.MerchantBase;
import com.lizhen.crm.api.service.DeDepartmentService;
import com.lizhen.crm.api.service.DeStaffService;
import com.lizhen.web.util.UserUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 企业部门表
 *
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-27 17:04:21
 */
@RestController
@RequestMapping("/department")
public class DeDepartmentController {
    @Reference
    private DeDepartmentService deDepartmentService;
    @Reference
    private DeStaffService deStaffService;

    /**
     * 列表
     */
    @RequestMapping("/getAll")
    public DataResponse getAll(RequestBase requestBase){
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        requestBase.setId(merchantBase.getId());
        List<DeDepartment> list = deDepartmentService.getAll(requestBase);
        return new DataResponse().setData(list);
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    public DataResponse getSortList(RequestBase requestBase){
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        requestBase.setId(merchantBase.getId());
        List<DeDepartment> page = deDepartmentService.getSortList(requestBase);
        return new DataResponse().setData(page);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public DataResponse save(DeDepartment deDepartment){
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        deDepartment.setMerchantId(merchantBase.getId());
		deDepartmentService.save(deDepartment);
        return new DataResponse();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public DataResponse update(DeDepartment deDepartment){
        return getDataResponse(deDepartment);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public DataResponse delete(DeDepartment deDepartment){
        deDepartment.setStatus(2);
        return getDataResponse(deDepartment);
    }

    @Transactional
    DataResponse getDataResponse(DeDepartment deDepartment) {
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        deDepartment.setMerchantId(merchantBase.getId());
        deDepartmentService.updateDepartment(deDepartment);
        if(deDepartment.getStatus()==null){
            deStaffService.updateStaffs(deDepartment);
        }
        return new DataResponse();
    }

}
