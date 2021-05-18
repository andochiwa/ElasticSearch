package com.github.es.test;

import com.github.es.entity.Product;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.Query;

/**
 * @author HAN
 * @version 1.0
 * @create 05-19-3:46
 */
@SpringBootTest
public class EsAdvanceSearchTest {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    // term查询
    @Test
    public void termQuery(){
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("category", "手机");
        Query query = new NativeSearchQuery(termQueryBuilder);
        SearchHits<Product> products = elasticsearchRestTemplate.search(query, Product.class);
        products.forEach(System.out::println);
    }

    // term查询加分页
    @Test
    public void termQueryByPage(){
        int currentPage= 0;
        int pageSize = 5;

        //设置查询分页
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize);
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("category", "手机");
        Query query = new NativeSearchQuery(termQueryBuilder).setPageable(pageRequest);
        SearchHits<Product> products  =
                elasticsearchRestTemplate.search(query,Product.class);
        products.forEach(System.out::println);
    }
}
