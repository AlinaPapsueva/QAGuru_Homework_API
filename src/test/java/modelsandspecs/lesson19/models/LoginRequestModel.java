package modelsandspecs.lesson19.models;

import lombok.Data;

@Data
public class LoginRequestModel {

    private String email;
    private String password;
}
