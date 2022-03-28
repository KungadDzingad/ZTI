package pl.karinawojtek.ztiserver.models.database;

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

    private boolean licitable;

    private String description;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false,insertable = false, updatable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User highestBidder;

    @Temporal(TemporalType.DATE)
    private Date publicationDate;

    @Temporal(TemporalType.DATE)
    private Date closingDate;

    @OneToOne
    @JoinColumn(name="auction", nullable = true)
    private Order order;

    @OneToMany(mappedBy = "reviewedAuction" )
    private List<AuctionReview> reviews = new ArrayList<>();

    @ManyToMany
    private List<User> favorites = new ArrayList<>();

}
