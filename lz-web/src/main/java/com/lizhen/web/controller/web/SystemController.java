package com.lizhen.web.controller.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lizhen.common.response.DataResponse;
import com.lizhen.crm.api.entity.UserBase;
import com.lizhen.crm.api.service.SystemService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by xsj on 2019/7/31.
 */

@RestController
@CrossOrigin
public class SystemController {

    @Reference
    private SystemService systemService;

    @RequestMapping("/getMenu")
    public DataResponse getMenuList( @RequestBody Map map  ){
         Integer start = ( (Integer)map.get("page")-1 )*(Integer)map.get("pageSize");
         map.put("start",start);
         return  systemService.getMenu( map );
    }
    @RequestMapping("/getMenuByType")
    public DataResponse getMenuByType( @RequestBody Map map ){
        return systemService.getParentMenu( map );
    }

    @RequestMapping("/saveMenu")
    public DataResponse saveMenu( @RequestBody Map map ){
        return systemService.saveMenu( map );
    }

    @RequestMapping("/updateMenu")
    public DataResponse updateMenu( @RequestBody Map map ){
        return systemService.updateMenu( map );
    }

    @RequestMapping("/delMenu")
    public DataResponse delMenu( @RequestBody Map map ){
        return systemService.delMenu( map );
    }

    @RequestMapping("/delRole")
    public DataResponse delRole( @RequestBody Map map ){
        return systemService.delRole( map );
    }

    @RequestMapping("/getRoleList")
    public DataResponse getRoleList( @RequestBody Map  map  ){
        Integer start = ( (Integer)map.get("page")-1 )*( Integer )map.get("pageSize");
        map.put("start",start);
        return  systemService.getRoleList(map);
    }
    @RequestMapping("/updateRoleBase")
    public DataResponse updateRoleBase( @RequestBody Map map  ){
        return  systemService.updateRoleBase( map );
    }

    @RequestMapping("/newRoleBase")
    public DataResponse newRoleBase( @RequestBody Map map  ){
        return  systemService.newRole( map );
    }

    @RequestMapping("/getRole")
    public DataResponse getRole( @RequestBody Map map  ) {
        return systemService.getRole(map);
    }

    @RequestMapping("/getUserRole")
    public DataResponse getUserRole( @RequestBody Map  map  ){
        return  systemService.getRoleList(map);
    }

    @RequestMapping("/selectIsRepeat")
    public DataResponse selectIsRepeat( @RequestBody Map  map  ){
        return  systemService.selectIsRepeat(map);
    }






}
