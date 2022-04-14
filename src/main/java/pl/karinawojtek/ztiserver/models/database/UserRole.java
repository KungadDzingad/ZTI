package pl.karinawojtek.ztiserver.models.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserRole {

    public static String USER_ROLE = "USER_ROLE";
    public static String ADMIN_ROLE = "ADMIN_ROLE";

    @Id
    private String roleName;

    @OneToMany(mappedBy = "role")
    @JsonBackReference
    private List<User> users = new ArrayList<>();
}
