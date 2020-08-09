package com.fh.utils;

<<<<<<< HEAD

=======
import com.fh.config.RedisPool;
>>>>>>> add shop-api
import redis.clients.jedis.Jedis;

public class RedisUtils {

    /**
     * redis 为key赋值方法
     *
     * @param key
     * @param value
     */
    public static void setKey(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource( );
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace( );
            throw new RuntimeException(e);
        } finally {
            if (null != jedis) {
                jedis.close( );
            }
        }
    }

    /**
     * redis get取值方法
     *
     * @param key
     */
    public static String getKey(String key) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource( );
            String res = jedis.get(key);
            return res;
        } catch (Exception e) {
            e.printStackTrace( );
            throw new RuntimeException(e);
        } finally {
            if (null != jedis) {
                jedis.close( );
            }
        }
    }

    /**
     * redis delet删除方法
     *
     * @param key
     */
<<<<<<< HEAD
    public static void delKey(String key) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource( );
            jedis.del(key);
=======
    public static long delKey(String key) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource( );
            return jedis.del(key);
>>>>>>> add shop-api
        } catch (Exception e) {
            e.printStackTrace( );
            throw new RuntimeException(e);
        } finally {
            if (null != jedis) {
                jedis.close( );
            }
        }
    }

    public static void setEx(String key, String value, int expireDate) {
<<<<<<< HEAD
        Jedis jedis =null;
        try {
            jedis= RedisPool.getResource( );
=======
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource( );
>>>>>>> add shop-api
            jedis.setex(key, expireDate, value);
        } catch (Exception e) {
            e.printStackTrace( );
        } finally {
            if (null != jedis) {
                jedis.close( );
            }
        }
    }

    /**
     * 判断key 是否存在
<<<<<<< HEAD
     * @param key
     * @return
     */
    public static boolean exist(String key){
        Jedis jedis =null;
        try {
            jedis= RedisPool.getResource( );
=======
     *
     * @param key
     * @return
     */
    public static boolean exist(String key) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource( );
>>>>>>> add shop-api
            return jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace( );
            return false;
        } finally {
            if (null != jedis) {
                jedis.close( );
            }
        }
    }

<<<<<<< HEAD
=======
    public static void expire(String key,int time) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource( );
            jedis.expire(key,time);
        } catch (Exception e) {
            e.printStackTrace( );
        } finally {
            if (null != jedis) {
                jedis.close( );
            }
        }
    }
>>>>>>> add shop-api
}
