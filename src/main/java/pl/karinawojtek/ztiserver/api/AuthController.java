package pl.karinawojtek.ztiserver.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.karinawojtek.ztiserver.models.MyUserDetails;
import pl.karinawojtek.ztiserver.models.request.AuthenticateRequest;
import pl.karinawojtek.ztiserver.models.response.TokenResponse;
import pl.karinawojtek.ztiserver.models.response.UsernameResponse;
import pl.karinawojtek.ztiserver.services.MyUserDetailsService;
import pl.karinawojtek.ztiserver.services.UserService;
import pl.karinawojtek.ztiserver.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth/")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;


    @PostMapping
//    @CrossOrigin(origins = "http://localhost:3000")
    private TokenResponse authenticate(@RequestBody AuthenticateRequest authRequest) throws Exception{
        try{
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword())
            );
        } catch(BadCredentialsException exception){
            throw new Exception("Incorrect username or password", exception);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        long id = ((MyUserDetails)userDetails).getId();
        TokenResponse tokenResponse = new TokenResponse(jwtUtil.generateToken(userDetails), id);
        return tokenResponse;
    }


}
