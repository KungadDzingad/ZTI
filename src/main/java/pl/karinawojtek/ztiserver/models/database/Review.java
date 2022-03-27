package pl.karinawojtek.ztiserver.models.database;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue
    private long id;
    private String description;
    private int mark;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User creator;

}
