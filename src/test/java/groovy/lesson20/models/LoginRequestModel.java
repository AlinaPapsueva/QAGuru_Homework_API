package groovy.lesson20.models;

import lombok.Data;

@Data
public class LoginRequestModel {

    private String email;
    private String password;
}
