package pl.karinawojtek.ztiserver.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.karinawojtek.ztiserver.models.request.AuthenticateRequest;
import pl.karinawojtek.ztiserver.services.MyUserDetailsService;
import pl.karinawojtek.ztiserver.utils.JwtUtil;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @PostMapping
    @CrossOrigin(origins = "http://localhost:3000")
    private String authenticate(@RequestBody AuthenticateRequest authRequest) throws Exception{
        try{
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword())
            );
        } catch(BadCredentialsException exception){
            throw new Exception("Incorrect username or password", exception);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        return jwtUtil.generateToken(userDetails);
    }


}
