package com.github.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.entity.Person;
import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

/**
 * @author HAN
 * @version 1.0
 * @create 05-17-21:19
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EsClientDoc {

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

    // 插入
    @Test
    void insert() throws IOException {
        // 插入数据
        IndexRequest request = new IndexRequest();
        request.index("person").id("1");
        // 插入的对象
        Person mary = new Person("mary", 18);
        // 转成json
        ObjectMapper mapper = new ObjectMapper();
        String maryJson = mapper.writeValueAsString(mary);
        // 把数据放入body里
        request.source(maryJson, XContentType.JSON);

        // 发送数据
        IndexResponse response
                = esClient.index(request, RequestOptions.DEFAULT);

        // 响应结果
        System.out.println(response);
        System.out.println(response.getId());
        System.out.println(response.getIndex());
    }

    // 更新
    @Test
    void update() throws Exception {
        UpdateRequest request = new UpdateRequest("person", "1");
        // 修改的内容
        request.doc(XContentType.JSON, "age", "16");

        // 发送请求
        UpdateResponse response =
                esClient.update(request, RequestOptions.DEFAULT);

        // 响应状态
        System.out.println(response);
    }

    // 查询
    @Test
    void query() throws Exception {
        GetRequest request = new GetRequest("person", "1");
        // 发送请求
        GetResponse response =
                esClient.get(request, RequestOptions.DEFAULT);

        System.out.println(response);
    }

    // 删除
    @Test
    void delete() throws Exception {
        DeleteRequest request = new DeleteRequest("person", "1");

        DeleteResponse response
                = esClient.delete(request, RequestOptions.DEFAULT);

        System.out.println(response);
    }
}
