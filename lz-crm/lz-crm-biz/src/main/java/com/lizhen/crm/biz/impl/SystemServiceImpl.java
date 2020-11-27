package com.lizhen.crm.biz.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lizhen.common.response.DataResponse;
import com.lizhen.crm.api.entity.MenuBase;
import com.lizhen.crm.api.entity.RoleBase;
import com.lizhen.crm.api.entity.UserBase;
import com.lizhen.crm.api.service.SystemService;
import com.lizhen.crm.kernel.dao.MenuBaseMapper;
import com.lizhen.crm.kernel.dao.RoleBaseMapper;
import com.lizhen.crm.kernel.dao.UserBaseMapper;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xsj on 2019/7/31.
 */
@Service//(interfaceClass =SystemService.class )
//@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class SystemServiceImpl implements SystemService{

    @Autowired
    private MenuBaseMapper menuBaseMapper;

    @Autowired
    private RoleBaseMapper roleBaseMapper;

    @Autowired
    private UserBaseMapper userBaseMapper;

    @Override
    public DataResponse getMenu( Map map ) {
        DataResponse  response = new DataResponse();
        List<MenuBase> menuLIst = menuBaseMapper.getMenuListById( map );
        int count= menuBaseMapper.getMeunCount( map );
        response.setData(menuLIst);
        response.setCount(count);
        return response;
    }

    @Override
    public DataResponse getParentMenu(Map map) {
        DataResponse  response = new DataResponse();
        List<MenuBase> menuLIst = menuBaseMapper.getMenuListByType( map );
        response.setData(menuLIst);
        return response;
    }
    @Transactional
    @Override
    public DataResponse saveMenu(Map map) {
        DataResponse  response = new DataResponse();
        menuBaseMapper.saveMenu (map );
        return response;
    }
    @Transactional
    @Override
    public DataResponse updateMenu(Map map) {
        DataResponse  response = new DataResponse();
        menuBaseMapper.updateMenu( map );
        return response;
    }
    @Transactional
    @Override
    public DataResponse delMenu(Map map) {
        DataResponse  response = new DataResponse();
//        getMenuList( (Integer)map.get("id")  ,userBaseMapper );
//        menuBaseMapper.delMenu( map );
        return response;
    }

    @Override
    public DataResponse getRoleList(Map map) {
        DataResponse  response = new DataResponse();
        List<RoleBase> roleList = roleBaseMapper.getRoleList( map );
        int count = roleBaseMapper.getRoleCount( map );
        response.setCount(count);
        response.setData(roleList);
        return response;
    }

    @Transactional
    @Override
    public DataResponse updateRoleBase( Map map ) {
        DataResponse  response = new DataResponse();
        roleBaseMapper.updateRoleBase( map );
        return response;
    }

    @Transactional
    @Override
    public DataResponse delRole( Map map ) {
        DataResponse  response = new DataResponse();
        Integer del =(Integer) map.get("del");//判断是否删除角色菜单的关系
        if( del == 2 ){
            roleBaseMapper.delRole( map );
            Map<String,Object> mapInfo = new HashedMap();
            mapInfo.put("roleId",map.get("id"));
            List<UserBase> UserBaseList = userBaseMapper.getUserList( mapInfo );
            for( UserBase user:UserBaseList ){
                Map<String,Object> mapData = new HashedMap();
                mapData.put("id",user.getId());
                userBaseMapper.updateUserByRoleId( mapData );
            }
        }
        roleBaseMapper.delRoleMenu( map );
        return response;
    }

    @Transactional
    @Override
    public DataResponse newRole( Map map ) {
        DataResponse  response = new DataResponse();
        if( map.get("id")==null || map.get("id").equals("") ){
            roleBaseMapper.newRole( map );
        }else{
            roleBaseMapper.updateRoleBase( map );
        }
        List<Integer>  list = ( List<Integer> ) map.get("ids");
        for( Integer obj: list ){
            Map mapInfo = new HashMap<String ,Object>();
            mapInfo.put("roleId",map.get("id"));
            mapInfo.put("menuId",obj);
            roleBaseMapper.newRoleMenu(mapInfo);
        }
        return response;
    }

    @Override
    public DataResponse getRole(Map map) {
        DataResponse  response = new DataResponse();
        RoleBase rolebase = roleBaseMapper.getRole( map );
        response.setData(rolebase);
        return response;
    }

    @Override
    public DataResponse selectIsRepeat(Map map) {
        DataResponse  response = new DataResponse();
        Map<String,Object> mapInfo = new HashedMap();
        Map<String,Object> mapInfo2 = new HashedMap();
        UserBase us1 = null;
        UserBase us2 = null;
        if( map.get("account")!=null){
            mapInfo.put("account",map.get("account"));
            us1=userBaseMapper.selectByAccount( mapInfo );
        }
        if( map.get("phone")!=null){
            mapInfo2.put("phone",map.get("phone"));
            us2=userBaseMapper.selectByAccount( mapInfo2 );
        }
        if( us1!=null && us2!=null ){
            response.setMessage("登录名和手机号重复！");
            response.setSuccess(false);
        }else if( us1!=null ){
            response.setMessage("登录名重复！");
            response.setSuccess(false);
        }else if( us2!=null ){
            response.setMessage("手机号重复！");
            response.setSuccess(false);
        }else{
            response.setSuccess(true);
        }
        return response;
    }


//    public void getMenuList(Integer parentId, UserBaseMapper userBaseMapper){
//        List<MenuBase> menuList = userBaseMapper.getMenuList(parentId);
//        fillChild(menuList, userBaseMapper);
//    }
//
//    public void fillChild(List<MenuBase> menuList, UserBaseMapper userBaseMapper) {
//        if(menuList != null && menuList.size()>0) {
//            for (MenuBase menu : menuList) {
//                Map mapData = new HashMap<String,Object>();
//                mapData.put("id",menu.getId());
//                menuBaseMapper.delMenu( mapData );
//                List<MenuBase> childs = userBaseMapper.getMenuList(menu.getId());
//                for( MenuBase child : childs ){
//                    Map map = new HashMap<String,Object>();
//                    map.put("id",child.getId());
//                    menuBaseMapper.delMenu( map );
//                }
//                fillChild(childs, userBaseMapper);
//            }
//        }
//    }
//
//
//    public List<Integer>  getMenuIds(int userId ){
//        return userBaseMapper.getMenuIds(userId);
//    }

}
