package com.example.PAS_REST.restapp.auth;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ApplicationScoped
public class JWTAuthenticationMechanism implements HttpAuthenticationMechanism {
 public final static String AUTHORIZATION ="Authorization";
 public final static String BEARER ="Bearer";

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse,
                                                HttpMessageContext httpMessageContext)
            throws AuthenticationException {

        if(!httpServletRequest.getRequestURL().toString().endsWith("/auth/login")){

            String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);
            if(authorizationHeader==null|| !authorizationHeader.startsWith(BEARER)){
                return httpMessageContext.responseUnauthorized();
            }
            String jwtSerializedToken = authorizationHeader.substring(BEARER.length()).trim();
//            if(JWTGenerator)
        }


        return httpMessageContext.responseUnauthorized();
    }
}
