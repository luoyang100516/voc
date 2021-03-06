package com.lizhen.web.controller.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lizhen.common.response.DataResponse;
import com.lizhen.common.util.PinYinUtil;
import com.lizhen.common.util.RequestUtil;
import com.lizhen.crm.api.dto.PasswordDto;
import com.lizhen.crm.api.dto.StaffNFC;
import com.lizhen.crm.api.entity.DeStaff;
import com.lizhen.crm.api.entity.StClockInRecord;
import com.lizhen.crm.api.service.DeStaffService;
import com.lizhen.crm.api.service.MerchantService;
import com.lizhen.crm.api.service.StClockInRecordService;
import com.lizhen.web.util.UserUtil;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class LoginController {

    @Reference
    private DeStaffService deStaffService;

    @Reference
    private StClockInRecordService stClockInRecordService;

    @Reference
    private MerchantService merchantService;

    @SneakyThrows
    @RequestMapping("/login")
    public DataResponse login(DeStaff staff){
        return  deStaffService.login( staff );
    }

    @RequestMapping("/checkin")
    public DataResponse checkin(HttpServletRequest request){
        DeStaff staff = UserUtil.getCurrentStaff();
        StClockInRecord record = new StClockInRecord();
        record.setStaffId(staff.getId());
        record.setIpUrl(RequestUtil.getIpAddress(request));
        stClockInRecordService.save( record );
        return  new DataResponse();
    }

    @RequestMapping("/changePassword")
    public DataResponse changePassword(PasswordDto passwordDto) throws Exception {
        DeStaff staff = UserUtil.getCurrentStaff();
        boolean res = deStaffService.updatePassword( staff,passwordDto );
        if(res){
            return  new DataResponse();
        }else{
            return  new DataResponse().setMessage("原密码错误").setSuccess(false).setCode(500);
        }
    }
    @RequestMapping("/updateStaff")
    public DataResponse updateStaff(DeStaff staffDTO){
        DeStaff staff = UserUtil.getCurrentStaff();
        if(staffDTO.getEmail()!=null){
            staff.setIdCard(staff.getEmail());
        }
        if(staffDTO.getPhone()!=null){
            staff.setIdCard(staff.getPhone());
        }
        if(staffDTO.getName()!=null){
            staff.setName(staffDTO.getName());
            staff.setAccount(PinYinUtil.toPinyin(staffDTO.getName()));
        }
        if(staffDTO.getIdCard()!=null){
            staff.setIdCard(staff.getIdCard());
        }
        if(staffDTO.getHeadImage()!=null){
            staff.setHeadImage(staffDTO.getHeadImage());
        }
        deStaffService.updateById(staff);
        return  new DataResponse();
    }

    @RequestMapping("/getIndexInfo")
    public DataResponse getIndexInfo(String url){
        return  merchantService.getStaffIndexInfo(url);
    }

    @RequestMapping("/addRecord")
    public DataResponse addRecord(@RequestBody StaffNFC staffNFC, HttpServletRequest request){
//        System.out.println(req);
//        System.out.println(staffNFC2);
////        staffNFC = JSONObject.parseObject(req,StaffNFC.class);
        staffNFC.setIpUrl(RequestUtil.getIpAddress(request));
        return  stClockInRecordService.addNFCSignRecord( staffNFC );
    }
}
