package api.infrastucture.elasticSearch;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import javax.inject.Inject;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ElasticSearchClient {

    private static final String HOST_1 = "es-proxy";
    private static final String HOST_2 = "http://0.0.0.0:17560";
    private static final int PORT_1 = 9200;
    private static final int PORT_2 = 17560;

    private String index;
    private String type;

    private static final int from = 0;
    private static final int size = 10;

    private TransportClient client;

    @Inject
    public ElasticSearchClient(){}

    public ElasticSearchClient startConnection() throws UnknownHostException {
        this.client = new PreBuiltTransportClient(Settings.EMPTY)
                //.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(HOST_1), PORT_1))
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(HOST_2), PORT_2));
        return this;
    }

    public ElasticSearchClient prepareSearch(String index){
        this.index = index;
        return this;
    }

    public ElasticSearchClient setType(String type){
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

    public GetResponse get(String id, String index, String type)
    {
        return client.prepareGet(index,type,id).get();
    }

    public void stopConnectoion() {
        this.client.close();
    }

}
