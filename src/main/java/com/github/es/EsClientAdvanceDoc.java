package com.github.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

/**
 * @author HAN
 * @version 1.0
 * @create 05-18-0:35
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EsClientAdvanceDoc {

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

    // 批量插入
    @Test
    void batchQuery() throws IOException {
        // 批量插入数据
        BulkRequest request = new BulkRequest();

        request.add(new IndexRequest().id("1").index("person")
                .source(XContentType.JSON, "name", "zhangsan", "age", "15"));
        request.add(new IndexRequest().id("2").index("person")
                .source(XContentType.JSON, "name", "lisi", "age", "16"));
        request.add(new IndexRequest().id("3").index("person")
                .source(XContentType.JSON, "name", "wangwu", "age", "17"));
        request.add(new IndexRequest().id("4").index("person")
                .source(XContentType.JSON, "name", "lisi", "age", "17"));
        request.add(new IndexRequest().id("5").index("person")
                .source(XContentType.JSON, "name", "wangwu", "age", "16"));
        request.add(new IndexRequest().id("6").index("person")
                .source(XContentType.JSON, "name", "zhangsan", "age", "18"));

        BulkResponse response = esClient.bulk(request, RequestOptions.DEFAULT);

        System.out.println(response);
    }

    // 批量删除
    @Test
    void batchDelete() throws IOException {
        BulkRequest request = new BulkRequest();

        request.add(new DeleteRequest("person", "1"));
        request.add(new DeleteRequest("person", "2"));
        request.add(new DeleteRequest("person", "3"));

        BulkResponse response = esClient.bulk(request, RequestOptions.DEFAULT);

        System.out.println(response);
    }

    // 查询索引中所有数据: matchAllQuery
    @Test
    void queryAll() throws IOException {
        // 全量查询
        SearchRequest request = new SearchRequest("person");

        SearchSourceBuilder query =
                new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());

        request.source(query);

        SearchResponse response
                = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println(response);
        SearchHits hits = response.getHits();
        hits.forEach(item -> System.out.println(item.getSourceAsString()));
    }

    // 条件查询: termQuery
    @Test
    void conditionQuery() throws IOException {
        SearchRequest request = new SearchRequest("person");

        SearchSourceBuilder query =
                new SearchSourceBuilder().query(QueryBuilders.termQuery("age", "17"));

        request.source(query);

        SearchResponse response
                = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println(response);
        SearchHits hits = response.getHits();
        hits.forEach(item -> System.out.println(item.getSourceAsString()));

    }

    // 分页, 排序, 过滤字段: from()、size() sort() fetchSource
    @Test
    void paginationSortFetchQuery() throws IOException {
        SearchRequest request = new SearchRequest("person");

        SearchSourceBuilder query = new SearchSourceBuilder()
                .query(QueryBuilders.matchAllQuery())
                .from(0).size(3)
                .sort("age", SortOrder.DESC)
                .fetchSource("age", "name");

        request.source(query);

        SearchResponse response
                = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println(response);
        SearchHits hits = response.getHits();
        hits.forEach(item -> System.out.println(item.getSourceAsString()));
    }

    // 组合查询: must(), mustNot(), should()
    @Test
    void mustQuery() throws IOException {
        SearchRequest request = new SearchRequest("person");

        // 组合查询
        SearchSourceBuilder query = new SearchSourceBuilder()
                .query(QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("age", "17"))
                        .must(QueryBuilders.matchQuery("name", "lisi")));


        request.source(query);

        SearchResponse response
                = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println(response);
        SearchHits hits = response.getHits();
        hits.forEach(item -> System.out.println(item.getSourceAsString()));
    }

    // 范围查询
    @Test
    void rangeQuery() throws IOException {
        SearchRequest request = new SearchRequest("person");

        // 范围查询
        SearchSourceBuilder query = new SearchSourceBuilder()
                .query(QueryBuilders.rangeQuery("age")
                        .gte(15)
                        .lte(17))
                .sort("age");


        request.source(query);

        SearchResponse response
                = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println(response);
        SearchHits hits = response.getHits();
        hits.forEach(item -> System.out.println(item.getSourceAsString()));
    }
}
