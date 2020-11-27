package com.lizhen.crm.biz.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.response.DataResponse;
import com.lizhen.common.util.*;
import com.lizhen.crm.api.dto.PasswordDto;
import com.lizhen.crm.api.dto.StClockInRecordDTO;
import com.lizhen.crm.api.dto.StLearnRecordLessonDTO;
import com.lizhen.crm.api.dto.StLearnRecordProjectDTO;
import com.lizhen.crm.api.entity.DeDepartment;
import com.lizhen.crm.api.entity.DeMerchant;
import com.lizhen.crm.api.entity.DeStaff;
import com.lizhen.crm.api.service.DeStaffService;
import com.lizhen.crm.kernel.dao.DeMerchantMapper;
import com.lizhen.crm.kernel.dao.DeStaffMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
public class DeStaffServiceImpl extends ServiceImpl<DeStaffMapper, DeStaff> implements DeStaffService {

    @Autowired
    private DeMerchantMapper deMerchantMapper;

    @Override
    public PageUtils queryPage(RequestBase requestBase) {
        LambdaQueryWrapper<DeStaff> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(DeStaff::getMerchantId,requestBase.getMerchantId())
//                .eq(DeStaff::getStatus,1)
                .like(StringUtil.isNotEmpty(requestBase.getName()),DeStaff::getName,requestBase.getName())
                .like(StringUtil.isNotEmpty(requestBase.getPhone()),DeStaff::getPhone,requestBase.getPhone())
                .like(StringUtil.isNotEmpty(requestBase.getWechat()),DeStaff::getWechat,requestBase.getWechat())
                .like(StringUtil.isNotEmpty(requestBase.getDepartment()),DeStaff::getDepartmentName,requestBase.getDepartment())
                .like(StringUtil.isNotEmpty(requestBase.getIdCard()),DeStaff::getIdCard,requestBase.getIdCard());
        IPage<DeStaff> page = this.page(
                new Query<DeStaff>().getPage(requestBase),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public DeStaff getInfo(DeStaff deStaff) {
        LambdaQueryWrapper<DeStaff> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(DeStaff::getMerchantId,deStaff.getMerchantId()).eq(DeStaff::getId,deStaff.getId());
        return this.getOne(lambdaQueryWrapper);
    }

    @Override
    public void updateStaff(DeStaff deStaff) {
        LambdaQueryWrapper<DeStaff> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(DeStaff::getId,deStaff.getId())
                .eq(DeStaff::getMerchantId,deStaff.getMerchantId());
        this.update(deStaff,lambdaQueryWrapper);
    }

    @Override
    public void updateStaffs(DeDepartment deDepartment) {
        LambdaUpdateWrapper<DeStaff> wrapper = new LambdaUpdateWrapper();
        wrapper.eq(DeStaff::getMerchantId,deDepartment.getMerchantId())
                .eq(DeStaff::getDepartmentId,deDepartment.getId())
                .set(DeStaff::getDepartmentName,deDepartment.getName());
        this.update(wrapper);
    }

    @Override
    public List<DeStaff> searchStaff(DeStaff deStaff) {
        LambdaQueryWrapper<DeStaff> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(DeStaff::getMerchantId,deStaff.getMerchantId())
                .eq(DeStaff::getStatus,1)
                .like(StringUtil.isNotEmpty(deStaff.getName()), DeStaff::getName,deStaff.getName())
                .like(deStaff.getDepartmentId()!=null, DeStaff::getDepartmentId,deStaff.getDepartmentId());
        return this.list(lambdaQueryWrapper);
    }





    @Override
    public DataResponse login(DeStaff deStaff ) throws Exception {
        DataResponse  response = new DataResponse().setCode(500).setSuccess(false);
        if (StringUtil.isEmpty(deStaff.getAccount())) {
            return response;
        }
        DeMerchant merchant = deMerchantMapper.getMerchant(deStaff.getUrl());
        if (merchant == null) {
            return response.setMessage("未查询到该企业");
        }
        LambdaQueryWrapper<DeStaff> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.and(wrapper -> {
                              wrapper.eq(DeStaff::getPhone,deStaff.getAccount())
                                     .or().eq(DeStaff::getIdCard,deStaff.getAccount())
                                     .or().eq(DeStaff::getSocialNo,deStaff.getAccount());
                          }).eq(DeStaff::getMerchantId,merchant.getMerchantId());
        DeStaff staff = this.getOne(lambdaQueryWrapper);
        if (staff != null) {
            if(staff.getStatus()==1 ){
                String pd = AesEncryptUtil.desEncrypt(staff.getPassword()).replace("\0","");
                if( pd.equals(deStaff.getPassword()) ){
                    Map<String,Object> map = new HashMap<>();
                    map.put("token",createToken(staff));
                    map.put("name",staff.getName());
                    map.put("id",staff.getId());
                    map.put("merchantId",staff.getMerchantId());
                    map.put("headImage",staff.getHeadImage());
                    map.put("phone",staff.getPhone());
                    map.put("idCard",staff.getIdCard());
                    response.setData(map);
                    response.setCode(200);
                    response.setSuccess(true);
                }else{
                    response.setMessage("密码错误！");
                }
            }else{
                response.setMessage("账户已被禁用！");
            }
        }else{
            response.setMessage("账号不存在！");
        }
        return response ;
    }

    @Override
    public Boolean updatePassword(DeStaff deStaff,PasswordDto passwordDto) throws Exception {
        String pd = AesEncryptUtil.desEncrypt(deStaff.getPassword()).replace("\0","");
        if( pd.equals(passwordDto.getOldPassword()) ){
            deStaff.setPassword(AesEncryptUtil.encrypt(passwordDto.getNewPassword()));
            this.updateById(deStaff);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public DataResponse getSignList(RequestBase requestBase) {
        PageUtil<StClockInRecordDTO> page = new PageUtil<>();
        int pageNum = requestBase.getCurrPage();
        int pageSize = requestBase.getPageSize();
        requestBase.setCurrPage((pageNum-1)*pageSize);
        List<StClockInRecordDTO> list = this.baseMapper.getSignList(requestBase);
        int count = this.baseMapper.getSignListCount(requestBase);
        page.setPageTotal(count);
        page.setPageData(list);
        page.setPageNumber(pageNum);
        page.setPageSize(pageSize);
        return new DataResponse().setData(page);
    }

    @Override
    public List<LinkedHashMap<String, Object>> getSignList(StClockInRecordDTO recordDTO) {
        return this.baseMapper.getExportSignList(recordDTO);
    }

    @Override
    public DataResponse getLearnList(RequestBase requestBase) {
        PageUtil<StLearnRecordProjectDTO> page = new PageUtil<>();
        int pageNum = requestBase.getCurrPage();
        int pageSize = requestBase.getPageSize();
        requestBase.setCurrPage((pageNum-1)*pageSize);
        List<StLearnRecordProjectDTO> list = this.baseMapper.getLearnList(requestBase);
        int count = this.baseMapper.getLearnListCount(requestBase);
        page.setPageTotal(count);
        page.setPageData(list);
        page.setPageNumber(pageNum);
        page.setPageSize(pageSize);
        return new DataResponse().setData(page);
    }

    @Override
    public DataResponse getStaffLearnList(RequestBase requestBase) {
        List<StLearnRecordLessonDTO> list = this.baseMapper.getStaffLearnList(requestBase);
        return new DataResponse().setData(list);
    }

    @Override
    public List<LinkedHashMap<String, Object>> getLearnList(StLearnRecordProjectDTO recordProjectDTO) {
        return this.baseMapper.getExportLearnList(recordProjectDTO);
    }

    @Override
    public List<LinkedHashMap<String, Object>> getLearnDetailList(Integer merchantId) {
        return this.baseMapper.getLearnDetailList(merchantId);
    }


    public String  createToken( DeStaff deStaff  ){
        String jsonString = JSONObject.toJSONString(deStaff);//将java对象转换为jsonString
        String sign = JwtUtil.sign( deStaff.getAccount() , String.valueOf(System.currentTimeMillis()), jsonString );
        System.out.println("=========token========"+ sign);
        return sign;
    }

}