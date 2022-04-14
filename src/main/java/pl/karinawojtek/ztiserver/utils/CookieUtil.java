package pl.karinawojtek.ztiserver.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class CookieUtil {
    public static final String AUTHORIZATION_COOKIE = "Authorization_Cookie";

    @Autowired
    private JwtUtil jwtUtil;

    public Cookie getCookie(String cookieName, HttpServletRequest request){
        final Cookie[] cookies = request.getCookies();

        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authorization-Cookie")) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public String getUsernameFromAuthorizationCookie(HttpServletRequest request) throws AuthorizationServiceException {
        Cookie cookie = getCookie(AUTHORIZATION_COOKIE, request);
        if(cookie == null) throw new AuthorizationServiceException("No Authorization Cookie");
        String jwt = cookie.getValue();
        if(jwt == null) throw new AuthorizationServiceException("Bad Cookie");
        String username = jwtUtil.extractUsername(jwt);
        if(username == null) throw new AuthorizationServiceException("Bad Cookie");

        return username;
    }

    public String getTokenFromAuthorizationCookie(HttpServletRequest request){
        Cookie cookie = getCookie(AUTHORIZATION_COOKIE, request);
        if(cookie != null)
            return getCookie(AUTHORIZATION_COOKIE, request).getValue();
        return null;
    }


}
