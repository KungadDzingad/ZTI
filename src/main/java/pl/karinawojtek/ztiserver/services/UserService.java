package pl.karinawojtek.ztiserver.services;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.karinawojtek.ztiserver.dao.RoleRepository;
import pl.karinawojtek.ztiserver.dao.UserRepository;
import pl.karinawojtek.ztiserver.exception.custom.AlreadyInFavouritesException;
import pl.karinawojtek.ztiserver.exception.custom.ApiRequestException;
import pl.karinawojtek.ztiserver.exception.custom.DuplicateObjectException;
import pl.karinawojtek.ztiserver.exception.custom.ObjectByIdNotFoundException;
import pl.karinawojtek.ztiserver.models.database.Auction;
import pl.karinawojtek.ztiserver.models.database.User;
import pl.karinawojtek.ztiserver.models.database.UserRole;
import pl.karinawojtek.ztiserver.models.request.ChangePasswordRequest;
import pl.karinawojtek.ztiserver.models.request.RegisterUserRequest;
import pl.karinawojtek.ztiserver.utils.CookieUtil;
import pl.karinawojtek.ztiserver.utils.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private StringUtil stringUtil;

    @Autowired
    private CookieUtil cookieUtil;

    public User getUserById(long id) throws ApiRequestException, ObjectByIdNotFoundException {
        Optional<User> userOpt = userRepository.findById(id);
        if(userOpt.isEmpty())
            throw new ObjectByIdNotFoundException("No User with id "+ id );
        return userOpt.get();
    }

    public User getUserByUsername(String username) throws NotFoundException {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isEmpty())
            throw new NotFoundException(username + " - not found");
        return userOpt.get();
    }

    public void registerUser(RegisterUserRequest reqisterUser) {
        if(userRepository.findByUsername(reqisterUser.getUsername()).isPresent())
            throw new DuplicateObjectException("Username already registered");
        if(userRepository.findByEmail(reqisterUser.getEmail()).isPresent())
            throw new DuplicateObjectException("Username already registered");
        if(userRepository.findByPhoneNumber(reqisterUser.getPhoneNumber()).isPresent())
            throw new DuplicateObjectException("Username already registered");

        if(!stringUtil.checkCorrectEmail(reqisterUser.getEmail()))
            throw new  BadCredentialsException("Wrong email");
        if(!stringUtil.checkPhoneNumberString(reqisterUser.getPhoneNumber()))
            throw new BadCredentialsException("Wrong Phone Number");


        User user = new User();
        user.setEmail(reqisterUser.getEmail());
        user.setPassword(encoder.encode(reqisterUser.getPassword()));
        user.setPhoneNumber(reqisterUser.getPhoneNumber());
        user.setUsername(reqisterUser.getUsername());
        user.setName(reqisterUser.getName());
        user.setRegistrationDate(new Date());
        user.setRole(roleRepository.findByRoleName(UserRole.USER_ROLE));
        userRepository.save(user);
    }


    public List<User> getAllUsers() {
        return (List<User>)userRepository.findAll();
    }

    public void changePassword(User user, ChangePasswordRequest changePasswordRequest) {
        if(!user.getPassword().equals(encoder.encode(changePasswordRequest.getOldPassword())))
            throw new ApiRequestException(new BadCredentialsException( user.getUsername()+" - old password not matching"), HttpStatus.BAD_REQUEST);
        user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);

    }

    public User getUserFromAuthorizedRequest(HttpServletRequest request) throws AuthorizationServiceException, NotFoundException {
        String username = cookieUtil.getUsernameFromAuthorizationCookie(request);
        return this.getUserByUsername(username);
    }

    public void addFavourite(User user, Auction auction) throws AlreadyInFavouritesException {
        if(user.getFavourites().contains(auction)) throw new AlreadyInFavouritesException();
        user.getFavourites().add(auction);
        auction.getFavorites().add(user);
        userRepository.save(user);
    }

    public List<Auction> getUserAuctions(User user) {
        return user.getAuctions();
    }
}
