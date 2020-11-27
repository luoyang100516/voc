package com.lizhen.crm.biz.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lizhen.common.response.DataResponse;
import com.lizhen.common.response.ResCode;
import com.lizhen.common.util.AesEncryptUtil;
import com.lizhen.common.util.JwtUtil;
import com.lizhen.common.util.PageUtil;
import com.lizhen.crm.api.dto.MerchantOperatorDTO;
import com.lizhen.crm.api.entity.*;
import com.lizhen.crm.api.service.MerchantService;
import com.lizhen.crm.kernel.dao.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantBaseMapper merchantBaseMapper;
//    @Autowired
//    private OperatorBaseMapper operatorBaseMapper;
    @Autowired
    private DeDepartmentMapper deDepartmentMapper;
    @Autowired
    private DeMerchantMapper deMerchantMapper;

    @Autowired
    DeProjectBaseMapper deProjectBaseMapper;
    @Autowired
    DeClassLessonMapper deClassLessonMapper;


    @Transactional
    @Override
    public DataResponse addMerchant(MerchantOperatorDTO merchantOperatorDTO) {
        DataResponse response = new DataResponse();
        try{
            MerchantBase merchantBase = new MerchantBase();
//            OperatorBase operatorBase = new OperatorBase();
            BeanUtils.copyProperties(merchantOperatorDTO,merchantBase);
//            BeanUtils.copyProperties(merchantOperatorDTO,operatorBase);
            merchantBase.setPassword(AesEncryptUtil.encrypt(merchantBase.getPassword()));
            merchantBaseMapper.save(merchantBase);

            DeDepartment department = new DeDepartment();
            department.setName(merchantBase.getMerchantName());
            department.setMerchantId(merchantBase.getId());
            deDepartmentMapper.insert(department);

            DeMerchant deMerchant = new DeMerchant();
            deMerchant.setPlatName(merchantBase.getMerchantName());
            deMerchant.setMerchantId(merchantBase.getId());
            deMerchantMapper.insert(deMerchant);
//            operatorBase.setPassword(AesEncryptUtil.encrypt(operatorBase.getPassword()));
//            operatorBase.setMerchantId(merchantBase.getId());
//            operatorBaseMapper.save(operatorBase);
        }catch (Exception e){
            log.error("addMerchant:",e);
            response.setMessageAndSuccess(false,"操作失败");
            response.setCode(ResCode.FAIL.getValue());
        }
        return response;
    }

    @Override
    public PageUtil<MerchantOperatorDTO> getMerchantList(MerchantOperatorDTO merchantOperatorDTO) {
        PageUtil<MerchantOperatorDTO> page = new PageUtil<>();
        int pageNum = merchantOperatorDTO.getCurrPage();
        int pageSize = merchantOperatorDTO.getPageSize();
        merchantOperatorDTO.setCurrPage((pageNum-1)*pageSize);
        List<MerchantOperatorDTO> list = merchantBaseMapper.getMerchantList(merchantOperatorDTO);
        int count = merchantBaseMapper.getMerchantListCount(merchantOperatorDTO);
        page.setPageTotal(count);
        page.setPageData(list);
        page.setPageNumber(pageNum);
        page.setPageSize(pageSize);
        return page;
    }

    @Override
    public DataResponse getMerchantDetail(MerchantOperatorDTO merchantOperatorDTO) {
        DataResponse response = new DataResponse();
        try{
            MerchantBase detail = merchantBaseMapper.getMerchantDetail(merchantOperatorDTO);
            detail.setPassword(null);
            response.setData(detail);
        }catch (Exception e){
            log.error("addMerchant:",e);
            response.setMessageAndSuccess(false,"操作失败");
            response.setCode(ResCode.FAIL.getValue());
        }
        return response;
    }

    @Override
    public DataResponse updateMerchant(MerchantOperatorDTO merchantOperatorDTO) {
        DataResponse response = new DataResponse();
        try{
            MerchantBase merchantBase = new MerchantBase();
            BeanUtils.copyProperties(merchantOperatorDTO,merchantBase);
            if(merchantBase.getPassword()!=null){
                merchantBase.setPassword(AesEncryptUtil.encrypt(merchantBase.getPassword()));
            }
            merchantBaseMapper.update(merchantBase);
        }catch (Exception e){
            log.error("updateMerchant:",e);
            response.setMessageAndSuccess(false,"操作失败");
            response.setCode(ResCode.FAIL.getValue());
        }
        return response;
    }



    @Override
    public DataResponse login( MerchantOperatorDTO merchantOperatorDTO ) throws Exception {
        DataResponse  response = new DataResponse().setCode(500);
        MerchantBase merchantBase = merchantBaseMapper.getMerchantByAccount(merchantOperatorDTO);
        if (merchantBase != null) {
            if(merchantBase.getStatus()==1 ){
                String pd = AesEncryptUtil.desEncrypt(merchantBase.getPassword()).replace("\0","");
                if( pd.equals(merchantOperatorDTO.getPassword()) ){
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("token",createToken(merchantBase));
                    map.put("name",merchantBase.getOperatorName());
                    map.put("id",merchantBase.getId());
                    LambdaQueryWrapper<DeMerchant> queryWrapper = new LambdaQueryWrapper();
                    queryWrapper.eq(DeMerchant::getMerchantId,merchantBase.getId());
                    DeMerchant merchant = deMerchantMapper.selectOne(queryWrapper);
                    map.put("info",merchant);
                    response.setData(map);
                    response.setCode(200);
                }else{
                    response.setMessage("密码错误！");
                    response.setSuccess(false);
                }
            }else{
                response.setMessage("账户已被禁用！");
                response.setSuccess(false);
            }
        }else{
            response.setMessage("账号不存在！");
            response.setSuccess(false);
        }

        return response ;
    }

    @Override
    public DataResponse getStaffIndexInfo(String url) {
        if (url == null) {
            return new DataResponse().setCode(500).setSuccess(false).setMessage("无地址");
        }else{
            DeMerchant merchant = deMerchantMapper.getMerchant(url);
            LambdaQueryWrapper<DeProjectBase> queryWrapper = new LambdaQueryWrapper();
            queryWrapper.eq(DeProjectBase::getMerchantId,merchant.getMerchantId());
            List<DeProjectBase> projectBaseList = deProjectBaseMapper.selectList(queryWrapper);
            projectBaseList.forEach(projectBase -> {
                List<Integer> lessonIds = this.deProjectBaseMapper.getLessonRelation(projectBase.getId());
                if (lessonIds.size()!=0){
                    List<DeClassLesson> lessonList = deClassLessonMapper.selectBatchIds(lessonIds);
                    projectBase.setLessonList(lessonList);
                }
            });
            JSONObject object = new JSONObject();
            object.put("merchantInfo",merchant);
            object.put("projectList",projectBaseList);
            return new DataResponse().setData(object);
        }
    }

    @Override
    public DataResponse getMerchantIndexInfo(String url){
        if (url == null) {
            return new DataResponse().setCode(500).setSuccess(false).setMessage("无地址");
        }else{
            DeMerchant merchant = deMerchantMapper.getMerchant(url);
            return new DataResponse().setData(merchant);
        }
    }

    public String  createToken( MerchantBase merchantBase  ){
        String jsonString = JSONObject.toJSONString(merchantBase);//将java对象转换为jsonString
        String sign = JwtUtil.sign( merchantBase.getAccount() , String.valueOf(System.currentTimeMillis()), jsonString );
        System.out.println("=========token========"+ sign);
        return sign;
    }

}
