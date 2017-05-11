package api.infrastucture.elasticSearch;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import javax.inject.Inject;
import java.net.InetAddress;

public class ElasticSearchClient {

    private static final String HOST_1 = "es-proxy";
    private static final String HOST_2 = "0.0.0.0";
    private static final int PORT_1 = 9200;
    private static final int PORT_2 = 17560;

    private String index;
    private String type;

    private static final int from = 0;
    private static final int size = 10;

    private TransportClient client;

    @Inject
    public ElasticSearchClient() {
    }

    public ElasticSearchClient startConnection() {
        try {
            Settings settings = Settings.builder()
                    .put("cluster.name","popmessage-es-cluster")
                    .put("client.transport.sniff",true).build();
            this.client = new PreBuiltTransportClient(settings)
                    //.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(HOST_1), PORT_1))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(HOST_2), PORT_2));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return this;
    }

    public ElasticSearchClient prepareSearch(String index) {
        this.index = index;
        return this;
    }

    public ElasticSearchClient setType(String type) {
        this.type = type;
        return this;
    }

    public SearchResponse executeQuery(QueryBuilder query) {
        return this.executeQuery(query, from, size);
    }

    public SearchResponse executeQuery(QueryBuilder query, QueryBuilder filter) {
        return this.executeQuery(query, filter, from, size);
    }

    public SearchResponse executeQuery(QueryBuilder query, int from, int size) {
        return this.client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(query)
                .setFrom(from).setSize(size).setExplain(true)
                .get();
    }

    public SearchResponse executeQuery(QueryBuilder query, QueryBuilder filter, int from, int size) {
        return this.client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(query)
                .setPostFilter(filter)
                .setFrom(from).setSize(size).setExplain(true)
                .get();
    }

    public GetResponse get(String id, String index, String type) {
        return client.prepareGet(index, type, id).get();
    }

    public IndexResponse set(String id, String index, String type, XContentBuilder jsonBuilder) {
        return this.client.prepareIndex(index, type, id)
                .setSource(jsonBuilder)
                .get();
    }

    public void stopConnection() {
        this.client.close();
    }

}
