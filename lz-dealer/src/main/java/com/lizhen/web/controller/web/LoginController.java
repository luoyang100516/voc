package com.lizhen.web.controller.web;
import com.alibaba.dubbo.config.annotation.Reference;
import com.lizhen.common.response.DataResponse;
import com.lizhen.crm.api.dto.MerchantOperatorDTO;
import com.lizhen.crm.api.service.MerchantService;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class LoginController {

    @Reference
    private MerchantService merchantService;

    @SneakyThrows
    @RequestMapping("/login")
    public DataResponse login(MerchantOperatorDTO merchantOperatorDTO){
        return  merchantService.login( merchantOperatorDTO );
    }

    @RequestMapping("/getIndexInfo")
    public DataResponse getIndexInfo(String url){
        return  merchantService.getMerchantIndexInfo(url);
    }

}
