package pl.karinawojtek.ztiserver.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.karinawojtek.ztiserver.models.request.RegisterUserRequest;
import pl.karinawojtek.ztiserver.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createUser(@RequestBody RegisterUserRequest registerUserRequest){
        service.registerUser(registerUserRequest);
    }

}
