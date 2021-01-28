package com.lizhen.web.controller.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lizhen.common.response.DataResponse;
import com.lizhen.common.response.ResCode;
import com.lizhen.common.util.QRCodeUtil;
import com.lizhen.common.util.UploadUtil;
import com.lizhen.crm.api.entity.DeMerchant;
import com.lizhen.crm.api.entity.MerchantBase;
import com.lizhen.crm.api.service.DeMerchantService;
import com.lizhen.web.util.UserUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;


/**
 * 企业配置信息表
 *
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-27 17:04:21
 */
@RestController
@RequestMapping("/merchant")
public class DeMerchantController {

    @Reference
    private DeMerchantService deMerchantService;

    /**
     * 信息
     */
    @RequestMapping("/info")
    public DataResponse info(){
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
		DeMerchant deMerchant = deMerchantService.getInfo(merchantBase.getId());
        return new DataResponse().setData( deMerchant);
    }

    /**
     * 更新小程序二维码信息
     */
    @RequestMapping("/updateQrCode")
    public DataResponse updateQrCode() throws IOException {
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        InputStream qrStream = QRCodeUtil.getQR(merchantBase.getAccessParameters());
        if (qrStream != null) {
            String qrCode = UploadUtil.uploadImage(qrStream);
            DeMerchant deMerchant = new DeMerchant();
            deMerchant.setQrCode(qrCode);
            deMerchant.setMerchantId(merchantBase.getId());
            deMerchantService.updateMerchant(deMerchant);
            return new DataResponse().setData(qrCode);
        }
        return new DataResponse().setResponse(false,"失败",ResCode.FAIL.getValue());
    }

//    /**
//     * 保存
//     */
//    @RequestMapping("/save")
//    public DataResponse save(DeMerchant deMerchant){
//        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
//        deMerchant.setMerchantId(merchantBase.getId());
//		deMerchantService.save(deMerchant);
//        return new DataResponse();
//    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public DataResponse update(DeMerchant deMerchant){
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        deMerchant.setMerchantId(merchantBase.getId());
		deMerchantService.updateMerchant(deMerchant);
        return new DataResponse();
    }

}
