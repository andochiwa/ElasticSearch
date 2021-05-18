package com.github.es.test;

import com.github.es.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

/**
 * 索引操作
 * @author HAN
 * @version 1.0
 * @create 05-18-21:10
 */
@SpringBootTest
public class EsIndexTest {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    // 创建索引
    @Test
    void createIndex() {
        System.out.println("创建索引");
    }

    // 删除索引
    @Test
    void deleteIndex() {
        elasticsearchRestTemplate.indexOps(Product.class).delete();
        System.out.println("删除索引");
    }
}
