package pl.karinawojtek.ztiserver.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.karinawojtek.ztiserver.models.database.User;
import pl.karinawojtek.ztiserver.models.request.ChangePasswordRequest;
import pl.karinawojtek.ztiserver.models.request.RegisterUserRequest;
import pl.karinawojtek.ztiserver.services.UserService;
import pl.karinawojtek.ztiserver.utils.CookieUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private CookieUtil cookieUtil;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createUser(@RequestBody RegisterUserRequest registerUserRequest){

        service.registerUser(registerUserRequest);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<User> getAllUsers(){
        return service.getAllUsers();
    }

    @GetMapping ("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public User getUserById(@PathVariable long id){
        return service.getUserById(id);
    }

    @PostMapping ("/password-change")
    @ResponseStatus(code = HttpStatus.OK)
    public void changePassword(HttpServletRequest request, @RequestBody ChangePasswordRequest changePasswordRequest){
       String username = cookieUtil.getUsernameFromAuthorizationCookie(request);
       User user = service.getUserByUsername(username);
       service.changePassword(user,changePasswordRequest);

    }

}
