package pl.karinawojtek.ztiserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.karinawojtek.ztiserver.dao.AuctionRepository;
import pl.karinawojtek.ztiserver.exception.custom.ObjectByIdNotFoundException;
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
    private AuctionRepository auctionRepository;


    public List<Auction> findAllAuctions() {
        return (List<Auction>) auctionRepository.findAll();
    }

    public Auction findAuctionById(long id) throws ObjectByIdNotFoundException {
        Optional<Auction> auction = auctionRepository.findById(id);
        if(auction.isEmpty())
            throw new ObjectByIdNotFoundException("No Auction with id " + id);
        return auction.get();
    }

    public void createAuction(CreateAuctionRequest createAuction, User user) throws ParseException {
        Auction auction = new Auction();
        auction.setName(createAuction.getName());
        auction.setDescription(createAuction.getDescription());
        Date date = new Date();

         date = new FromStringToDateFormatter().parseDate(createAuction.getClosingDate());

        auction.setClosingDate(date);
        auction.setBuyNowPrice(createAuction.getBuyNowPrice());
        auction.setOwner(user);
        auction.setCurrentPrice(createAuction.getCurrentPrice());
        auction.setPublicationDate(new Date());

        auctionRepository.save(auction);
    }

    public Auction getAuctionById(long id) throws ObjectByIdNotFoundException{
        Optional<Auction> auctionOptional = auctionRepository.findById(id);
        if(auctionOptional.isEmpty()) throw new ObjectByIdNotFoundException("No Auction with id " + id);

        return auctionOptional.get();
    }

    public void deleteAuction(Auction auction) {
        auctionRepository.delete(auction);
    }


}
