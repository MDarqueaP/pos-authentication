package ec.com.edimca.authentication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ec.com.edimca.authentication.model.User;
import ec.com.edimca.authentication.repository.UserRepository;
import ec.com.edimca.authentication.security.UserNotActiveException;

@Service
public class AuthService implements UserDetailsService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, UserNotActiveException {

        Optional<User> userFound = this.userRepository.findByEmail(email);
        if (!userFound.isPresent()) {
            logger.error("Login error: Email not found in storage");
            throw new UsernameNotFoundException("Login error: Email not found in storage");
        }
        if (!userFound.get().getActive()) {
            logger.error("Login error: User is not active");
            throw new UserNotActiveException("User is not active");
        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        userFound.get().getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRole().getName()));
        });
        return new org.springframework.security.core.userdetails.User(
                userFound.get().getEmail(),
                userFound.get().getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }
    
}
