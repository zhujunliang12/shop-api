package com.fh;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.junit.jupiter.api.Test;

public class TestRSA {

    @Test
    public void test1(){
        // 依赖
        RSA rsa = new RSA( );
        // 获取公钥
        String publicKeyBase64 = rsa.getPublicKeyBase64( );
        // 获取私钥
        String privateKeyBase64 = rsa.getPrivateKeyBase64( );
        System.out.println(publicKeyBase64);
        System.out.println(privateKeyBase64);
    }

    @Test
     public void test2(){
        String publicKeyBase64="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCK/sCfmZHaqAlS//NwgbOzcwxOIlBBQdEupe8pu+BoeWgSUpL9kjCbV+FNrmdKSFzJFCaFSMVwDi9uwEGboNu+3htLYQWHSOx9YFjePfAWeEaMjdZTCRRyRGEVzbQmMBJhFamSCs54/i37GDcb0t2KDiQubrfsqon8RoVzzfX13wIDAQAB";
        String privateKeyBase64="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIr+wJ+ZkdqoCVL/83CBs7NzDE4iUEFB0S6l7ym74Gh5aBJSkv2SMJtX4U2uZ0pIXMkUJoVIxXAOL27AQZug277eG0thBYdI7H1gWN498BZ4RoyN1lMJFHJEYRXNtCYwEmEVqZIKznj+LfsYNxvS3YoOJC5ut+yqifxGhXPN9fXfAgMBAAECgYASG6JIqJ8h12Zvk7zJWXxp0oE15K6D8ekopKGF1F4l/jsat/tCYbtHYamOjzwRp1+/KoriLlragF6ZlR53AfverotDGp0NGbiPoUFvnGajFRg0A5sqEKxoncvfzhequGMIdAYwlIDYKGSnNlW3c0imB1Zx8kAnuaFOgmMGCUBF8QJBAMzse4jx30UouAdOd6lQ8QIbj1HRb23y/6Tdoi+rbnNcSdqxkXLosxMgKYfivj5xBroIcAjsR+9UDK/5YErOEwsCQQCto5Qe1VvkpuLbb/WMGVClYi+NeqyuTlMS/uBHZbPWcdKn+Ei2jtxeaBTB5QNMlIggV6wlkUvObeGQn9pLnez9AkEAnb/F8UvQhhqsX+OFti96+BZjI6bH0qnnJfD/cxbS9bcrm1BqGT0M7UIq9lIUBVlYxkjMhfHuBkx7X9Pmzm8ItQJBAJDPWzhbt8d5ZribJq0wb5F90SqspFa5+45cAa+JofG1+3kaF4oomqAhLS6HT7kOJnjSpT6vbgk6mmOC7QsjKgECQEMMWTOr6nUui9yUlWbzy8Y0nyDCBIFdEsVTiaqgrnQvxW09bx5ADEvVsHBrT9zqoxXGJnCNNrepjGytm9WZh1E=";

        RSA rsa = new RSA(privateKeyBase64,publicKeyBase64);
        // 根据它里面的方法 将明文 通过 公钥 加密
        String jiami = rsa.encryptBase64("你好小明", KeyType.PublicKey);
        System.out.println("加密后的私钥："+jiami);
        // 加加密后的私钥 进行解密
        byte[] bytes = rsa.decryptFromBase64(jiami, KeyType.PrivateKey);
        String s = new String(bytes);
        System.out.println("解密后："+s);
    }
}
