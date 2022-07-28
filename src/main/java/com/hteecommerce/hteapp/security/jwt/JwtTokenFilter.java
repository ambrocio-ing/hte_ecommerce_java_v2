package com.hteecommerce.hteapp.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hteecommerce.hteapp.security.service.UserDetailsServiceImplements;

public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserDetailsServiceImplements userDetailsServiceImplements;

    public String getToken(HttpServletRequest request){

        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer")){
            return header.replace("Bearer ", "");
        }
        else{
            return null;
        }

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String token = getToken(request);
        if(token != null && jwtProvider.validateToken(token)){
            String username = jwtProvider.getUsername(token);
            UserDetails userDetails = userDetailsServiceImplements.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken upat = 
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(upat);
        }

        filterChain.doFilter(request, response);
        
    }
    
}
