package com.lizhen.crm.kernel.dao;

import com.lizhen.crm.api.entity.MenuBase;
import com.lizhen.crm.api.entity.UserBase;

import java.util.List;
import java.util.Map;

public interface UserBaseMapper {

      UserBase getUserRole( UserBase userBase );
      List<MenuBase> getMenus( Integer userId );



      UserBase selectByAccount ( Map map );
      List<UserBase> getUserList( Map map );
      void updateUserBase( Map map );
      void updateUserByPhone( Map map );
      void newUserBase( Map map );
      void updateUserByRoleId( Map map );
      Integer getUserCount(Map map);

}