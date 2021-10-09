package learn.springboot.restapi;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@Slf4j
public class TestRestController {

    private Map<Integer, User> usersMap = new HashMap<>();

    private AtomicInteger userId = new AtomicInteger();

    @PostMapping("/createAndReturnOK")
    public String createAndReturnOK(@RequestBody User user) {
        usersMap.put(userId.incrementAndGet(), user);
        return "OK";
    }

    @PostMapping("/createAndReturnId")
    public ResponseEntity<Integer> createAndReturnDetails(@RequestBody User user) {
        var userId = this.userId.incrementAndGet();
        usersMap.put(userId, user);
        log.info("User created with ID:" + userId);
        return ResponseEntity.ok().body(userId);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Integer userId) {
        User user = usersMap.get(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            log.error("User is null with id:" + userId);
            return ResponseEntity.notFound().header("userId", "NOT_FOUND").build();
        }
    }

    @GetMapping("/userRuntimeException")
    public ResponseEntity<User> getUserException() {
        User user = usersMap.get(null);
        Optional.ofNullable(user).orElseThrow(() -> new RuntimeException("Test this exception"));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/listHeaders")
    public ResponseEntity<Map> listHeaders(@RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> log.info("Header " + key + ":=" + value));
        return ResponseEntity.ok(headers);
    }

    @PostMapping("/upload")
    public ResponseEntity<Map> uploadFile(@RequestParam("file") MultipartFile file,
        @RequestHeader Map<String, String> headers) throws IOException {
        log.info("Attempting to Upload File");
        file.transferTo(Path.of("./upload-dir/", file.getOriginalFilename()));
        headers.put("upload", "OK");
        return ResponseEntity.ok(headers);
    }


}
