package com.lizhen.crm.biz.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.lizhen.common.enums.AliMessageTemplateCode;
import com.lizhen.common.redis.RedisUtil;
import com.lizhen.common.redis.VerificationCodeUtil;
import com.lizhen.common.response.DataResponse;
import com.lizhen.common.response.ResCode;
import com.lizhen.common.util.AesEncryptUtil;
import com.lizhen.common.util.AliMsUtil;
import com.lizhen.common.util.JwtUtil;
import com.lizhen.common.util.StringUtil;
import com.lizhen.crm.api.entity.MenuBase;
import com.lizhen.crm.api.entity.UserBase;
import com.lizhen.crm.api.service.LoginService;
import com.lizhen.crm.kernel.dao.RoleBaseMapper;
import com.lizhen.crm.kernel.dao.UserBaseMapper;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by yangwei on 2019/7/23.
 */
@Service
//@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class LoginServiceImpl implements LoginService {

    /**
     * 验证码前缀
     */
    private static final String PREFIX_VERIFICATION_CODE = "LOSE_VERIFICATION_CODE_";

    @Autowired
    private UserBaseMapper userBaseMapper;

    @Autowired
    private RoleBaseMapper  roleBaseMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public DataResponse login( UserBase user ){
        DataResponse  response = new DataResponse().setCode(500);
        try{
            UserBase userInfo = userBaseMapper.getUserRole( user );
            if( userInfo!=null && !userInfo.equals("")){
                String password = userInfo.getPassword();
                if( userInfo.getStatus()==1 ){
                    if(userInfo.getRoleId()!=null && userInfo.getRoleStatus()!=null && userInfo.getRoleStatus()!=0){
                        try {
                            String pd = AesEncryptUtil.desEncrypt(password).replace("\0","");
                            if( pd.equals(user.getPassword()) ){
                                Map<String,Object> map = new HashMap<String,Object>();
                                map.put("token",createToken(userInfo));
                                map.put("name",userInfo.getName());
                                map.put("id",userInfo.getId());
                                map.put("account",userInfo.getAccount());
                                map.put("roleName",userInfo.getRoleName());
                                map.put("password",userInfo.getPassword());
                                map.put("type",userInfo.getType());
                                List<MenuBase> menuList = getMenuList(userInfo.getId());
                                map.put("data",menuList);
                                response.setData(map);
                                response.setCode(200);
                            }else{
                                response.setMessage("密码错误！");
                                response.setSuccess(false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        response.setMessage("请分配角色！");
                        response.setSuccess(false);
                    }
                }else{
                    response.setMessage("账户已被禁用！");
                    response.setSuccess(false);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            response.setMessage("登录失败！");
            response.setSuccess(false);
        }
        return response ;
    }

    public List<MenuBase> getMenuList(Integer userId){
        List<MenuBase> menuList = userBaseMapper.getMenus(userId);
        return addChildren(0,menuList);
    }

    private static List<MenuBase> addChildren(Integer menuId, List<MenuBase> menus) {
        return menus.stream().filter(menu -> menu.getParentId().equals(menuId)).map(menu -> {
            menu.setChilds(addChildren(menu.getId(), menus));
            return menu;
        }).sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort())))
                .collect(Collectors.toList());
    }













    @Override
    public DataResponse getAllMenuList(Map map) {
        DataResponse  response = new DataResponse();
        List<MenuBase> menus = new ArrayList<>();
        if(map.get("id")!=null ){
            menus = roleBaseMapper.getRoleMenus( (Integer)map.get("id"));
        }
        if( (Integer)map.get("choose") ==1 ){
            List<MenuBase> menuList = addChildren( 0  ,menus );
            response.setData( menuList );
        }else if((Integer)map.get("choose") ==2 ){
            List<MenuBase> menuList = addChildren( 0  ,menus );
            response.setData( menuList );
        }
        return response;
    }

    @Override
    public DataResponse getUserList(Map map) {
        DataResponse  response = new DataResponse();
        List<UserBase> userList = userBaseMapper.getUserList(map);
        Integer count = userBaseMapper.getUserCount( map );
        response.setCount( count ) ;
        response.setData( userList );
        return response;
    }

    @Transactional
    @Override
    public DataResponse updateUserBase(Map map) {
        DataResponse  response = new DataResponse();
        userBaseMapper.updateUserBase( map );
        return response;
    }

    @Transactional
    @Override
    public DataResponse newUserBase(Map map) {
        DataResponse  response = new DataResponse();
        UserBase user = userBaseMapper.selectByAccount( map );
        if(user!=null){
            response.setSuccess(false);
            response.setMessage("登录名账号重复");
        }else{
            userBaseMapper.newUserBase( map );
        }
        return response;
    }

    @Override
    public DataResponse selectByAcount(Map map) {
        DataResponse  response = new DataResponse();
        UserBase user = userBaseMapper.selectByAccount( map );
        response.setData( user );
        return response;
    }

    @Override
    public DataResponse updateUser(Map map) {
        DataResponse  response = new DataResponse();
        if (map.get("id")!=null) {
            userBaseMapper.updateUserBase( map );
        }else{
            userBaseMapper.newUserBase(map);
        }
        return response;
    }

    @Override
    public DataResponse changePassword(UserBase user){
        DataResponse  response = new DataResponse();
        UserBase userInfo = userBaseMapper.getUserRole( user );
        if( userInfo!=null && !userInfo.equals("")){
            String password = userInfo.getPassword();
            String pd = null;
            if( userInfo.getStatus()==1 ){
                try {
                    pd = AesEncryptUtil.desEncrypt(password).replace("\0","");
                    if( pd.equals( AesEncryptUtil.desEncrypt(user.getOldPassword()).replace("\0","")) ){
                        Map map = new HashMap();
                        map.put("account",user.getAccount());
                        map.put("password",user.getPassword());
                        userBaseMapper.updateUserByPhone( map );
                    }else{
                        response.setMessage("密码错误！");
                        response.setSuccess(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                response.setMessage("账户已被禁用！");
                response.setSuccess(false);
            }

        }
        return response ;
    }
    @Override
    public DataResponse losePassword(UserBase user){
        DataResponse  response = new DataResponse();
        String phone = user.getPhone();
        try {
            UserBase userInfo = userBaseMapper.getUserRole( user );
            if(userInfo!=null){
                if( userInfo.getStatus()==1 ){
                    String verifyCode = user.getVerifyCode();
                    String key=(String)RedisUtil.get(PREFIX_VERIFICATION_CODE+phone);
                    if(!StringUtil.equals(verifyCode,key)){
                        response.setMessage("验证码不正确！");
                        response.setCode(500);
                        response.setSuccess(false);
                    }else{
                        Map map = new HashMap();
                        map.put("phone",user.getPhone());
                        map.put("password",user.getPassword());
                        userBaseMapper.updateUserByPhone( map );
                    }
                }else{
                    response.setMessage("账户已被禁用！");
                    response.setSuccess(false);
                }
            }else{
                response.setMessage("手机号不存在！");
                response.setCode(500);
                response.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("修改密码失败！");
            response.setSuccess(false);
        }
        //登录成功则移除Redis验证码
        RedisUtil.remove(PREFIX_VERIFICATION_CODE+phone);
        return response ;
    }



    @Override
    public DataResponse sendVerificationCode(String phone) {
        DataResponse response=new DataResponse(false, ResCode.FAIL.getValue(),"发送失败");

        try {
            Map<String,Object> mapInfo = new HashMap<>();
            mapInfo.put("phone",phone);
//            UserBase userInfo = userBaseMapper.getAppUserRole( mapInfo );
//            if(userInfo==null){
//                response.setMessage("手机号不存在！");
//                return  response;
//            }
            String verificationCode= VerificationCodeUtil.getVerificationCode(6);
            JSONObject message =new JSONObject();
            message.put("code",verificationCode);
            String msgStr=message.toString();

            String templateCode= AliMessageTemplateCode.SMS_159625390.name();
            SendSmsResponse sendSmsResponse= AliMsUtil.sendSms(phone, templateCode,msgStr);
            if ("OK".equals(sendSmsResponse.getCode())) {
                com.lizhen.common.redis.RedisUtil.set(PREFIX_VERIFICATION_CODE + phone, verificationCode, 180000);
                response.setMessageAndSuccess(true, "发送成功");
                response.setCode(ResCode.SUCCESS.getValue());
            }else {
                response.setMessageAndSuccess(false,"发送失败："+sendSmsResponse.getMessage());
                response.setCode(ResCode.FAIL.getValue());
            }
        }catch (Exception e){

            response.setMessage("验证码发送失败,内部错误");
        }

        return response;
    }



    public String  createToken( UserBase user  ){
        JSONObject json = JSONObject.fromObject(user);//将java对象转换为json对象
        String str = json.toString();//将json对象转换为字符串
        String sign = JwtUtil.sign( user.getAccount() , String.valueOf(System.currentTimeMillis()), str );
        System.out.println("=========token========"+ sign);
        //redisTemplate.opsForValue().set(user.getAccount(),sign );
        //redisTemplate.expire(user.getAccount(), Long.parseLong(JwtUtil.accessTokenExpireTime) * 3600000, TimeUnit.MILLISECONDS);
        return sign;
    }


}
