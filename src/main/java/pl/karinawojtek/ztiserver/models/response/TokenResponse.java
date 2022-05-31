package pl.karinawojtek.ztiserver.models.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class TokenResponse {
    private String token;

    public TokenResponse(String token){
        this.token = token;
    }
}
