package pl.karinawojtek.ztiserver.models.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UsernameResponse {

    private String username;

    public UsernameResponse(String username){
        this.username = username;
    }

}
