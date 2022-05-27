package pl.karinawojtek.ztiserver.api;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.karinawojtek.ztiserver.exception.custom.ObjectByIdNotFoundException;
import pl.karinawojtek.ztiserver.exception.custom.WrongReviewMarkException;
import pl.karinawojtek.ztiserver.models.database.Review;
import pl.karinawojtek.ztiserver.models.database.User;
import pl.karinawojtek.ztiserver.models.database.UserReview;
import pl.karinawojtek.ztiserver.models.request.ChangePasswordRequest;
import pl.karinawojtek.ztiserver.models.request.CreateReviewRequest;
import pl.karinawojtek.ztiserver.models.request.RegisterUserRequest;
import pl.karinawojtek.ztiserver.services.ReviewService;
import pl.karinawojtek.ztiserver.services.UserService;
import pl.karinawojtek.ztiserver.utils.CookieUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CookieUtil cookieUtil;

    @PostMapping
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createUser(@RequestBody RegisterUserRequest registerUserRequest){

        userService.registerUser(registerUserRequest);
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(code = HttpStatus.OK)
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping ("/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(code = HttpStatus.OK)
    public User getUserById(@PathVariable long id) throws ObjectByIdNotFoundException {
        return userService.getUserById(id);
    }

    @PostMapping ("/password-change")
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(code = HttpStatus.OK)
    public void changePassword(HttpServletRequest request, @RequestBody ChangePasswordRequest changePasswordRequest) throws NotFoundException {
       String username = cookieUtil.getUsernameFromAuthorizationCookie(request);
       User user = userService.getUserByUsername(username);
       userService.changePassword(user,changePasswordRequest);

    }

    @GetMapping("/{id}/reviews")
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(code = HttpStatus.OK)
    public List<UserReview> getUserReviewed(@PathVariable long id) throws ObjectByIdNotFoundException {
        User user = userService.getUserById(id);
        return user.getMyReviews();
    }

    @PostMapping("/{id}/reviews")
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createUserReview(HttpServletRequest request, @RequestBody CreateReviewRequest createReviewRequest,
                                 @PathVariable long id) throws WrongReviewMarkException, ObjectByIdNotFoundException, NotFoundException {
        User user = userService.getUserFromAuthorizedRequest(request);
        User reviewed = userService.getUserById(id);
        reviewService.createUserReview(user,reviewed, createReviewRequest);
    }



}
