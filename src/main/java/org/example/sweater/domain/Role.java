package org.example.sweater.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 Интерфейс GrantedAuthority - представляет полномочия, предоставленные Authenticationобъекту.
 A GrantedAuthority должен либо представлять себя как a, String либо иметь специальную поддержку AccessDecisionManager.

 */

public enum Role implements GrantedAuthority {
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
