package com.github.es.dao;

import com.github.es.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author HAN
 * @version 1.0
 * @create 05-18-21:06
 */
@Repository
public interface ProductDao extends ElasticsearchRepository<Product, Long> {
}
