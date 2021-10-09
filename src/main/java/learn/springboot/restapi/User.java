package learn.springboot.restapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    public enum GENDER {
        MALE,FEMALE
    }

    private String name;

    private String email;

    private String status;

    private GENDER gender;


    public static User getTestUser(){
        return new User("A", "a@B.com", "active", GENDER.MALE);
    }

}
