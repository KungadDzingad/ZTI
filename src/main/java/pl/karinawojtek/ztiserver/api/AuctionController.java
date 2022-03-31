package pl.karinawojtek.ztiserver.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.karinawojtek.ztiserver.models.database.Auction;
import pl.karinawojtek.ztiserver.models.database.User;
import pl.karinawojtek.ztiserver.models.request.CreateAuctionRequest;
import pl.karinawojtek.ztiserver.services.AuctionService;
import pl.karinawojtek.ztiserver.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private UserService userService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<Auction> getAllAuctions(){
        return auctionService.findAllAuctions();
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Auction getAuctionById(@PathVariable long id){
        return auctionService.findAuctionById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createAuction(@RequestBody CreateAuctionRequest createAuction, @RequestParam long userId){
        User user = userService.getUserById(userId);
        auctionService.createAuction(createAuction, user);
    }


}
