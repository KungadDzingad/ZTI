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
import java.util.Objects;

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
    @JsonManagedReference
    private UserRole role;

    @Temporal(TemporalType.DATE)
    private Date registrationDate;

    @OneToMany(mappedBy = "owner")
    @JsonBackReference
    private List<Auction> auctions = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "auction_favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "auction_id")
    )
    @JsonIgnore
    private List<Auction> favourites= new ArrayList<>();

    @OneToMany(mappedBy = "purchaser")
    @JsonIgnore
    private List<Order> orderHistory = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    @JsonIgnore
    private List<Review> createdReviews = new ArrayList<>();

    @OneToMany(mappedBy = "reviewedUser")
    @JsonIgnore
    private List<UserReview> myReviews = new ArrayList<>();

    public boolean isAdmin(){
        return role.getRoleName().equals(UserRole.ADMIN_ROLE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(name, user.name) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(role, user.role) && Objects.equals(registrationDate, user.registrationDate) && Objects.equals(auctions, user.auctions) && Objects.equals(favourites, user.favourites) && Objects.equals(orderHistory, user.orderHistory) && Objects.equals(createdReviews, user.createdReviews) && Objects.equals(myReviews, user.myReviews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, name, phoneNumber, role, registrationDate, auctions, favourites, orderHistory, createdReviews, myReviews);
    }
}
