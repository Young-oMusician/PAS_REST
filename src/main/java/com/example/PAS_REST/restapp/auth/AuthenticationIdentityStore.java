package com.example.PAS_REST.restapp.auth;

import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

public class AuthenticationIdentityStore implements IdentityStore {
    @Override

    public CredentialValidationResult validate(Credential credential) {
        return null;
    }
}
