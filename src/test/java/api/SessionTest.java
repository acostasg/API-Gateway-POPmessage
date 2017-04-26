package api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SessionTest extends AbstractTest {

    @Test
    public void test_login() {
        String responseMsg = this.target.path("session/token")
                .queryParam("token","sakjfh97325437hskfsdfd_sdkjfsjf1283763339564921734sdfbdsj")
                .request()
                .header("Authorization","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
                .get(String.class);
        assertEquals("{\"Token\":{\"hash\":\"sakjfh97325437hskfsdfd_sdkjfsjf1283763339564921734sdfbdsj\"}}", responseMsg);
    }

}
