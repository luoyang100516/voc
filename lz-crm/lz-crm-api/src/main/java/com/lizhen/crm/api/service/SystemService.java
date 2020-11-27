package com.lizhen.crm.api.service;

import com.lizhen.common.response.DataResponse;
import com.lizhen.crm.api.entity.UserBase;

import java.util.Map;

/**
 * Created by xsj on 2019/7/31.
 */
public interface SystemService {
    public DataResponse getMenu  ( Map map );
    public DataResponse getParentMenu  ( Map map );
    public DataResponse saveMenu  ( Map map );
    public DataResponse updateMenu  ( Map map );
    public DataResponse  delMenu( Map map );
    public DataResponse  getRoleList( Map map );
    public DataResponse updateRoleBase( Map map );
    public DataResponse delRole( Map map );
    public DataResponse newRole( Map map );
    public DataResponse getRole( Map map );
    public DataResponse selectIsRepeat( Map map );
}
