package com.lizhen.common.util;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * 小程序二维码生成工具类
 */
@Configuration
public class QRCodeUtil {

    private static String APP_ID = "wxd28673c4c90d2251";

    private static String APP_SECRET = "5ea32e4fcbabf4dcf24f6607a5135303";

    private static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

    //方的
    private static String CODE_URL = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode";

    //圆的
    private static String CODE_URL2 = "https://api.weixin.qq.com/wxa/getwxacodeunlimit";

    //openid
    private static String OPENID_URL = "https://api.weixin.qq.com/sns/jscode2session";

    /**
     *
     * @方法名称 getToken
     * @return
     */
    public static String getToken() {
        try {
//            Map<String, String> map = new LinkedHashMap<>();
//            map.put("grant_type", "client_credential");
//            map.put("appid", APP_ID);//appid 小程序后台获取
//            map.put("secret", APP_SECRET);//app秘钥 小程序后台获取
            String param = "?grant_type=client_credential&appid="+APP_ID+"&secret="+APP_SECRET;
//            String res = HttpUtil.postGeneralUrl(TOKEN_URL,"multipart/form-data",JSONObject.toJSONString(map),"UTF-8");
            String res = HttpUtil.sendPost(TOKEN_URL+param);
            JSONObject json = JSONObject.parseObject(res);
            if (json.getString("access_token") != null || json.getString("access_token") != "") {
                return json.getString("access_token");
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     *
     * @方法名称 getQR
     * @return
     */
    public static InputStream getQR(String merchantUrl) {
        try
        {
            if(getToken()==null){
                return null;
            }
            URL url = new URL(CODE_URL2+"?access_token="+getToken());
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");//post 必须 post
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            int length = 20;
            if(merchantUrl.length()<length){
                length=merchantUrl.length();
            }
            paramJson.put("scene","url=" + merchantUrl.substring(0,length));//这就是你二维码里携带的参数 String
//            paramJson.put("path", "pages/index/index"); //这是设置扫描二维码后跳转的页面
//            paramJson.put("path", "?merchantUrl=" + merchantUrl);
            paramJson.put("width", 430);
            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            return httpURLConnection.getInputStream();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    /**
     *
     * 获取用户openid
     * @方法名称 getOpenId
     * @return
     */
    public static String getOpenId(String code) {
        try
        {
            String grant_type = "authorization_code";
            Map<String,String> params = new HashMap<>();
            params.put("appid",APP_ID);
            params.put("secret",APP_SECRET);
            params.put("js_code",code);
            params.put("grant_type",grant_type);
            params.put("connect_redirect","1");
            return HttpUtil.getOpenId(OPENID_URL,params);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
