package pl.karinawojtek.ztiserver.api;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.karinawojtek.ztiserver.exception.custom.ObjectByIdNotFoundException;
import pl.karinawojtek.ztiserver.exception.custom.UnauthorizedException;
import pl.karinawojtek.ztiserver.exception.custom.WrongReviewMarkException;
import pl.karinawojtek.ztiserver.models.database.Auction;
import pl.karinawojtek.ztiserver.models.database.Review;
import pl.karinawojtek.ztiserver.models.database.User;
import pl.karinawojtek.ztiserver.models.database.UserReview;
import pl.karinawojtek.ztiserver.models.request.ChangePasswordRequest;
import pl.karinawojtek.ztiserver.models.request.CreateReviewRequest;
import pl.karinawojtek.ztiserver.models.request.RegisterUserRequest;
import pl.karinawojtek.ztiserver.models.response.UsernameResponse;
import pl.karinawojtek.ztiserver.services.AuctionService;
import pl.karinawojtek.ztiserver.services.ReviewService;
import pl.karinawojtek.ztiserver.services.UserService;
import pl.karinawojtek.ztiserver.utils.CookieUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private AuctionService auctionService;

    @Autowired
    private CookieUtil cookieUtil;

    @PostMapping
//    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createUser(@RequestBody RegisterUserRequest registerUserRequest){

        userService.registerUser(registerUserRequest);
    }

    @GetMapping
//    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(code = HttpStatus.OK)
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping ("/{id}")
//    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(code = HttpStatus.OK)
    public User getUserById(@PathVariable long id) throws ObjectByIdNotFoundException {
        return userService.getUserById(id);
    }

    @PostMapping ("/password-change")
//    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(code = HttpStatus.OK)
    public void changePassword(HttpServletRequest request, @RequestBody ChangePasswordRequest changePasswordRequest) throws NotFoundException {
       String username = cookieUtil.getUsernameFromAuthorizationCookie(request);
       User user = userService.getUserByUsername(username);
       userService.changePassword(user,changePasswordRequest);

    }

    @GetMapping("/{id}/reviews")
//    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(code = HttpStatus.OK)
    public List<UserReview> getUserReviewed(@PathVariable long id) throws ObjectByIdNotFoundException {
        User user = userService.getUserById(id);
        return user.getMyReviews();
    }

    @PostMapping("/{id}/reviews")
//    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createUserReview(HttpServletRequest request, @RequestBody CreateReviewRequest createReviewRequest,
                                 @PathVariable long id) throws WrongReviewMarkException, ObjectByIdNotFoundException, NotFoundException {
        User user = userService.getUserFromAuthorizedRequest(request);
        User reviewed = userService.getUserById(id);
        reviewService.createUserReview(user,reviewed, createReviewRequest);
    }

    @GetMapping("/me/favourites")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Auction> getUserFavourites(HttpServletRequest request) throws NotFoundException, UnauthorizedException {
        User user = userService.getUserFromAuthorizedRequest(request);
        return user.getFavourites();
    }

    @GetMapping("/me/auctions")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Auction> getUserAuctions(HttpServletRequest request) throws ObjectByIdNotFoundException, NotFoundException {
        User user = userService.getUserFromAuthorizedRequest(request);
        return userService.getUserAuctions(user);
    }

    @DeleteMapping("/me/auctions/{id}/")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void getUserAuctions(HttpServletRequest request, @PathVariable long id) throws ObjectByIdNotFoundException, NotFoundException, UnauthorizedException {
        User user = userService.getUserFromAuthorizedRequest(request);
        Auction auction = auctionService.findAuctionById(id);
        if(auction.getOwner().getId() == user.getId()) auctionService.deleteAuction(auction);
        else throw new UnauthorizedException();
    }

    @GetMapping("/me/username")
    public UsernameResponse getLoggedUserUsername(HttpServletRequest request) throws NotFoundException {
        User user = userService.getUserFromAuthorizedRequest(request);
        return new UsernameResponse(user.getUsername());
    }

}
