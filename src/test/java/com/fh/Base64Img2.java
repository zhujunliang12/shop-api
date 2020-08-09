package com.fh;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.UUID;

public class Base64Img2 {

    /**
     * 将 图片 加密成base64位
     * @Description: 根据图片地址转换为base64编码字符串
     * @Author:
     * @CreateTime:
     * @return
     */
    public static String getImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }


    /**
     * base64字符串转图片
     * @param imgStr 图片的base64
     * @param path  将要生成的地址
     * @return
     */
    public static String generateImage(String imgStr, String path) {
 //       path="d:\\";
        //如果图像数据为空
        if (imgStr == null) {
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //解密
            byte[] b = decoder.decodeBuffer(imgStr);
            //处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            //图片名称
            String fileName= UUID.randomUUID().toString() +".jpg";
            File image = new File(path+fileName);
            if (!image.exists()) {
                image.getParentFile().mkdir();
            }
            OutputStream out = new FileOutputStream(path+fileName);
            out.write(b);
            out.flush();
            out.close();
            //返回图片地址+名称，方便存入数据库中
            return (path+fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String imageStr = getImageStr("D:\\img\\大声撒.jpg");
        System.out.println(imageStr);
        generateImage(imageStr,"d:\\");
    }

}
