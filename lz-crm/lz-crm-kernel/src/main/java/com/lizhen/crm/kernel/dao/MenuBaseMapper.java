package com.lizhen.crm.kernel.dao;

import com.lizhen.crm.api.entity.MenuBase;
import java.util.List;
import java.util.Map;
public interface MenuBaseMapper {
    List<MenuBase> getMenuListById( Map map );
    int getMeunCount ( Map map );
    List<MenuBase> getMenuListByType( Map map );
    void saveMenu ( Map map );
    void updateMenu ( Map map );
    void delMenu( Map map );
}