package com.duminska.ultimatetasklist.config.token;

import com.duminska.ultimatetasklist.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public JwtUser loadUserByUsername(String s) throws UsernameNotFoundException {

        com.duminska.ultimatetasklist.user.User user = userDao.getByEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException(s);
        }
        return JwtUser.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .id(user.getId())
                .authorities(new ArrayList<>())
                .build();
    }

}