package com.github.es.config;

import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * @author HAN
 * @version 1.0
 * @create 05-18-21:02
 */
@ConfigurationProperties(prefix = "elasticsearch")
@Configuration
@Data
public class EsConfiguration extends AbstractElasticsearchConfiguration {

    private String host;

    private Integer port;

    // 构建es客户端
    @Override
    public RestHighLevelClient elasticsearchClient() {
        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port));
        return new RestHighLevelClient(builder);
    }
}
