package com.fh.service.impl;

import com.fh.service.JedisUtil;
import com.fh.utils.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class JedisUtilImpl implements JedisUtil {

  
    @Autowired
    private JedisPool jedisPool;

   //放入缓存
    public void putObject(Object key, Object value) {
        // TODO Auto-generated method stub
        Jedis resource = jedisPool.getResource();
        resource.set(SerializeUtil.serialize(key.toString()),
                SerializeUtil.serialize(value));
        resource.close();
    }
   //清楚缓存
    public Object removeObject(Object arg0) {
        // TODO Auto-generated method stub
        Jedis resource = jedisPool.getResource();
        Object expire = resource.expire(
                SerializeUtil.serialize(arg0.toString()), 0);
        resource.close();
        return expire;
    }
    //从缓存中取
    public Object getObject(Object arg0) {
        // TODO Auto-generated method stub
        Jedis resource = jedisPool.getResource();
        Object value = SerializeUtil.unserialize(resource.get(
                SerializeUtil.serialize(arg0.toString())));
        resource.close();
        return value;
    }

    @Override
    public void deleteKey(String key) {
        Jedis jedes = jedisPool.getResource();
        jedes.del(key);
    }
}