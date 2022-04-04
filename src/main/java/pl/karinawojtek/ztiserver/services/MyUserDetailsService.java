package pl.karinawojtek.ztiserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.karinawojtek.ztiserver.dao.UserRepository;
import pl.karinawojtek.ztiserver.models.MyUserDetails;
import pl.karinawojtek.ztiserver.models.database.User;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findByUsername(username);
        userOpt.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));

        return new MyUserDetails(userOpt.get());
    }

}
