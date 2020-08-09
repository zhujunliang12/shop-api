package com.fh.utils;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {



    private RedisPool(){}

    private static JedisPool jedisPool;

    private static void initPool(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig( );
        jedisPoolConfig.setMaxTotal(1000);
        jedisPoolConfig.setMaxIdle(500);
        jedisPoolConfig.setMinIdle(500);
        jedisPool = new JedisPool(jedisPoolConfig,"192.168.40.129",6379);
    }

    static {
        initPool();
    }

    public static Jedis getResource(){
        return jedisPool.getResource();
    }


}
