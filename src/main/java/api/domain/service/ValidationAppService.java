package api.domain.service;

import api.domain.exceptions.InvalidAppKey;
import api.domain.infrastructure.ConfigRepository;

import javax.inject.Inject;

public class ValidationAppService {

    @Inject
    private ConfigRepository configRepository;

    @Inject
    public ValidationAppService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public void validationKey(String key) throws InvalidAppKey {
        if (!this.configRepository.get("keyApp").equals(key)) {
            throw new InvalidAppKey("Invalid key application");
        }
    }

}
