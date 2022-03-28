package pl.karinawojtek.ztiserver.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.karinawojtek.ztiserver.models.database.Auction;
import pl.karinawojtek.ztiserver.services.AuctionService;

import java.util.List;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

    @Autowired
    private AuctionService service;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<Auction> getAllAuctions(){
        return service.findAllAuctions();
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Auction getAuctionById(@PathVariable long id){
        return service.findAuctionById(id);
    }

}
