package com.fh.service;

public interface JedisUtil {
    /**
     * 放入缓存
     * @param key
     * @param value
     */
    void putObject(Object key, Object value);
    /**
     * 清除缓存
     * @param arg0
     * @return
     */
    Object removeObject(Object arg0);
    /**
     * 从缓存中获取
     * @param arg0
     * @return
     */
    Object getObject(Object arg0);

    void deleteKey(String key);
}