package api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SessionTest extends AbstractTest {

    @Test
    public void test_login() {
        String responseMsg = this.target.path("session/token")
                .queryParam("Token", TOKEN_TESTING)
                .request()
                .header("Authorization", APP_KEY_TESTING)
                .get(String.class);
        assertEquals("{\"hash\":\"sakjfh97325437hskfsdfd_sdkjfsjf1283763339564921734sdfbdsj\"}", responseMsg);
    }

}
