package com.fh.config;


import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {


    public static final String IP = "192.168.40.133";

    private RedisPool() {
    }

    private static JedisPool jedisPool;

    private static void initPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig( );
        jedisPoolConfig.setMaxTotal(1000);
        jedisPoolConfig.setMaxIdle(500);
        jedisPoolConfig.setMinIdle(500);
        jedisPool = new JedisPool(jedisPoolConfig, IP, 6379);
    }

    static {
        initPool( );
    }

    public static Jedis getResource() {
        return jedisPool.getResource( );
    }


}
