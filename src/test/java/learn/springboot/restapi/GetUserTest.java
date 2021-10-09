package learn.springboot.restapi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedHashMap;
import learn.springboot.restapi.User.GENDER;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GetUserTest extends AbstractTestNGSpringContextTests {

    @LocalServerPort
    private int port;

    private TestRestTemplate testRestTemplate = new TestRestTemplate();


    @BeforeClass
    public void setUp() {
        String url = "http://localhost:" + port + "/api/createAndReturnId";
        User createdUser =  User.getTestUser();
        testRestTemplate.postForEntity(url, createdUser, Integer.class);
    }

    @Test
    void testGetUserSuccess() {
        String url = "http://localhost:" + port + "/api/user/1";
        ResponseEntity<User> userResponseEntity = testRestTemplate.getForEntity(url, User.class);
        User receivedUser = userResponseEntity.getBody();
        assertThat(receivedUser.getName()).isEqualTo("A");
    }

    @Test
    void testGetUserFailure() {
        String url = "http://localhost:" + port + "/api/user/2";
        ResponseEntity<User> userResponseEntity = testRestTemplate.getForEntity(url, User.class);
        assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(userResponseEntity.getHeaders().containsKey("userId")).isTrue();
        assertThat(userResponseEntity.getHeaders().get("userId")).isNotNull();
        assertThat(userResponseEntity.getHeaders().get("userId").get(0)).isEqualTo("NOT_FOUND");
        User receivedUser = userResponseEntity.getBody();
        assertThat(receivedUser).isNull();
    }


    @Test
    void testListHeaders() {
        String url = "http://localhost:" + port + "/api/listHeaders";
        HttpHeaders headers = new HttpHeaders();
        headers.add("my-custom-header", "10");
        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity<Object> responseEntity = testRestTemplate
            .exchange(url, HttpMethod.POST, httpEntity, Object.class);
        assertThat(responseEntity.getBody()).isExactlyInstanceOf(LinkedHashMap.class);
    }


}
