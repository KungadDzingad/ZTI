package pl.karinawojtek.ztiserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.karinawojtek.ztiserver.dao.UserRepository;
import pl.karinawojtek.ztiserver.exception.DuplicateObjectException;
import pl.karinawojtek.ztiserver.exception.ObjectByIdNotFoundException;
import pl.karinawojtek.ztiserver.models.database.User;
import pl.karinawojtek.ztiserver.models.request.RegisterUserRequest;

import java.util.Date;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    public User getUserById(long id) throws ObjectByIdNotFoundException {
        Optional<User> userOpt = repository.findById(id);
        if(userOpt.isEmpty())
            throw new ObjectByIdNotFoundException();
        return userOpt.get();
    }

    public void registerUser(RegisterUserRequest reqisterUser) throws DuplicateObjectException {
        if(repository.findByUsername(reqisterUser.getUsername()).isPresent())
            throw new DuplicateObjectException("Username already registered");
        if(repository.findByEmail(reqisterUser.getUsername()).isPresent())
            throw new DuplicateObjectException("email already registered");
        if(repository.findByPhoneNumber(reqisterUser.getUsername()).isPresent())
            throw new DuplicateObjectException("Phone Number already registered");
        User user = new User();
        user.setEmail(reqisterUser.getEmail());
        user.setPassword(encoder.encode(reqisterUser.getPassword()));
        user.setPhoneNumber(reqisterUser.getPhoneNumber());
        user.setUsername(reqisterUser.getUsername());
        user.setName(reqisterUser.getName());
        user.setRegistrationDate(new Date());
        repository.save(user);
    }

}
