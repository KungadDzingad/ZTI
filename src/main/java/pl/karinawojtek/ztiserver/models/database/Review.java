package pl.karinawojtek.ztiserver.models.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Review_Type")
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
    @JsonBackReference
    private User creator;
}
