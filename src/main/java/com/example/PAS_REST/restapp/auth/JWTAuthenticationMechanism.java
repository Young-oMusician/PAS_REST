package com.example.PAS_REST.restapp.auth;

import io.jsonwebtoken.ExpiredJwtException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        String name = httpServletRequest.getParameter("name");
        String password = httpServletRequest.getParameter("password");
        String token = extractToken(httpMessageContext);

        if (name != null && password != null) {
            LOGGER.log(Level.INFO, "credentials : {0}, {1}", new String[]{name, password});
            // validation of the credential using the identity store
            CredentialValidationResult result = identityStoreHandler.validate(new UsernamePasswordCredential(name, password));
            if (result.getStatus() == CredentialValidationResult.Status.VALID) {
                // Communicate the details of the authenticated user to the container and return SUCCESS.
                return createToken(result, httpMessageContext);
            }
            // if the authentication failed, we return the unauthorized status in the http response
            return httpMessageContext.responseUnauthorized();
        } else if (token != null) {
            // validation of the jwt credential
            return validateToken(token, httpMessageContext);
        } else if (httpMessageContext.isProtected()) {
            // A protected resource is a resource for which a constraint has been defined.
            // if there are no credentials and the resource is protected, we response with unauthorized status
            return httpMessageContext.responseUnauthorized();
        }
        // there are no credentials AND the resource is not protected,
        // SO Instructs the container to "do nothing"
        return httpMessageContext.doNothing();
    }

    private AuthenticationStatus createToken(CredentialValidationResult result, HttpMessageContext context) {
        String jwt = jwtGenerator.createToken(result.getCallerPrincipal().getName(), result.getCallerGroups(), false);
        context.getResponse().setHeader(AUTHORIZATION, BEARER + jwt);
        return context.notifyContainerAboutLogin(result.getCallerPrincipal(), result.getCallerGroups());
    }

    private AuthenticationStatus validateToken(String token, HttpMessageContext context) {
        try {
            if (jwtGenerator.validateToken(token)) {
                JWTCredential credential = jwtGenerator.getCredential(token);
                return context.notifyContainerAboutLogin(credential.getPrincipal(), credential.getAuthorities());
            }
            // if token invalid, response with unauthorized status
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
