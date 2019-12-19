package com.microsoft.azure.config;
import com.azure.search.ApiKeyCredentials;
import com.azure.search.SearchIndexClient;
import com.azure.search.SearchIndexClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * SearchConfig
 */
@Configuration

public class ClientConfiguration {
    private  String url;
    private  String key;
    private  String index;

    @Autowired
    public ClientConfiguration(@Value("${azure.search.url}") String url, @Value("${azure.search.key}") String key, @Value("${azure.search.hotel.index}") String index) {
        this.url = url;
        this.key = key;
        this.index = index;
    }

    public SearchIndexClient client() {
        SearchIndexClient client = new SearchIndexClientBuilder()
                .endpoint(url)
                .credential(new ApiKeyCredentials(key))
                .indexName(index)
                .buildClient();
        return client;
    }


}
