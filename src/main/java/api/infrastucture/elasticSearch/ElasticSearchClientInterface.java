package api.infrastucture.elasticSearch;

import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.SearchResult;

public interface ElasticSearchClientInterface {

    ElasticSearchClientInterface prepareSearch(String index);

    ElasticSearchClientInterface setType(String type);

    SearchResult executeQuery(String searchSource) throws java.io.IOException;

    JestResult get(String id) throws java.io.IOException;

    DocumentResult set(String jsonBuilder, String id) throws java.io.IOException;

    DocumentResult put(String jsonBuilder, String id) throws java.io.IOException;

    JestResult del(String id) throws java.io.IOException;

}
