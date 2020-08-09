package com.fh.service;



import com.fh.common.ServerResponse;
import com.fh.model.DataTableResult;
import com.fh.model.Product;
import com.fh.model.SelectProductParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ProductService {


    DataTableResult queryProductList(SelectProductParam selectProductParam);

    ServerResponse changeStatu(Product product);

    ServerResponse changeHotStatu(Product product);


    ServerResponse addProduct(Product product);

    ServerResponse getProductById(Product product);

    ServerResponse updateProduct(Product product);

    ServerResponse downFiler(Product product, HttpServletResponse response);

    List<Product> findProductList(SelectProductParam selectProductParam);
}
