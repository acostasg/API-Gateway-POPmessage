package api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SessionTest extends AbstractTest {

    @Test
    public void test_login() {
        String responseMsg = this.target.path("session/token")
                .request()
                .header("Authorization","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
                .get(String.class);
        assertEquals("Success!", responseMsg);
    }

}
