package com.lizhen.web.controller.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lizhen.common.response.DataResponse;
import com.lizhen.common.util.PageUtil;
import com.lizhen.crm.api.dto.MerchantOperatorDTO;
import com.lizhen.crm.api.service.MerchantService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/merchant")
public class MerchantController {

    @Reference
    private MerchantService merchantService;


    @RequestMapping("/addMerchant")
    public DataResponse addMerchant(MerchantOperatorDTO merchantOperatorDTO){
        return  merchantService.addMerchant( merchantOperatorDTO );
    }
    @RequestMapping("/getMerchantList")
    public DataResponse getMerchantList(MerchantOperatorDTO merchantOperatorDTO){
        PageUtil<MerchantOperatorDTO> list = merchantService.getMerchantList( merchantOperatorDTO );
        return  new DataResponse().setData(list);
    }
    @RequestMapping("/getMerchantDetail")
    public DataResponse getMerchantDetail(MerchantOperatorDTO merchantOperatorDTO){
        return  merchantService.getMerchantDetail( merchantOperatorDTO );
    }

    @RequestMapping("/updateMerchant")
    public DataResponse updateMerchant(MerchantOperatorDTO merchantOperatorDTO){
        return  merchantService.updateMerchant( merchantOperatorDTO );
    }



}
