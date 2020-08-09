package com.fh.config;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class WXConfig implements WXPayConfig {

    private byte[] certData;


    public String getAppID() {
        return "wxa1e44e130a9a8eee";
    }

    public String getMchID() {
        return "1507758211";
    }

    public String getKey() {
        return "feihujiaoyu12345678yuxiaoyang123";
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
