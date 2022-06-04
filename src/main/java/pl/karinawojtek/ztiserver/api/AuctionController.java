package pl.karinawojtek.ztiserver.api;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.*;
import pl.karinawojtek.ztiserver.exception.custom.AlreadyInFavouritesException;
import pl.karinawojtek.ztiserver.exception.custom.ObjectByIdNotFoundException;
import pl.karinawojtek.ztiserver.exception.custom.WrongReviewMarkException;
import pl.karinawojtek.ztiserver.models.database.Auction;
import pl.karinawojtek.ztiserver.models.database.AuctionReview;
import pl.karinawojtek.ztiserver.models.database.User;
import pl.karinawojtek.ztiserver.models.request.CreateAuctionRequest;
import pl.karinawojtek.ztiserver.models.request.CreateReviewRequest;
import pl.karinawojtek.ztiserver.services.AuctionService;
import pl.karinawojtek.ztiserver.services.ReviewService;
import pl.karinawojtek.ztiserver.services.UserService;
import pl.karinawojtek.ztiserver.utils.CookieUtil;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/auctions/")
@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "http://localhost:3000")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CookieUtil cookieUtil;

    @GetMapping
//    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Auction> getAllAuctions(@RequestParam(required = false)String auction){
        if(auction != null ) return auctionService.findAllAuctionsWithFilter(auction);
        return auctionService.findAllAuctions();
    }

    @GetMapping("{id}")
//    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(code = HttpStatus.OK)
    public Auction getAuctionById(@PathVariable long id) throws ObjectByIdNotFoundException {
        return auctionService.findAuctionById(id);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
//    @CrossOrigin(origins = "*")
    public void createAuction(@RequestBody CreateAuctionRequest createAuction, HttpServletRequest request) throws ParseException, NotFoundException {
        User user = userService.getUserFromAuthorizedRequest(request);
        auctionService.createAuction(createAuction, user);
    }

    @DeleteMapping("{id}")
//    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAuction(HttpServletRequest request, @PathVariable long id) throws AuthorizationServiceException, ObjectByIdNotFoundException, NotFoundException {
       User user = userService.getUserFromAuthorizedRequest(request);
        Auction auction = auctionService.getAuctionById(id);
        if(user.isAdmin() || user.equals(auction.getOwner())){
            auctionService.deleteAuction(auction);
        }else throw new AuthorizationServiceException("User " + user.getUsername() + " not allowed to perform this operation");
    }

    @GetMapping("{id}/reviews")
//    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(code = HttpStatus.OK)
    public List<AuctionReview> getAuctionReviews(@PathVariable long id) throws ObjectByIdNotFoundException {
        Auction auction = auctionService.getAuctionById(id);
        return auction.getReviews();
    }

    @PostMapping("{id}/reviews")
//    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addAuctionReview(HttpServletRequest request, @PathVariable long id,
                                 @RequestBody CreateReviewRequest reviewRequest)
            throws AuthorizationServiceException, WrongReviewMarkException, ObjectByIdNotFoundException, NotFoundException
    {

        Auction auction = auctionService.getAuctionById(id);
        User user = userService.getUserFromAuthorizedRequest(request);

        reviewService.createAuctionReview(auction, user, reviewRequest);
    }

    @PostMapping("{id}/favourites/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addFavouriteAuction(HttpServletRequest request, @PathVariable long id) throws NotFoundException, ObjectByIdNotFoundException, AlreadyInFavouritesException {
        User user = userService.getUserFromAuthorizedRequest(request);
        Auction auction = auctionService.findAuctionById(id);
        userService.addFavourite(user, auction);
    }
}
