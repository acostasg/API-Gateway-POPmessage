package api.infrastucture.elasticSearch;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    protected Date getDateFromString(String dateOfBirth) {
        DateFormat format = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
        Date date;
        try {
            date = format.parse(dateOfBirth);
        } catch (ParseException e) {
            date = new Date();
        }
        return date;
    }

    protected String getDateFromDate(Date dateOfBirth) {
        String DATE_FORMAT_NOW = "dd/mm/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(dateOfBirth);
    }


}
