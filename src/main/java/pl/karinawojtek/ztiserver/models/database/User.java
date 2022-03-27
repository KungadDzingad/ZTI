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
    private String password;
    private String name;
    @Temporal(TemporalType.DATE)
    private Date publicationDate;

    @OneToMany(mappedBy = "owner")
    private List<Auction> auctions = new ArrayList<>();

    @OneToMany(mappedBy = "highestBidder")
    private List<Auction> bids= new ArrayList<>();

    @OneToMany
    private List<Auction> favourites= new ArrayList<>();

    @OneToMany
    private List<UserReview> opinions = new ArrayList<>();

    @OneToMany(mappedBy = "purchaser")
    private List<Order> orderHistory = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    private List<Review> createReviews = new ArrayList<>();

    @OneToMany(mappedBy = "reviewedUser")
    private List<Review> myReviews = new ArrayList<>();


}
