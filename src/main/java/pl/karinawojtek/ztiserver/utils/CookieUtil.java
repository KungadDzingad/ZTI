package pl.karinawojtek.ztiserver.utils;

import org.springframework.beans.factory.annotation.Autowired;
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

    public String getUsernameFromAuthorizationCookie(HttpServletRequest request){
        return jwtUtil.extractUsername(getCookie(AUTHORIZATION_COOKIE, request).getValue());
    }

    public String getTokenFromAuthorizationCookie(HttpServletRequest request){
        return getCookie(AUTHORIZATION_COOKIE, request).getValue();
    }


}
