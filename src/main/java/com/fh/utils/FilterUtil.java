package com.fh.utils;

import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

public class FilterUtil {
    /**
     * 获取后缀的方法
     * @param fileName
     * @return
     */
    public static String preff(String fileName){
        int le = fileName.lastIndexOf(".");
        String preffZhui = fileName.substring(le);
        return preffZhui;
    }

    /**
     * 日期转换为年月日
     * @return
     */
    public static String ymd(){
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
        String format = yyyyMMdd.format(new Date( ));
        return format;
    }
    /**
     * 日期转换为年月日
     * @return
     */
    public static String ymd(Date data){
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
        String format = yyyyMMdd.format(data);
        return format;
    }


    /**
     * 解压的工具类
     * @param srcFile
     * @param destDirPath
     * @return
     * @throws RuntimeException
     */
    public static List<Map<String,Object>> unZip(File srcFile, String destDirPath) throws RuntimeException {

        List<Map<String,Object>>list=new ArrayList();
        Map<String,Object>map=null;

        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new RuntimeException(srcFile.getPath() + "所指文件不存在");
        }
        // 开始解压
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(srcFile);
            Enumeration entries = zipFile.getEntries( );
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                //  System.out.println("解压" + entry.getName());
                String name = entry.getName( );
                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    String dirPath = destDirPath + "/" + entry.getName();
                    File dir = new File(dirPath);
                    dir.mkdirs();
                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    File targetFile = new File(destDirPath + "/" + entry.getName());
                    // 保证这个文件的父文件夹必须要存在
                    if(!targetFile.getParentFile().exists()){
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();

                    String newName=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())+ RandomStringUtils.randomNumeric(6)
                            +name.substring(name.lastIndexOf("."));

                    map=new HashMap<>();
                    map.put("filterRealName",name);
                    map.put("filterSaveName",newName);

                    // 将压缩文件内容写入到这个文件中
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    int len;
                    byte[] buf = new byte[1024];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }

                    list.add(map);
                    // 关流顺序，先打开的后关闭
                    fos.close();
                    is.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("unzip error from ZipUtils", e);
        } finally {
            if(zipFile != null){
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    //导出 excel 工具类
    public static void xlsDownloadFile(HttpServletResponse response, Workbook wb) {
        OutputStream os = null;
        try {
            os = response.getOutputStream(); //重点突出(特别注意),通过response获取的输出流，作为服务端向客户端浏览器输出内容的通道
            // 处理下载文件名的乱码问题(根据浏览器的不同进行处理)
            response.reset(); // 重点突出
            response.setCharacterEncoding("UTF-8"); // 重点突出
            response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
            response.setHeader("Content-Disposition", "attachment;filename="+ UUID.randomUUID().toString()+".xlsx");// 重点突出
            wb.write(os);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        } finally {
            // 特别重要
            // 1. 进行关闭是为了释放资源
            // 2. 进行关闭会自动执行flush方法清空缓冲区内容
            try {
                if (null != os) {
                    os.close();
                    os = null;
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
    }

    //导出pdf 工具类
    public static void pdfDownloadFile(HttpServletResponse response, String htmlContent) {
        OutputStream os = null;
        com.itextpdf.text.Document document = null;
        PdfWriter writer = null;
        try {
            os = response.getOutputStream(); //重点突出(特别注意),通过response获取的输出流，作为服务端向客户端浏览器输出内容的通道
            // 处理下载文件名的乱码问题(根据浏览器的不同进行处理)
            response.reset(); // 重点突出
            response.setCharacterEncoding("UTF-8"); // 重点突出
            response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
            response.setHeader("Content-Disposition", "attachment;filename="+ UUID.randomUUID().toString()+".pdf");// 重点突出
            // step 1
            document = new com.itextpdf.text.Document();
            // step 2
            writer = PdfWriter.getInstance(document, os);
            // step 3
            document.open();
            // step 4
            XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
            fontImp.register("simhei.ttf");
            XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                    new ByteArrayInputStream(htmlContent.getBytes("utf-8")), null, Charset.forName("UTF-8"), fontImp);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        } finally {
            // 特别重要
            // 1. 进行关闭是为了释放资源
            // 2. 进行关闭会自动执行flush方法清空缓冲区内容
            if (null != document) {
                document.close();
                document = null;
            }
            if (null != writer) {
                writer.close();
                writer = null;
            }
            if (null != os) {
                try {
                    os.close();
                    os = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //导出 word 工具类
    public static void downloadFile(HttpServletRequest request, HttpServletResponse response, File file) {

        BufferedInputStream bis = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedOutputStream bos = null;
        try {
            String fileName = file.getName();
            is = new FileInputStream(file);  //文件流的声明
            os = response.getOutputStream(); //重点突出(特别注意),通过response获取的输出流，作为服务端向客户端浏览器输出内容的通道
            // 为了提高效率使用缓冲区流
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(os);
            // 处理下载文件名的乱码问题(根据浏览器的不同进行处理)
            if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
                fileName = new String(fileName.getBytes("GB2312"),"ISO-8859-1");
            } else {
                // 对文件名进行编码处理中文问题
                fileName = java.net.URLEncoder.encode(fileName, "UTF-8");// 处理中文文件名的问题
                fileName = new String(fileName.getBytes("UTF-8"), "GBK");// 处理中文文件名的问题
            }
            response.reset(); // 重点突出
            response.setCharacterEncoding("UTF-8"); // 重点突出
            response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);// 重点突出
            int bytesRead = 0;
            byte[] buffer = new byte[4096];
            while ((bytesRead = bis.read(buffer)) != -1){ //重点
                bos.write(buffer, 0, bytesRead);// 将文件发送到客户端
                bos.flush();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        } finally {
            // 特别重要
            // 1. 进行关闭是为了释放资源
            // 2. 进行关闭会自动执行flush方法清空缓冲区内容
            try {
                if (null != bis) {
                    bis.close();
                    bis = null;
                }
                if (null != bos) {
                    bos.close();
                    bos = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
                if (null != os) {
                    os.close();
                    os = null;
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
    }
}
