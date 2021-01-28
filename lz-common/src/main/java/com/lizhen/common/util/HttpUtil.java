package com.lizhen.common.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * http 工具类
 */
@Slf4j
public class HttpUtil {

    public static InputStream sendGetOriginal(String reqUrl) throws Exception {
        return sendGetOriginal(reqUrl, null);
    }

    public static String getOpenId(String reqUrl, Map<String, String> params) throws Exception {
        HttpGet request = new HttpGet();
        if (params != null) {
            reqUrl = buildUrl(reqUrl, params);
        }
        HttpClient client =  HttpClientBuilder.create().build();
        request.setHeader("Accept-Encoding", "gzip");
        request.setURI(new URI(reqUrl));
        // 执行get请求，得到响应体
        HttpResponse response = client.execute(request);
        String res = EntityUtils.toString(response.getEntity());
        JSONObject object = JSONObject.parseObject(res);
        return object.getString("openid");
    }
    public static InputStream sendGetOriginal(String reqUrl, Map<String, String> params) throws Exception {
        HttpGet request = new HttpGet();
        if (params != null) {
            reqUrl = buildUrl(reqUrl, params);
        }

        HttpClient client =  HttpClientBuilder.create().build();
        request.setHeader("Accept-Encoding", "gzip");
        request.setURI(new URI(reqUrl));
        // 执行get请求，得到响应体
        HttpResponse response = client.execute(request);
        return response.getEntity().getContent();
    }

    private static String buildUrl(String reqUrl, Map<String, String> params) {
        StringBuilder query = new StringBuilder();
        Set<String> set = params.keySet();
        for (String key : set) {
            query.append(String.format("%s=%s&", key, params.get(key)));
        }
        return reqUrl + "?" + query.toString();
    }

    public static String post(String requestUrl, String accessToken, String params)
            throws Exception {
        String contentType = "application/x-www-form-urlencoded";
        return HttpUtil.post(requestUrl, accessToken, contentType, params);
    }

    public static String post(String requestUrl, String accessToken, String contentType, String params)
            throws Exception {
        String encoding = "UTF-8";
        if (requestUrl.contains("nlp")) {
            encoding = "GBK";
        }
        return HttpUtil.post(requestUrl, accessToken, contentType, params, encoding);
    }

    public static String post(String requestUrl, String accessToken, String contentType, String params, String encoding)
            throws Exception {
        String url = requestUrl + "?access_token=" + accessToken;
        return HttpUtil.postGeneralUrl(url, contentType, params, encoding);
    }

    public static String postGeneralUrl(String generalUrl, String params) throws Exception {
        return postGeneralUrl(generalUrl, "application/json;charset=utf-8", params, "utf-8");
    }

    public static String postGeneralUrl(String generalUrl, String contentType, String params, String encoding)
            throws Exception {
        URL url = new URL(generalUrl);
        // 打开和URL之间的连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        // 设置通用的请求属性
        connection.setRequestProperty("Content-Type", contentType);
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        // 得到请求的输出流对象
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.write(params.getBytes(encoding));
        out.flush();
        out.close();

        // 建立实际的连接
        connection.connect();
        // 获取所有响应头字段
        Map<String, List<String>> headers = connection.getHeaderFields();
        // 遍历所有的响应头字段
        for (String key : headers.keySet()) {
            System.err.println(key + "--->" + headers.get(key));
        }
        // 定义 BufferedReader输入流来读取URL的响应
        BufferedReader in = null;
        in = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), encoding));
        String result = "";
        String getLine;
        while ((getLine = in.readLine()) != null) {
            result += getLine;
        }
        in.close();
        System.err.println("result:" + result);
        return result;
    }

    /**
     * 一般json格式的post请求
     * @param reqUrl
     * @param params
     * @return
     * @throws Exception
     */
    public static String sendPost(String reqUrl, Map<String, String> params) throws Exception {
        HttpClient httpClient =  HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(reqUrl);

        // 设置请求的header
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");

        // 设置请求的参数
        JSONObject jsonParam = new JSONObject();
        jsonParam.putAll(params);
        StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
        httpPost.setEntity(entity);

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        return EntityUtils.toString(response.getEntity(), "utf-8");
    }

    /**
     * 一般json格式的post请求
     * @param reqUrl
     * @return
     * @throws Exception
     */
    public static String sendPost(String reqUrl) throws Exception {
        HttpClient httpClient =  HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(reqUrl);

        // 设置请求的header
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        return EntityUtils.toString(response.getEntity(), "utf-8");
    }


}
