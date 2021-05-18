package com.github.es.test;

import com.github.es.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author HAN
 * @version 1.0
 * @create 05-18-21:18
 */
@SpringBootTest
public class EsSearchTest {

    @Autowired
    private ProductDao productDao;

}
