package com.example.PAS_REST.restapp;

import javax.annotation.security.DeclareRoles;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@DeclareRoles({"READER", "EMPLOYEE", "ADMIN"})
@ApplicationPath("/")
public class HelloApplication extends Application {

}