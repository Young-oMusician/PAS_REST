package com.example.PAS_REST.restapp.auth;

import com.example.PAS_REST.model.datalayer.obj.People.Person;
import com.example.PAS_REST.restapp.DataCenter;

import static java.util.Collections.singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import static javax.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;
import javax.security.enterprise.identitystore.IdentityStore;
import static javax.security.enterprise.identitystore.IdentityStore.ValidationType.VALIDATE;

@RequestScoped
public class AuthenticationIdentityStore implements IdentityStore {

    private Map<String, String> callerToPassword;

    @Inject
    private DataCenter dataCenter;

    @PostConstruct
    public void init() {
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        CredentialValidationResult result;

        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential usernamePassword = (UsernamePasswordCredential) credential;
            String expectedPW = dataCenter.get_hr().getPerson(usernamePassword.getCaller()).getPassword();
            if(!dataCenter.get_hr().getPerson(usernamePassword.getCaller()).isActive()){
                return INVALID_RESULT;
            }
            if (expectedPW != null && expectedPW.equals(usernamePassword.getPasswordAsString())) {
                return new CredentialValidationResult(usernamePassword.getCaller(), singleton(dataCenter.get_hr().getPerson(usernamePassword.getCaller()).getRole()));
            } else {
                return INVALID_RESULT;
            }
        } else {
            return NOT_VALIDATED_RESULT;
        }
    }

    @Override
    public Set<ValidationType> validationTypes() {
        return singleton(VALIDATE);
    }
}
