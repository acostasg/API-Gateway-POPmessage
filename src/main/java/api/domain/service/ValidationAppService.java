package api.domain.service;

import api.domain.exceptions.InvalidAppKey;

public class ValidationAppService {

    private final static String key = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";

    public static void validateKeyApp(String key) throws InvalidAppKey
    {
        if(!key.equals(ValidationAppService.key)){
            throw new InvalidAppKey("Invalid key application");
        }
    }

}
