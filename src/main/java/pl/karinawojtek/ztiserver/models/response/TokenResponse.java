package pl.karinawojtek.ztiserver.models.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class TokenResponse {
    private String token;
    private long id;

    public TokenResponse(String token, long id){
        this.token = token;
        this.id = id;
    }
}
