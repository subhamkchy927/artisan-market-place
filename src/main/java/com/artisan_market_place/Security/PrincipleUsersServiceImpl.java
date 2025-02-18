package com.artisan_market_place.Security;

import com.artisan_market_place.entity.UsersLoginInfo;
import com.artisan_market_place.repository.LoginUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class PrincipleUsersServiceImpl implements UserDetailsService {

    @Autowired
    private LoginUserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(PrincipleUsersServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Entering in loadUserByUsername Method...");
        UsersLoginInfo user = userRepository.findByLoginId(username);
        if(user == null){
            logger.error("Username not found: " + username);
            throw new UsernameNotFoundException("could not found user..!!");
        }
        logger.info("User Authenticated Successfully..!!!");
        return new PrincipleUsers(user);
    }
}
