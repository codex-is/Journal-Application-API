package com.harsh.JournallingApplication.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring security Configuration for securing the application
 *
 * this class configures authentication and authorization rules.
 * It uses UserDetailsService to retrive user specific data
 * and applies securty filters to incoming HTTP requests
 */

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    /**
     * Custom implementation of UserDetailsService to fetch
     * user credentials (from database or other stores).
     * This gets injected by Spring automatically.
     */
    private final UserDetailsService userDetailsService;

    public SpringSecurity(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Defines the main security filter chain where we configure
     * -CSRF protection
     * - Authentication rules
     * -Login methods
     * @param http HttpSecurity instance provided by Spring Security
     * @return SecurityFilterChain object
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity http) throws Exception{
        // Disable CSRF (recommended for stateless REST APIs but use cautiously for web apps)
        http.csrf(customizer ->customizer.disable());
        // Require authentication for every request (can be fine-tuned with request matchers)
        http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
        // Enable default form-based login page
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());

        // If you want stateless authentication (like with JWT), uncomment below:
 //     http.sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    /**
     * Authentication provider bean.
     * Uses a DaoAuthenticationProvider which delegates authentication
     * to a configured UserDetailsService and applies password encoding.
     *
     * @return AuthenticationProvider for handling user validation
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        // Configure strong password encoding using BCrypt with strength 12
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));

        // Attach custom UserDetailsService for fetching user data (username, roles, password)
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }



    /**
     * Example: In-Memory Authentication (for testing/demo only).
     * This creates a hardcoded user with username and password.
     *
     * ⚠️ DO NOT use in production as credentials are stored in plain text here.
     */
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails user1 = User
//                .withDefaultPasswordEncoder()
//                .username("Harsh")
//                .password("h@123")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user1);
//    }
}

