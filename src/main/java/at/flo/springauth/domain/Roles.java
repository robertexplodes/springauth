package at.flo.springauth.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    ROLE_USER, ROLE_MOD, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}

