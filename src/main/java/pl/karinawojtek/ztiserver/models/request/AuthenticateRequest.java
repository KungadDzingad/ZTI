package pl.karinawojtek.ztiserver.models.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticateRequest {
    private String username;
    private String password;

}
