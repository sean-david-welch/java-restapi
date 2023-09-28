package com.example.demo.utils;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

public class JwtCookieFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("yourCookieName".equals(cookie.getName())) {
                    String jwt = cookie.getValue();
                    request = new CustomHttpServletRequestWrapper(request, jwt);
                    break;
                }
            }
        }
        chain.doFilter(request, response);
    }

    static class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {
        private final String jwt;

        public CustomHttpServletRequestWrapper(HttpServletRequest request, String jwt) {
            super(request);
            this.jwt = jwt;
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            if ("Authorization".equals(name)) {
                return Collections.enumeration(Collections.singletonList("Bearer " + jwt));
            }
            return super.getHeaders(name);
        }
    }
}
