package pl.karinawojtek.ztiserver.models.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jdk.jfr.Name;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserReview extends Review{

    @ManyToOne
    @JoinColumn(name="reviewed_id", nullable=true,insertable = true, updatable = false)
    @JsonBackReference
    private User reviewedUser;

}
