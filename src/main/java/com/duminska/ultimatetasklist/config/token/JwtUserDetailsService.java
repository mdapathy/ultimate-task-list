package com.duminska.ultimatetasklist.config.token;

import com.duminska.ultimatetasklist.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.duminska.ultimatetasklist.user.User user = userDao.getByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No user with such email");
        }
        return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}