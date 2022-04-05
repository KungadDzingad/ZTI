package pl.karinawojtek.ztiserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.karinawojtek.ztiserver.dao.RoleRepository;
import pl.karinawojtek.ztiserver.dao.UserRepository;
import pl.karinawojtek.ztiserver.exception.DuplicateObjectException;
import pl.karinawojtek.ztiserver.exception.ObjectByIdNotFoundException;
import pl.karinawojtek.ztiserver.models.database.User;
import pl.karinawojtek.ztiserver.models.database.UserRole;
import pl.karinawojtek.ztiserver.models.request.RegisterUserRequest;

import java.util.Date;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    public User getUserById(long id) throws ObjectByIdNotFoundException {
        Optional<User> userOpt = userRepository.findById(id);
        if(userOpt.isEmpty())
            throw new ObjectByIdNotFoundException();
        return userOpt.get();
    }

    public User getUserByUsername(String username) throws BadCredentialsException {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isEmpty())
            throw new BadCredentialsException(username + " - not found");
        return userOpt.get();
    }

    public void registerUser(RegisterUserRequest reqisterUser) throws DuplicateObjectException {
        if(userRepository.findByUsername(reqisterUser.getUsername()).isPresent())
            throw new DuplicateObjectException("Username already registered");
        if(userRepository.findByEmail(reqisterUser.getUsername()).isPresent())
            throw new DuplicateObjectException("email already registered");
        if(userRepository.findByPhoneNumber(reqisterUser.getUsername()).isPresent())
            throw new DuplicateObjectException("Phone Number already registered");
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


}
