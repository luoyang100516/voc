package com.lizhen.crm.kernel.dao;


import com.lizhen.crm.api.entity.MenuBase;
import com.lizhen.crm.api.entity.RoleBase;

import java.util.List;
import java.util.Map;


public interface RoleBaseMapper {
    List<MenuBase> getRoleMenus(Integer roleId );


    List<RoleBase> getRoleList ( Map map );
    int getRoleCount( Map map );
    void updateRoleBase( Map map );
    void delRole( Map map );
    void delRoleMenu( Map map );
    void newRole( Map map );
    void newRoleMenu( Map map );
    RoleBase getRole( Map map );
}