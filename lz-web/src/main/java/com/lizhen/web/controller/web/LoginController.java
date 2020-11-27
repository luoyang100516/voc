package com.lizhen.web.controller.web;
import com.alibaba.dubbo.config.annotation.Reference;
import com.lizhen.common.response.DataResponse;
import com.lizhen.crm.api.entity.UserBase;
import com.lizhen.crm.api.service.LoginService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@CrossOrigin
public class LoginController {

    @Reference
    private LoginService loginService;

    @RequestMapping("/login")
    public DataResponse login(UserBase user){
        return  loginService.login( user );
    }



    @RequestMapping("/getAllMenuList")
    public DataResponse getAllMenuList(@RequestBody Map map){
        return  loginService.getAllMenuList( map );
    }

    @RequestMapping("/updateUserBase")
    public DataResponse updateUserBase(@RequestBody Map map){
        return  loginService.updateUserBase( map );
    }
    @RequestMapping("/updateUser")
    public DataResponse updateUser(@RequestBody Map map){
        return  loginService.updateUserBase( map );
    }

    @RequestMapping("/getUserList")
    public DataResponse getUserList(@RequestBody Map  map){
            Integer start = ( (Integer)map.get("page")-1 )*( Integer )map.get("pageSize");
            map.put("start",start);
        return  loginService.getUserList( map );
    }
    @RequestMapping("/newUserBase")
    public DataResponse newUserBase(@RequestBody Map map){
        return  loginService.newUserBase( map );
    }

    @RequestMapping("/selectByAcount")
    public DataResponse selectByAcount(@RequestBody Map map){
        return  loginService.selectByAcount( map );
    }

    @RequestMapping("/updateUserInfo")
    public DataResponse updateUserInfo(@RequestBody Map map){
        return  loginService.updateUser( map );
    }


    @RequestMapping("/changePassword")
    public DataResponse changePassword(@RequestBody UserBase user , HttpServletRequest request, HttpServletResponse response){
        return  loginService.changePassword( user );
    }
    @RequestMapping("/losePassword")
    public DataResponse losePassword(@RequestBody UserBase user , HttpServletRequest request, HttpServletResponse response){
        return  loginService.losePassword( user );
    }

}
