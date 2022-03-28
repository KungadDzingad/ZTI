package pl.karinawojtek.ztiserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.karinawojtek.ztiserver.dao.AuctionRepository;
import pl.karinawojtek.ztiserver.exception.ObjectByIdNotFoundException;
import pl.karinawojtek.ztiserver.models.database.Auction;

import java.util.List;
import java.util.Optional;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository repository;

    public List<Auction> findAllAuctions() {
        return (List<Auction>) repository.findAll();
    }

    public Auction findAuctionById(long id) throws ObjectByIdNotFoundException {
        Optional<Auction> auction = repository.findById(id);
        if(auction.isEmpty())
            throw new ObjectByIdNotFoundException();
        return auction.get();
    }
}
