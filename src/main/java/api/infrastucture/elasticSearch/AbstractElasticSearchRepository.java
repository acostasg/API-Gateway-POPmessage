package api.infrastucture.elasticSearch;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;

public class AbstractElasticSearchRepository {

    @Inject
    ElasticSearchClient elasticSearchClient;

    protected void stopConnection() {
        this.elasticSearchClient.stopConnection();
    }

    protected void startConnection() {
        this.elasticSearchClient.startConnection();
    }

    protected Date getDateExpiration() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 6);

        return cal.getTime();
    }

}
