package learn.springboot.restapi;


import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestApiErrorResponse {

    private Instant timeStamp = Instant.now();
    private String message;

    public RestApiErrorResponse(String message){
        this.message = message;
    }

}
