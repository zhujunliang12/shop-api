package com.fh.common;

import java.security.MessageDigest;


import java.util.Random;

import org.apache.commons.codec.binary.Hex;


<<<<<<< HEAD

/**
 * <pre>项目名称：movie-admin    
 * 类名称：MD5Util    
 * 类描述：MD5加密工具类(包含加盐算法) 
 * 创建人：李川 lichuan@163.com    
 * 创建时间：2019年10月30日 上午9:56:04    
 * 修改人：李川 lichuan@163.com     
 * 修改时间：2019年10月30日 上午9:56:04    
 * 修改备注：       
 * @version </pre>
 */
public class MD5Util {
	
	/**
	 * 盐：字符串 salt
     * 生成含有随机盐的密码
     */
    public static   String generate(String password) {
    	//int a = 2;
    	//a++ 龙 3
    	//高 2 a++ 3
    	//synchronized 同步锁
        Random r = new Random();
        //线程不安全，效率高，长度可变
        StringBuilder sb = new StringBuilder(16);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int len = sb.length();
=======
/**
 * <pre>项目名称：movie-admin
 * 类名称：MD5Util
 * 类描述：MD5加密工具类(包含加盐算法)
 * 创建人：李川 lichuan@163.com
 * 创建时间：2019年10月30日 上午9:56:04
 * 修改人：李川 lichuan@163.com
 * 修改时间：2019年10月30日 上午9:56:04
 * 修改备注：
 * @version </pre>
 */
public class MD5Util {

    /**
     * 盐：字符串 salt
     * 生成含有随机盐的密码
     */
    public static String generate(String password) {
        //int a = 2;
        //a++ 龙 3
        //高 2 a++ 3
        //synchronized 同步锁
        Random r = new Random( );
        //线程不安全，效率高，长度可变
        StringBuilder sb = new StringBuilder(16);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int len = sb.length( );
>>>>>>> add shop-api
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        //随机盐
<<<<<<< HEAD
        String salt = sb.toString();
=======
        String salt = sb.toString( );
>>>>>>> add shop-api
        //调用加密算法
        password = md5Hex(password + salt);
        char[] cs = new char[48];
        //把密码和盐分开
        for (int i = 0; i < 48; i += 3) {
            cs[i] = password.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }
<<<<<<< HEAD
    
=======

>>>>>>> add shop-api
    /**
     * 校验密码是否正确
     */
    public static boolean verify(String password, String md5) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5.charAt(i);
            cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
            cs2[i / 3] = md5.charAt(i + 1);
        }
        String salt = new String(cs2);
        return md5Hex(password + salt).equals(new String(cs1));
    }
<<<<<<< HEAD
    
=======

>>>>>>> add shop-api
    /**
     * md5加密算法
     * 获取十六进制字符串形式的MD5摘要
     */
    public static String md5Hex(String src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            //核心代码，加密方法
<<<<<<< HEAD
            byte[] bs = md5.digest(src.getBytes());
            return new String(new Hex().encode(bs));
=======
            byte[] bs = md5.digest(src.getBytes( ));
            return new String(new Hex( ).encode(bs));
>>>>>>> add shop-api
        } catch (Exception e) {
            return null;
        }
    }
<<<<<<< HEAD
    
    public static void main(String[] a){

    	
=======


    public static void main(String[] a) {


>>>>>>> add shop-api
    }

}
