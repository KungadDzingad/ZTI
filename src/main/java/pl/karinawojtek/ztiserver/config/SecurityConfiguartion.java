package pl.karinawojtek.ztiserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.karinawojtek.ztiserver.filters.JwtRequestFilter;
import pl.karinawojtek.ztiserver.services.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguartion extends WebSecurityConfigurerAdapter {

    public final static String CORS = "http://localhost:3000";

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
//                .httpBasic()
//                .and()
//                .antMatcher("/api/**")
                .authorizeRequests()
                .antMatchers("/api/auth/").permitAll()
                .antMatchers( "/api/users/").permitAll()
//                .antMatchers( "/api/users/{id}").permitAll()
//                .antMatchers( HttpMethod.GET,"/api/users/{id}/reviews").permitAll()
                .antMatchers(HttpMethod.GET, "/api/auctions/").permitAll()
//                .antMatchers("/api/**").authenticated()
//                .antMatchers("api/auctions/**").authenticated()
//                .antMatchers(HttpMethod.POST, "api/auctions").authenticated()


                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .formLogin().disable();
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/api/auth/")
//                .antMatchers( "/api/users/")
//                .antMatchers( "/api/users/{id}")
//                .antMatchers( HttpMethod.GET,"/api/users/{id}/reviews")
//                .antMatchers(HttpMethod.GET, "/api/auctions/");
//    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").allowedOrigins("http://localhost:3000");
//            }
//        };
//    }
}
