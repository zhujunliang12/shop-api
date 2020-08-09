package com.fh.utils;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

import java.io.UnsupportedEncodingException;

public class RASUtils {

   public static final String PUBLICKEYBASE64="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCK/sCfmZHaqAlS//NwgbOzcwxOIlBBQdEupe8pu+BoeWgSUpL9kjCbV+FNrmdKSFzJFCaFSMVwDi9uwEGboNu+3htLYQWHSOx9YFjePfAWeEaMjdZTCRRyRGEVzbQmMBJhFamSCs54/i37GDcb0t2KDiQubrfsqon8RoVzzfX13wIDAQAB";
   public static final String PRIVATEKEYBASE64="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIr+wJ+ZkdqoCVL/83CBs7NzDE4iUEFB0S6l7ym74Gh5aBJSkv2SMJtX4U2uZ0pIXMkUJoVIxXAOL27AQZug277eG0thBYdI7H1gWN498BZ4RoyN1lMJFHJEYRXNtCYwEmEVqZIKznj+LfsYNxvS3YoOJC5ut+yqifxGhXPN9fXfAgMBAAECgYASG6JIqJ8h12Zvk7zJWXxp0oE15K6D8ekopKGF1F4l/jsat/tCYbtHYamOjzwRp1+/KoriLlragF6ZlR53AfverotDGp0NGbiPoUFvnGajFRg0A5sqEKxoncvfzhequGMIdAYwlIDYKGSnNlW3c0imB1Zx8kAnuaFOgmMGCUBF8QJBAMzse4jx30UouAdOd6lQ8QIbj1HRb23y/6Tdoi+rbnNcSdqxkXLosxMgKYfivj5xBroIcAjsR+9UDK/5YErOEwsCQQCto5Qe1VvkpuLbb/WMGVClYi+NeqyuTlMS/uBHZbPWcdKn+Ei2jtxeaBTB5QNMlIggV6wlkUvObeGQn9pLnez9AkEAnb/F8UvQhhqsX+OFti96+BZjI6bH0qnnJfD/cxbS9bcrm1BqGT0M7UIq9lIUBVlYxkjMhfHuBkx7X9Pmzm8ItQJBAJDPWzhbt8d5ZribJq0wb5F90SqspFa5+45cAa+JofG1+3kaF4oomqAhLS6HT7kOJnjSpT6vbgk6mmOC7QsjKgECQEMMWTOr6nUui9yUlWbzy8Y0nyDCBIFdEsVTiaqgrnQvxW09bx5ADEvVsHBrT9zqoxXGJnCNNrepjGytm9WZh1E=";


    /**
     * ras 解密的方法
     * @param data
     * @return
     */
    public static String decrptCode(String data){
        RSA rsa = new RSA(PRIVATEKEYBASE64,PUBLICKEYBASE64);
        byte[] bytes = rsa.decryptFromBase64(data, KeyType.PrivateKey);
        try {
            String decrptStr = new String(bytes, "utf-8");
            return decrptStr;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace( );
            throw new RuntimeException(e);
        }

    }


}
