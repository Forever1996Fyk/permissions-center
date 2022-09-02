package com.ah.cloud.permissions.elsticsearch.infrastructure.config;

import com.ah.cloud.permissions.elsticsearch.application.manager.DocResponseManager;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.impl.BulkDocResponseHandler;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.impl.CreateDocResponseHandler;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.impl.DeleteDocResponseHandler;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.impl.UpdateDocResponseHandler;
import lombok.Data;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-25 14:22
 **/
@Data
@Configuration
public class ElasticSearchConfig {
    private static final String HTTP_SCHEME = "http";

    /**
     * es服务端连接地址
     */
    @Value(value = "${elasticsearch.hostname}")
    private String hostName;

    /**
     * es服务端口号
     */
    @Value(value = "${elasticsearch.port}")
    private int port;

    /**
     * 认证用户名
     */
    @Value(value = "${elasticsearch.user:}")
    private String userName;

    /**
     * 认证密码
     */
    @Value(value = "${elasticsearch.password:}")
    private String password;

    /**
     * 需要认证的es
     * @return
     */
    @Bean
    public RestHighLevelClient restHighLevelClientCredential() {
        // 构建es认证提供实体,
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        // 采用es用户名密码认证方式
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(this.userName, this.password));
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(this.hostName, this.port, HTTP_SCHEME))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        return new RestHighLevelClient(restClientBuilder);
    }

    /**
     * 不需要认证的es
     * @return
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(this.hostName, this.port, HTTP_SCHEME));
        return new RestHighLevelClient(restClientBuilder);
    }

    /**
     * 构造 文档响应处理器bean
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DocResponseManager docResponseManager() {
        DocResponseManager manager = new DocResponseManager();
        manager.add(new CreateDocResponseHandler<>());
        manager.add(new DeleteDocResponseHandler());
        manager.add(new UpdateDocResponseHandler<>());
        manager.add(new BulkDocResponseHandler());
        return manager;
    }
}
