package learn.springboot.restapi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.LinkedHashMap;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GetUserExceptionTest extends AbstractTestNGSpringContextTests {

    @LocalServerPort
    private int port;

    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Test
    void testGetUserException() {
        String url = "http://localhost:" + port + "/api/userRuntimeException";
        ResponseEntity<User> userResponseEntity = testRestTemplate.getForEntity(url, User.class);
        assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        User receivedUser = userResponseEntity.getBody();
        assertThat(receivedUser).isNotNull();
        assertThat(receivedUser.getName()).isNull();
    }

    @Test
    void testGetUserExceptionByObject() {
        String url = "http://localhost:" + port + "/api/userRuntimeException";
        Object object = testRestTemplate.getForObject(url, Object.class);
        assertThat(object).isExactlyInstanceOf(LinkedHashMap.class);
    }

    @Test
    void testGetUserExceptionByExchange() {
        String url = "http://localhost:" + port + "/api/userRuntimeException";
        HttpEntity httpEntity = new HttpEntity(new HashMap<>());
        ResponseEntity<Object> responseEntity = testRestTemplate
            .exchange(url, HttpMethod.GET, httpEntity, Object.class);
        assertThat(responseEntity.getBody()).isExactlyInstanceOf(LinkedHashMap.class);
    }
}
