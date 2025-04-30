package com.desafio.user.security;

import org.springframework.security.web.util.matcher.RequestMatcher;

import jakarta.servlet.http.HttpServletRequest;

public class CustomRequestMatcher implements RequestMatcher{

    public CustomRequestMatcher() {
    
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        if(request.getMethod().equals("POST")
        		&& request.getServletPath().equals("/users")) {
            return true;
        }
        return false;
    }

}