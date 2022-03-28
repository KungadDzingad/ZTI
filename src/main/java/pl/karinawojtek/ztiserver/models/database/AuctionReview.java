package pl.karinawojtek.ztiserver.models.database;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AuctionReview extends Review {

    @ManyToOne
    @JoinColumn(name = "auction_id", nullable = false,insertable = false, updatable = false)
    private Auction reviewedAuction;
}
