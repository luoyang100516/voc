package com.lizhen.crm.api.service;
import com.lizhen.common.response.DataResponse;
import com.lizhen.crm.api.entity.UserBase;

import java.util.Map;

/**
 * Created by xsj on 2019/7/25.
 */
public interface LoginService {

    public DataResponse login ( UserBase user );



    public DataResponse getAllMenuList ( Map map );
    public DataResponse getUserList( Map map );
    public DataResponse updateUserBase( Map map );
    public DataResponse newUserBase( Map map );
    public DataResponse selectByAcount( Map map );
    public DataResponse updateUser( Map map );

    DataResponse changePassword(UserBase user);
    DataResponse losePassword(UserBase user);

    DataResponse sendVerificationCode(String phone);
}
