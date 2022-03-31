package pl.karinawojtek.ztiserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.karinawojtek.ztiserver.dao.AuctionRepository;
import pl.karinawojtek.ztiserver.exception.ObjectByIdNotFoundException;
import pl.karinawojtek.ztiserver.models.database.Auction;
import pl.karinawojtek.ztiserver.models.database.User;
import pl.karinawojtek.ztiserver.models.request.CreateAuctionRequest;
import pl.karinawojtek.ztiserver.utils.FromStringToDateFormatter;

import java.text.ParseException;
import java.util.Date;
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

    public void createAuction(CreateAuctionRequest createAuction, User user) {
        Auction auction = new Auction();
        auction.setName(createAuction.getName());
        auction.setDescription(createAuction.getDescription());
        Date date = new Date();
        try {
             date = new FromStringToDateFormatter().parseDate(createAuction.getClosingDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        auction.setClosingDate(date);
        auction.setBuyNowPrice(createAuction.getBuyNowPrice());
        auction.setOwner(user);
        auction.setCurrentPrice(createAuction.getCurrentPrice());
        auction.setPublicationDate(new Date());
        auction.setLicitable(createAuction.isLicitable());

        repository.save(auction);
    }
}
