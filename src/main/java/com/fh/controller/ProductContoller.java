package com.fh.controller;

import com.fh.common.Constant;
import com.fh.common.ServerResponse;
import com.fh.model.DataTableResult;
import com.fh.model.Product;
import com.fh.model.SelectProductParam;
import com.fh.service.ProductService;
import com.fh.utils.FilterUtil;
import com.fh.utils.PDFUtil;
import com.fh.utils.RedisUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("product")
@CrossOrigin
public class ProductContoller {
    @Autowired
    private ProductService productService;

    @RequestMapping("initProduct")
    public String initProduct(){

        return "product/queryProductList";
    }
    @RequestMapping("initAddProduct")
    public String initAddProduct(){

        return "product/addProduct";
    }
    @RequestMapping("initUpdateProduct")
    public String initUpdateProduct(ModelMap modelMap,Product product){
        modelMap.put("porduct",product);
        return "product/updateProduct";
    }

    @RequestMapping("clearCatch")
    @ResponseBody
    public ServerResponse clearCatch(){
        RedisUtils.delKey(Constant.REDIS_PRODUCT_KEY);
        return ServerResponse.success();
    }
    /**
     * 查询商品信息
     * @param selectProductParam
     * @return
     */
    @RequestMapping("queryProductList")
    @ResponseBody
    public DataTableResult queryProductList(SelectProductParam selectProductParam){
        return productService.queryProductList(selectProductParam);
    }

    /**
     * 增加商品信息
     * @param product
     * @return
     */
    @RequestMapping("addProduct")
    @ResponseBody
    public ServerResponse addProduct(Product product)
    {
        return productService.addProduct(product);
    }
    /**
     * 修改商品信息
     * @param product
     * @return
     */
    @RequestMapping("updateProduct")
    @ResponseBody
    public ServerResponse updateProduct(Product product){

        return productService.updateProduct(product);
    }
    /**
     * 回显商品信息
     * @param product
     * @return
     */
    @RequestMapping("getProductById")
    @ResponseBody
    public ServerResponse getProductById(Product product){

        return productService.getProductById(product);
    }
    /**
     * 改变上下架的状态
     * @param product
     * @return
     */
    @RequestMapping("changeStatu")
    @ResponseBody
    public ServerResponse changeStatu(Product product){

        return productService.changeStatu(product);
    }

    /**
     * 改变是否热销的状态
     * @param product
     * @return
     */
    @RequestMapping("changeHotStatu")
    @ResponseBody
    public ServerResponse changeHotStatu(Product product){
        return productService.changeHotStatu(product);
    }


    /**
     * 下载图片
     * @return
     */
    @PostMapping("downFiler")
    public ServerResponse downFiler(Product product, HttpServletResponse response){

        return productService.downFiler(product,response);
    }

    /**
     * 导出pdf
     */
    @RequestMapping("/exprotPdf")
    public void exprotPdf(HttpServletResponse response,SelectProductParam selectProductParam) throws Exception {
        /*// 根据动态条件 获取数据
        List<Product> findProductList=productService.findProductList(selectProductParam);
        // 将其转换为 html
        Configuration configuration = new Configuration( );
        //解决乱码
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassForTemplateLoading(this.getClass(),"/template");
        Template template = configuration.getTemplate("productTemplet.html");
        Map data=new HashMap();
        data.put("title","商品展示列表");
        data.put("productList",findProductList);
        data.put("createDate",new Date());
        StringWriter stringWriter = new StringWriter( );
        template.process(data,stringWriter);
        String htmlContent = stringWriter.toString( );
        FilterUtil.pdfDownloadFile(response,htmlContent);*/
        try {
            List<Product> findProductList=productService.findProductList(selectProductParam);
            Map map=new HashMap<String,Object>();
            map.put("findProductList",findProductList);
            ByteArrayOutputStream baos = PDFUtil.createPDF("/template/productPdf.html", map);
            //设置response文件头
            PDFUtil.renderPdf(response, baos.toByteArray(), "pdf文件");
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 导出work
     */
    @RequestMapping("/exprotWork")
    public void exprotWork(HttpServletResponse response,SelectProductParam selectProductParam,HttpServletRequest request) throws IOException {
        // 根据动态条件 获取数据
        List<Product> findProductList=productService.findProductList(selectProductParam);
        // 将其转换为 html
        Configuration configuration = new Configuration( );
        //解决乱码
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassForTemplateLoading(this.getClass(),"/template");
        Template template = configuration.getTemplate("product.xml");
        Map data=new HashMap();
        data.put("productList",findProductList);
        FileOutputStream out=null;
        OutputStreamWriter osw=null;
        File file = null;
        try {
            file = new File("d:/" + UUID.randomUUID( ).toString( ) + ".docx");
            out= new FileOutputStream(file);
            osw= new OutputStreamWriter(out, "utf-8");
            template.process(data,osw);
            osw.flush();
        } catch (TemplateException e) {
            e.printStackTrace( );
        }finally {
            if(null!=osw){
                osw.close();
                osw=null;
            }
            if(null!=out){
                out.close();
                out=null;
            }
        }
        //下载
        FilterUtil.downloadFile(request,response,file);
        if (file.exists()){
            file.delete();
        }
    }


}
