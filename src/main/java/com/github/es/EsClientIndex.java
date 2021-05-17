package com.github.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

/**
 * @author HAN
 * @version 1.0
 * @create 05-17-20:45
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EsClientIndex {

    // 创建ES客户端
    RestHighLevelClient esClient = new RestHighLevelClient(
            RestClient.builder(
                    new HttpHost("localhost", 9200, "http")
            )
    );

    @AfterAll
    void closeClient() throws IOException {
        // 关闭客户端
        esClient.close();
    }

    // 创建索引
    @Test
    void createIndex() throws IOException {
        // 创建索引
        CreateIndexRequest person = new CreateIndexRequest("person");
        CreateIndexResponse response =
                esClient.indices().create(person, RequestOptions.DEFAULT);

        // 响应状态
        System.out.println(response.isAcknowledged());
    }

    // 查询索引
    @Test
    void searchIndex() throws IOException {
        // 获取索引
        GetIndexRequest person = new GetIndexRequest("person");
        GetIndexResponse response =
                esClient.indices().get(person, RequestOptions.DEFAULT);

        // 响应状态
        System.out.println(response.getAliases());
        System.out.println(response.getMappings());
        System.out.println(response.getSettings());

    }

    // 删除索引
    @Test
    void deleteIndex() throws IOException {
        // 删除索引
        DeleteIndexRequest person = new DeleteIndexRequest("person");
        AcknowledgedResponse response
                = esClient.indices().delete(person, RequestOptions.DEFAULT);

        // 响应状态
        System.out.println(response.isAcknowledged());
    }
}
