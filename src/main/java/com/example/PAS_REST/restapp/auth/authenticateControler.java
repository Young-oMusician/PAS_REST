package com.example.PAS_REST.restapp.auth;

import com.example.PAS_REST.restapp.beans.LoginBean;

import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.security.enterprise.SecurityContext;

import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Path("auth")
public class authenticateControler {

        private static final Logger LOGGER = Logger.getLogger(authenticateControler.class.getName());


        @Inject
        private IdentityStoreHandler identityStoreHandler;

        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.TEXT_PLAIN)
        @Path("login")
        public Response login(LoginBean loginBean) {
            Credential credential = new UsernamePasswordCredential(loginBean.email,new Password(loginBean.password));
            CredentialValidationResult result = identityStoreHandler.validate(credential);

            LOGGER.log(Level.INFO, "login");
            if (result.getStatus()== CredentialValidationResult.Status.VALID) {
//                JsonObject result = Json.createObjectBuilder()
//                        .add("user", securityContext.getCallerPrincipal().getName())
//                        .build();
                return Response.accepted().type("aplocation/jwt").build();
            }
            return Response.status(UNAUTHORIZED).build();
        }


}
