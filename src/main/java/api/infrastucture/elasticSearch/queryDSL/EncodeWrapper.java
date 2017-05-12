package api.infrastucture.elasticSearch.queryDSL;

import org.glassfish.jersey.internal.util.Base64;

public class EncodeWrapper {

    private String text;
    //TODO add service to encryte with secret key
    EncodeWrapper(String text) {
        this.text = text;
    }

    private String Encode(){
        return new String(Base64.encode(this.text.getBytes()));
    }

    public static String Encoder(String text){
        return new EncodeWrapper(text).Encode();
    }
}
