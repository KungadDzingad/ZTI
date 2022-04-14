package pl.karinawojtek.ztiserver.models.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Auction {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    private double currentPrice;

    private double buyNowPrice;

    private String description;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    @JsonBackReference
    private User owner;

    @Temporal(TemporalType.DATE)
    private Date publicationDate;

    @Temporal(TemporalType.DATE)
    private Date closingDate;

    @OneToOne
    @JoinColumn(name="order_id", nullable = true)
    private Order order;

    @OneToMany(mappedBy = "reviewedAuction" )
    @JsonIgnore
    private List<AuctionReview> reviews = new ArrayList<>();

    @ManyToMany
    @JsonIgnore
    private List<User> favorites = new ArrayList<>();


}
