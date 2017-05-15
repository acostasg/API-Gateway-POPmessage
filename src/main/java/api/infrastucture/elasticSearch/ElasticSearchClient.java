package api.infrastucture.elasticSearch;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.*;

import javax.inject.Inject;
import javax.inject.Singleton;

public class ElasticSearchClient {

    private static final String HOST = "http://192.168.1.132:17560";
    private static final String HOST_DOCKER = "http://apache-proxy:17560";

    private String index;
    private String type;

    private JestClient client;

    @Inject
    @Singleton
    public ElasticSearchClient() {
        startConnection();
    }


    private void startConnection() {
        try {
            if (null == this.client) {
                JestClientFactory factory = new JestClientFactory();
                factory.setHttpClientConfig(new HttpClientConfig
                        .Builder(HOST)
                        .defaultMaxTotalConnectionPerRoute(5)
                        .maxTotalConnection(30)
                        .multiThreaded(true)
                        .build()
                );
                this.client = factory.getObject();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    ElasticSearchClient prepareSearch(String index) {
        this.index = index;
        return this;
    }

    ElasticSearchClient setType(String type) {
        this.type = type;
        return this;
    }


    SearchResult executeQuery(String searchSource) throws java.io.IOException {
        Search search = new Search.Builder(searchSource)
                .addIndex(this.index)
                .addType(this.type)
                .build();

        return client.execute(search);
    }


    JestResult get(String id) throws java.io.IOException {
        Get get = new Get.Builder(this.index, id).type(this.type).build();

        return client.execute(get);
    }

    DocumentResult set(String jsonBuilder, String id) throws java.io.IOException {
        Index index = new Index.Builder(jsonBuilder).id(id).index(this.index).type(this.type).build();
        return client.execute(index);
    }

    DocumentResult put(String jsonBuilder, String id) throws java.io.IOException {
        return client.execute(new Update.Builder(jsonBuilder).index(this.index).type(this.type).id(id).build());
    }

    JestResult del(String id) throws java.io.IOException {
        return this.client.execute(new Delete.Builder(id).index(this.index).type(this.type).build());
    }

}
