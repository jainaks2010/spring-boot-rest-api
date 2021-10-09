package learn.springboot.restapi;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Slf4j
public class CreateUserTest extends AbstractTestNGSpringContextTests {

    @LocalServerPort
    private int port;

    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Test
    void testUserCreationAndReturnOK() {
        String url = "http://localhost:" + port + "/api/createAndReturnOK";
        User user = User.getTestUser();
        ResponseEntity<String> responseEntity = testRestTemplate.postForEntity(url, user, String.class);
        String body = responseEntity.getBody();
        assertThat(body).isEqualTo("OK");
    }

    @Test
    void testUserCreationAndReturnId() {
        String url = "http://localhost:" + port + "/api/createAndReturnId";
        User user = User.getTestUser();
        ResponseEntity<Integer> responseEntity = testRestTemplate.postForEntity(url, user, Integer.class);
        Integer userId = responseEntity.getBody();
        assertThat(userId).isGreaterThanOrEqualTo(0);
    }


    @Test
    void testUserCreationByJson() throws IOException {
        String url = "http://localhost:" + port + "/api/createAndReturnId";
        String userJson = Files.readString(Path.of("./src/test/resources/test.txt"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity(userJson, headers);
        ResponseEntity<Integer> responseEntity = testRestTemplate.postForEntity(url, httpEntity, Integer.class);
        Integer userId = responseEntity.getBody();
        assertThat(userId).isGreaterThanOrEqualTo(0);
    }

    @Test
    void testUserCreationInGoRestDotCoDotInByJson() throws IOException {
        String url = "https://gorest.co.in/public/v1/users";
        String userJson = Files.readString(Path.of("./src/test/resources/test.txt"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        HttpEntity httpEntity = new HttpEntity(userJson, headers);
        ResponseEntity<Object> responseEntity = testRestTemplate.postForEntity(url, httpEntity, Object.class);
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode.is2xxSuccessful()) {
            Object body = responseEntity.getBody();
            System.out.println(body + body.getClass().getName());
        } else {
            System.out.println(
                "Response:" + statusCode.value() + ":" + statusCode.name() + ":" + statusCode.getReasonPhrase());
        }

    }

    @Test
    void testUserCreationByJsonUsingRequestEntity() throws IOException {
        String url = "http://localhost:" + port + "/api/createAndReturnId";
        String userJson = Files.readString(Path.of("./src/test/resources/test.txt"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity<String> requestEntity = RequestEntity.post(url).headers(headers).body(userJson);
        ResponseEntity<Integer> responseEntity = testRestTemplate.postForEntity(url, requestEntity, Integer.class);
        Integer userId = responseEntity.getBody();
        assertThat(userId).isGreaterThanOrEqualTo(0);
    }


}
