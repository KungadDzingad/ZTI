package pl.karinawojtek.ztiserver.models.database;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    @Temporal(TemporalType.DATE)
    private Date registrationDate;

    @OneToMany(mappedBy = "owner")
    private List<Auction> auctions = new ArrayList<>();

    @OneToMany(mappedBy = "highestBidder")
    private List<Auction> bids = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "auction_favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "auction_id")
    )
    private List<Auction> favourites= new ArrayList<>();

    @OneToMany(mappedBy = "purchaser")
    private List<Order> orderHistory = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    private List<Review> createdReviews = new ArrayList<>();

    @OneToMany(mappedBy = "reviewedUser")
    private List<UserReview> myReviews = new ArrayList<>();


}
