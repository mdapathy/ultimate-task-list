package com.duminska.ultimatetasklist.config.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    final private JwtTokenProvider tokenProvider;


    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            String jwtToken = tokenProvider.resolveToken((HttpServletRequest) servletRequest);

            if (StringUtils.hasText(jwtToken) && tokenProvider.validateToken(jwtToken)) {

                String emailFromJwt = tokenProvider.getEmailFromJwt(jwtToken);

                if (StringUtils.isEmpty(emailFromJwt)) {
                    throw new UsernameNotFoundException("Username from JWT not found");
                }

                Authentication authentication = tokenProvider.getAuthentication(jwtToken);

                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }
        } catch (Exception ex) {
            log.info("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}


