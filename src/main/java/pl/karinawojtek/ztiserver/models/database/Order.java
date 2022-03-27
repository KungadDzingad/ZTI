package pl.karinawojtek.ztiserver.models.database;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User purchaser;

    @OneToOne
    private Auction auction;

}
