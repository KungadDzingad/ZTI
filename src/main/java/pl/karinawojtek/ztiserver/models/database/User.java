package pl.karinawojtek.ztiserver.models.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;
    private String name;
    @Column(unique = true)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name="user_role_id", nullable=false)
    private UserRole role;

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
