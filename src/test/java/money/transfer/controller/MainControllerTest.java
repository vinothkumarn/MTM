package money.transfer.controller;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spark.Spark;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainControllerTest {

    private static HttpClient httpClient = new HttpClient();

    @BeforeAll
    public static void setUp() throws Exception {
        MainController mainController = new MainController();
        httpClient.start();
        String[] args = {};
        mainController.main(args);
        Spark.awaitInitialization();
    }

    @AfterAll
    public static void tearDown() {
        Spark.stop();
    }

    @Test
    public void testCreateAccount() throws Exception {
        Request request = httpClient.POST("http://localhost:4567/api/accounts/createAccount");
        request.content(new StringContentProvider("{\"accountOwnerName\":\"vinoth vino\"}"));
        ContentResponse response = request.send();
        assertEquals(HttpStatus.CREATED_201, response.getStatus());
    }

    @Test
    public void shouldRespondErrorMsgForInvalidGetAccount() throws Exception {
        ContentResponse response = httpClient.GET("http://localhost:4567/api/accounts/123");
        assertEquals("Account doesn't exist. Please check the account number", response.getContentAsString());
    }

    @Test
    public void shouldRespondErrorMsgForInvalidTransfers() throws Exception {
        Request request = httpClient.POST("http://localhost:4567/api/accounts/transfer");
        request.content(new StringContentProvider("{\"senderId\":\"123455\", \"receiverId" +
                "\":\"45456546\", \"amount\":\"100\"}"));
        ContentResponse response = request.send();
        assertEquals("Failed Operation.Invalid request data", response.getContentAsString());
    }

    @Test
    public void shouldRespondErrorMsgForNegativeTransfers() throws Exception {
        Request request = httpClient.POST("http://localhost:4567/api/accounts/transfer");
        request.content(new StringContentProvider("{\"senderId\":\"b8e1867c-0768-43c2-acb5-3357f58341d2\", " +
                "\"receiverId" +
                "\":\"60d6ecfb-491f-41fc-84a3-052e216791f2\", \"amount\":\"-99\"}"));
        ContentResponse response = request.send();
        assertEquals("Failed Operation.Invalid request data", response.getContentAsString());
    }
}
