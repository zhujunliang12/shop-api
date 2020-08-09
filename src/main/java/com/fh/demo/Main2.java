package com.fh.demo;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class Main2 {

    public static void main(String[] args) throws IOException, TemplateException {
        //创建配置
        Configuration configuration = new Configuration( );
        //指定 模板 路径
        configuration.setClassForTemplateLoading(Main2.class,"/template");
        // 获取 模板 文件 内容
        Template template = configuration.getTemplate("productTemplet.html");
        //构造数据
        Map data=new HashMap<>();
        // key 对应模板中的属性
        data.put("userName","sddd");
        // 模板与数据结合
        FileWriter fileWriter = new FileWriter( "d://a");
        template.process(data,fileWriter);


    }
}
