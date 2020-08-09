package com.fh.task;

import com.fh.common.MailUtils;
import com.fh.product.mapper.IProductMapper;
import com.fh.product.po.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

//@Component
public class TaskStock {

    @Autowired
    private MailUtils mailUtils;

    @Resource
    private IProductMapper productMapper;

   /* @Scheduled(cron = "0/5 * * * * ? ") // 间隔5秒执行
    public void taskTime() throws Exception {
        System.out.println(new Date());
        mailUtils.sendMail("260006856@qq.com","每5分发送一次","测试成功了吗？");
    }
*/

    /*@Scheduled(cron = "0/30 * * * * ? ") // 间隔30秒执行
    public void taskTimeProductStock() throws Exception {

       List<Product> queryProductStock=productMapper.queryProductStock();
        StringBuilder stringBuilder = new StringBuilder( );

        stringBuilder.append("<body>" +
                "<table border='5px' style='border:solid 1px #E8F2F9;font-size=10px;'>"+
                "<tr><th>商品名称</th><th>商品价格</th><th>商品销量</th><th>商品标题</th></tr>");
        String tr="";
        for (Product product:queryProductStock) {
            stringBuilder.append("<tr><td>"+product.getProductName()+"</td><td>"+product.getPrice()+"</td><td>"+product.getStock()+"</td><td>"+product.getTitle()+"</td></tr>");
        }
        stringBuilder.append("</table></body>");
        String sc=stringBuilder.toString();
        mailUtils.sendMail("260006856@qq.com","没30秒发送一次销量的数据",sc);
    }*/

}
