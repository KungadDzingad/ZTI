package pl.karinawojtek.ztiserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.karinawojtek.ztiserver.dao.RoleRepository;
import pl.karinawojtek.ztiserver.dao.UserRepository;
import pl.karinawojtek.ztiserver.exception.ApiRequestException;
import pl.karinawojtek.ztiserver.exception.DuplicateObjectException;
import pl.karinawojtek.ztiserver.exception.ObjectByIdNotFoundException;
import pl.karinawojtek.ztiserver.models.database.User;
import pl.karinawojtek.ztiserver.models.database.UserRole;
import pl.karinawojtek.ztiserver.models.request.ChangePasswordRequest;
import pl.karinawojtek.ztiserver.models.request.RegisterUserRequest;

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

    public User getUserById(long id) throws ApiRequestException {
        Optional<User> userOpt = userRepository.findById(id);
        if(userOpt.isEmpty())
            throw new ApiRequestException(new ObjectByIdNotFoundException(), HttpStatus.BAD_REQUEST);
        return userOpt.get();
    }

    public User getUserByUsername(String username) throws ApiRequestException {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isEmpty())
            throw new ApiRequestException(new BadCredentialsException(username + " - not found"),HttpStatus.BAD_REQUEST);
        return userOpt.get();
    }

    public void registerUser(RegisterUserRequest reqisterUser) throws ApiRequestException {
        if(userRepository.findByUsername(reqisterUser.getUsername()).isPresent())
            throw new ApiRequestException(new DuplicateObjectException("Username already registered"), HttpStatus.BAD_REQUEST);
        if(userRepository.findByEmail(reqisterUser.getUsername()).isPresent())
            throw new ApiRequestException(new DuplicateObjectException("Username already registered"), HttpStatus.BAD_REQUEST);
        if(userRepository.findByPhoneNumber(reqisterUser.getUsername()).isPresent())
            throw new ApiRequestException(new DuplicateObjectException("Username already registered"), HttpStatus.BAD_REQUEST);
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
}
