package com.example.PAS_REST.restapp.auth;

import com.example.PAS_REST.restapp.beans.LoginBean;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.stream.JsonParser;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class JWTAuthenticationMechanism implements HttpAuthenticationMechanism {
    private static final Logger LOGGER = Logger.getLogger(JWTAuthenticationMechanism.class.getName());
    public final static String AUTHORIZATION ="Authorization";
    public final static String BEARER ="Bearer";

    @Inject
    private AuthenticationIdentityStore identityStoreHandler;

    @Inject
    private JWTGenerator jwtGenerator;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse,
                                                HttpMessageContext httpMessageContext)
            throws AuthenticationException {

        String email = httpServletRequest.getParameter("email");
        String password = httpServletRequest.getParameter("password");

        String token = extractToken(httpMessageContext);

        if (email != null && password != null) {

            CredentialValidationResult result = identityStoreHandler.validate(new UsernamePasswordCredential(email, password));
            if (result == CredentialValidationResult.INVALID_RESULT || result == CredentialValidationResult.NOT_VALIDATED_RESULT) {
                return httpMessageContext.responseUnauthorized();
            }
            return httpMessageContext.notifyContainerAboutLogin(result.getCallerPrincipal(), result.getCallerGroups());
        } else if (token != null) {
            return validateToken(token, httpMessageContext);
        } else if (httpMessageContext.isProtected()) {
            return httpMessageContext.responseUnauthorized();
        }
        return httpMessageContext.doNothing();
    }

    private AuthenticationStatus validateToken(String token, HttpMessageContext context) {
        try {
            if (jwtGenerator.validateToken(token)) {
                JWTCredential credential = jwtGenerator.getCredential(token);
                return context.notifyContainerAboutLogin(credential.getPrincipal(), credential.getAuthorities());
            }
            return context.responseUnauthorized();
        } catch (ExpiredJwtException eje) {
            LOGGER.log(Level.INFO, "Security exception for user {0} - {1}", new String[]{eje.getClaims().getSubject(), eje.getMessage()});
            return context.responseUnauthorized();
        }
    }

    private String extractToken(HttpMessageContext context) {
        String authorizationHeader = context.getRequest().getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            String token = authorizationHeader.substring(BEARER.length(), authorizationHeader.length());
            return token;
        }
        return null;
    }
}
