package api.infrastucture.elasticSearch;

import api.domain.service.FormatDataService;

import javax.inject.Inject;
import java.util.Date;

class AbstractElasticSearchRepository {

    @Inject
    ElasticSearchClient elasticSearchClient;

    Date getDateExpiration() {
        return FormatDataService.getDateExpiration();
    }

    Date getDateFromString(String dateOfBirth) {
        return FormatDataService.getDateFromString(dateOfBirth);
    }

    String getDateFromDate(Date dateOfBirth) {
        return FormatDataService.getDateFromDate(dateOfBirth);
    }

}
