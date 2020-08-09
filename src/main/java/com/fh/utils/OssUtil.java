package com.fh.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

public class OssUtil {

    public static final String ENDPOINT = "https://oss-cn-beijing.aliyuncs.com";
    public static final String ACCESSKEYID = "LTAI4GKZurzwinq8ecfUmcJm";
    public static final String ACCESSKEYSECRET = "UtUpWPJ6YMb6lZzS8v7bCpAeWB3VR9";
    public static final String BUCKET = "https://zhujunliang.oss-cn-beijing.aliyuncs.com/";
    public static final String BUCKETNAME = "zhujunliang";

    /**
     * 文件上传工具类
     */
    public static String ossUploadFilter(InputStream in,String filerName){
        OSS ossClient=null;
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
            String preff = FilterUtil.preff(filerName);
            String uuid = UUID.randomUUID( ).toString( ).replace("-","")+preff;
            String ymd = FilterUtil.ymd( );
            ossClient.putObject(BUCKETNAME, ymd + "/" + uuid, in);

            //设置返回的url有效期为10年
            /*Date expiration = new Date(System.currentTimeMillis() + 10 * 365 * 24 * 60 * 60 * 1000);
            URL url = ossClient.generatePresignedUrl(BUCKETNAME, ymd + "/" + uuid, expiration);
            String[] split = url.toString().split("\\?");
           String path= split[0];*/
            //return  path;
           return BUCKET+ymd+"/"+uuid;
        } catch (Exception e) {
            e.printStackTrace( );
            throw  new RuntimeException(e);
        }finally {
            if(null !=in){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace( );
                }
            }
            if(null != ossClient){
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }

    }


    /**
     * 修改图片 删除原有的路径
     * @param url
     */
    public static void deleteFile(String url){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
        // 删除文件。BUCKET+ymd+"/"
        ossClient.deleteObject(BUCKETNAME,url.replace(BUCKET,""));
        // 关闭OSSClient。
        ossClient.shutdown();
    }


}
