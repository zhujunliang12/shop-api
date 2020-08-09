package com.fh.config;

import com.github.wxpay.sdk.WXPayConfig;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class PayConfig implements WXPayConfig {

    public static final String APPID = "wxa1e44e130a9a8eee";
    public static final String MCHID = "1507758211";
    public static final String KEY = "feihujiaoyu12345678yuxiaoyang123";

    private byte[] certData;

    public String getAppID() {
        return APPID;
    }

    public String getMchID() {
        return MCHID;
    }

    public String getKey() {
        return KEY;
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

}
