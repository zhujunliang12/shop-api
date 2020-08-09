package com.fh.shop;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class TestOSS {

    /**
     * 测试oss文件上传
     */
    @Test
    public  void testOss(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = "LTAI4GKZurzwinq8ecfUmcJm";
        String accessKeySecret = "UtUpWPJ6YMb6lZzS8v7bCpAeWB3VR9";
        String Bucket="1907aa.oss-cn-beijing.aliyuncs.com";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("D:\\img\\timg.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace( );
        }
        ossClient.putObject("1907aa", "b.jpg", inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    public void delete(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4GKZurzwinq8ecfUmcJm";
        String accessKeySecret = "UtUpWPJ6YMb6lZzS8v7bCpAeWB3VR9";
        String bucketName = "1907aa";
        String objectName = "787a0122-3d89-47c3-b4e2-f7d4a4012ea8.jpg";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }


}
