package com.github.es.test;

import com.github.es.dao.ProductDao;
import com.github.es.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author HAN
 * @version 1.0
 * @create 05-18-21:18
 */
@SpringBootTest
public class EsDocumentTest {

    @Autowired
    private ProductDao productDao;

    // 插入
    @Test
    public void save(){
        Product product = new Product();
        product.setId(1L);
        product.setTitle("华为手机");
        product.setCategory("手机");
        product.setPrice(2999.0);
        productDao.save(product);
    }
    // 修改
    @Test
    public void update(){
        Product product = new Product();
        product.setId(1L);
        product.setTitle("小米 2 手机");
        product.setCategory("手机");
        product.setPrice(9999.0);
        productDao.save(product);
    }

    // 根据 id 查询
    @Test
    public void findById(){
        Product product = productDao.findById(1L).orElseThrow();
        System.out.println(product);
    }

    // 查询所有
    @Test
    public void findAll(){
        Iterable<Product> products = productDao.findAll();
        for (Product product : products) {
            System.out.println(product);
        }
    }
}
