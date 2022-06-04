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
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

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
//        Date date = new Date();

//         date = new FromStringToDateFormatter().parseDate(createAuction.getClosingDate());

        auction.setClosingDate(null);
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
        var users = auction.getFavorites();
        for (User user : users) {
            user.getFavourites().remove(auction);
        }
        auctionRepository.delete(auction);
    }


    public List<Auction> findAllAuctionsWithFilter(String auctionFilter) {
        return findAllAuctions().stream().filter(s-> s.getDescription().toLowerCase().contains(auctionFilter.toLowerCase()) ||
                s.getName().toLowerCase().contains(auctionFilter.toLowerCase())).collect(Collectors.toList());
    }
}
