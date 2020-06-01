package com.duminska.ultimatetasklist.config.security;

import com.duminska.ultimatetasklist.config.token.JwtUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public String getUserId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            return ((JwtUser) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal()).getId();
        }
        return null;
    }


}