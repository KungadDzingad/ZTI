package pl.karinawojtek.ztiserver.models.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class RegisterUserRequest {
    private String username;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
}
