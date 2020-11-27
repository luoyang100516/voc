package com.lizhen.web.controller.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.response.DataResponse;
import com.lizhen.common.response.ResCode;
import com.lizhen.common.util.PageUtils;
import com.lizhen.crm.api.entity.DeProjectBase;
import com.lizhen.crm.api.entity.MerchantBase;
import com.lizhen.crm.api.entity.TestPaper;
import com.lizhen.crm.api.service.DeProjectBaseService;
import com.lizhen.crm.api.service.TestPaperService;
import com.lizhen.web.util.UserUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 企业项目基础表
 *
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-09-06 16:22:25
 */
@RestController
@RequestMapping("/project")
public class DeProjectBaseController {
    @Reference
    private DeProjectBaseService deProjectBaseService;
    @Reference
    private TestPaperService testPaperService;


    /**
     * 保存项目
     */
    @RequestMapping("/addProject")
    public DataResponse addProject(DeProjectBase deProjectBase){
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        deProjectBase.setMerchantId(merchantBase.getId());
        deProjectBaseService.addProject(deProjectBase);
        return  new DataResponse();
    }
    /**
     * 校验编辑key
     */
    @RequestMapping("/checkKey")
    public DataResponse checkKey(String key){
        if (deProjectBaseService.checkKey(key)) {
            return  new DataResponse();
        }else{
            return  new DataResponse().setSuccess(false).setCode(ResCode.FAIL.getValue()).setMessage("更新项目密码错误！");
        }
    }

    /**
     * 更新项目
     */
    @RequestMapping("/updateProject")
    public DataResponse updateProject(DeProjectBase deProjectBase){
        deProjectBaseService.updateProject(deProjectBase);
        return  new DataResponse();
    }

    /**
     * 删除项目
     */
    @RequestMapping("/deleteProject")
    public DataResponse deleteProject(DeProjectBase deProjectBase){
//        String updateKey = deProjectBase.getUpdateKey();
//        if (deProjectBaseService.checkKey(updateKey)) {
        DeProjectBase projectBase = new DeProjectBase();
        projectBase.setId(deProjectBase.getId());
        projectBase.setStatus(2);
        deProjectBaseService.updateById(projectBase);
        return  new DataResponse();
//        }else{
//            return  new DataResponse().setSuccess(false).setCode(ResCode.FAIL.getValue()).setMessage("更新项目密码错误！");
//        }
    }

    /**
     * 列表
     */
    @RequestMapping("/projectList")
    public DataResponse projectList(RequestBase requestBase){
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        requestBase.setMerchantId(merchantBase.getId());
        PageUtils page = deProjectBaseService.projectList(requestBase);
        return new DataResponse().setData(page);
    }

    /**
     * 项目详情
     */
    @RequestMapping("/getProjectDetail")
    public DataResponse getProjectDetail(RequestBase requestBase){
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        requestBase.setMerchantId(merchantBase.getId());
        DeProjectBase projectBase = deProjectBaseService.getProjectDetail(requestBase);
        return  new DataResponse().setData(projectBase);
    }


    /**
     * 试卷列表
     */
    @RequestMapping("/getPaperList")
    public DataResponse getPaperList(RequestBase requestBase){
        requestBase.setStatus(2);
        PageUtils page = testPaperService.queryPage(requestBase);
        return new DataResponse().setData(page);
    }

    /**
     * 添加项目试卷
     */
    @RequestMapping("/addProjectPaper")
    public DataResponse addProjectPaper(RequestBase requestBase){
        deProjectBaseService.addProjectPaper(requestBase);
        return  new DataResponse();
    }

    /**
     * 修改项目试卷
     */
    @RequestMapping("/updateProjectPaper")
    public DataResponse updateProjectPaper(RequestBase requestBase){
        deProjectBaseService.updateProjectPaper(requestBase);
        return  new DataResponse();
    }


    /**
     * 项目试卷列表
     */
    @RequestMapping("/getProjectPaperList")
    public DataResponse getProjectPaperList(RequestBase requestBase){
        List<TestPaper> list = deProjectBaseService.queryProjectPaper(requestBase);
        return new DataResponse().setData(list);
    }


    /**
     * 课程试卷列表
     */
    @RequestMapping("/getLessonPaperList")
    public DataResponse getLessonPaperList(RequestBase requestBase){
        List<TestPaper> list = deProjectBaseService.queryLessonPaper(requestBase);
        return new DataResponse().setData(list);
    }




}
