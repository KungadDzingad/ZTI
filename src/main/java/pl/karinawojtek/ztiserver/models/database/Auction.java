package pl.karinawojtek.ztiserver.models.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    @JsonManagedReference
    private User owner;

    @Temporal(TemporalType.DATE)
    private Date publicationDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private Date closingDate;

    @OneToOne
    @JoinColumn(name="order_id", nullable = true)
    private Order order;

    @OneToMany(mappedBy = "reviewedAuction" ,cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<AuctionReview> reviews = new ArrayList<>();

    @ManyToMany(mappedBy = "favourites")
    @JsonIgnore
    private List<User> favorites = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auction auction = (Auction) o;
        return id == auction.id && Objects.equals(name, auction.name) && Objects.equals(description, auction.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
