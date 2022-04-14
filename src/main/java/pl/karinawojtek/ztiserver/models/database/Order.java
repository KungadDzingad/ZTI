package pl.karinawojtek.ztiserver.models.database;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "auction_order")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    @JsonManagedReference
    private User purchaser;

    @OneToOne
    private Auction auction;

}
