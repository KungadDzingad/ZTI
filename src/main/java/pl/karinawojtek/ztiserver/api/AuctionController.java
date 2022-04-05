package pl.karinawojtek.ztiserver.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.karinawojtek.ztiserver.models.database.Auction;
import pl.karinawojtek.ztiserver.models.database.User;
import pl.karinawojtek.ztiserver.models.request.CreateAuctionRequest;
import pl.karinawojtek.ztiserver.services.AuctionService;
import pl.karinawojtek.ztiserver.services.UserService;
import pl.karinawojtek.ztiserver.utils.CookieUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private UserService userService;

    @Autowired
    private CookieUtil cookieUtil;

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
    public void createAuction(@RequestBody CreateAuctionRequest createAuction, HttpServletRequest request){
        String username = cookieUtil.getUsernameFromAuthorizationCookie(request);
        User user = userService.getUserByUsername(username);
        auctionService.createAuction(createAuction, user);
    }


}
