package com.lizhen.common.util;

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * 文件上传七牛工具类
 */
@Configuration
public class UploadUtil {

    private static String accessKey;
    private static String secretKey;
    private static String imageBucket;
    private static String videoBucket;

    private static String remoteUrl;
    private static String remoteImageUrl;

    @Value("${qn.accessKey}")
    public void setAccessKey(String accessKey) {
        UploadUtil.accessKey = accessKey;
    }
    @Value("${qn.secretKey}")
    public void setSecretKey(String secretKey) {
        UploadUtil.secretKey = secretKey;
    }
    @Value("${qn.imageBucket}")
    public void setImageBucket(String imageBucket) {
        UploadUtil.imageBucket = imageBucket;
    }
    @Value("${qn.videoBucket}")
    public void setVideoBucket(String videoBucket) {
        UploadUtil.videoBucket = videoBucket;
    }

    @Value("${qn.remoteUrl}")
    public void setRemoteUrl(String remoteUrl) {
        UploadUtil.remoteUrl = remoteUrl;
    }

    @Value("${qn.remoteImageUrl}")
    public void setRemoteImageUrl(String remoteImageUrl) {
        UploadUtil.remoteImageUrl = remoteImageUrl;
    }


    /**
     * 图片上传

     * @param file  图片路径
     * @return  链接
     * @throws QiniuException
     */
    public static  String  uploadImage(MultipartFile file) throws IOException {
        Auth auth = Auth.create(accessKey,secretKey);
        String upToken = auth.uploadToken(imageBucket);  //上传凭证
        com.qiniu.storage.Configuration cfg = new com.qiniu.storage.Configuration(Zone.autoZone());
        UploadManager uploadManager = new UploadManager(cfg);
        String newFileName = UUID.randomUUID().toString() + ".jpg" ;
        Response response = uploadManager.put(file.getBytes(), newFileName, upToken);
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(),DefaultPutRet.class);
//        String remoteImageUrl = "http://qgdyb2c03.hn-bkt.clouddn.com/";
        return remoteImageUrl+putRet.key;
    }
    /**
     * 图片上传

     * @param is  图片路径
     * @return  链接
     * @throws QiniuException
     */
    public static  String  uploadImage(InputStream is) throws IOException {
        Auth auth = Auth.create(accessKey,secretKey);
        String upToken = auth.uploadToken(imageBucket);  //上传凭证
        com.qiniu.storage.Configuration cfg = new com.qiniu.storage.Configuration(Zone.autoZone());
        UploadManager uploadManager = new UploadManager(cfg);
        String newFileName = UUID.randomUUID().toString() + ".jpg" ;
        ByteArrayOutputStream outStream;
        outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while( (len=is.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        Response response = uploadManager.put(outStream.toByteArray(), newFileName, upToken);
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(),DefaultPutRet.class);
        return remoteImageUrl+putRet.key;
    }
    /**
     * 文件上传

     * @param filePath  文件路径
     * @return  链接
     * @throws QiniuException
     */
    public static  String  uploadFile(String filePath,String fileName) throws IOException {
        Auth auth = Auth.create(accessKey,secretKey);
        String upToken = auth.uploadToken(imageBucket,fileName);  //上传凭证
        com.qiniu.storage.Configuration cfg = new com.qiniu.storage.Configuration(Zone.autoZone());
        UploadManager uploadManager = new UploadManager(cfg);
        Response response = uploadManager.put(filePath, fileName, upToken);
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(),DefaultPutRet.class);
        File file = new File(filePath);
        file.delete();
        return remoteImageUrl+putRet.key;
    }
    /**
     * 获取视频链接
     * @param fileName  视频名称
     * @return  链接
     * @throws QiniuException
     */
    public static  String  getVideoUrl(String fileName) throws IOException {
        String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
        Auth auth = Auth.create(accessKey,secretKey);
        String publicUrl = String.format("%s/%s", remoteUrl, encodedFileName);
        long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
        String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        return finalUrl;
    }
    /**
     * 获取视频链接
     * @param filenameList  文件名称列表
     * @return  链接
     * @throws QiniuException
     */
    public static   List<Map<String,Object>>  getVideoUrlList(String filenameList) throws IOException {
        Auth auth = Auth.create(accessKey,secretKey);
        List<Map<String,Object>> videoUrlList = new ArrayList<>();
        String[] nameList = filenameList.split(",");
        for(String filename : nameList){
            String encodedFileName = URLEncoder.encode(filename, "utf-8").replace("+", "%20");
            String publicUrl = String.format("%s/%s", remoteUrl, encodedFileName);
            long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
            String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
            Map<String,Object> fileMap = new HashMap<>();
            fileMap.put("filename",filename);
            fileMap.put("fileUrl",finalUrl);
            videoUrlList.add(fileMap);
        }
        return videoUrlList;
    }

    /**
     * 获取文件列表
     * @return  文件列表
     * @throws QiniuException
     */
    public static  List<String>  getFileList(String prefix) throws IOException {
        Auth auth = Auth.create(accessKey,secretKey);
        com.qiniu.storage.Configuration cfg = new com.qiniu.storage.Configuration(Zone.autoZone());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        FileListing list = bucketManager.listFiles(videoBucket,prefix,null,0,null);
        List<String> nameList = new ArrayList<>();
        for(FileInfo f: list.items){
            nameList.add(f.key);
        }
        return nameList;
    }

}
