package pl.karinawojtek.ztiserver.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.karinawojtek.ztiserver.models.database.Auction;

@Repository
public interface AuctionRepository extends CrudRepository<Auction, Long> {
}
