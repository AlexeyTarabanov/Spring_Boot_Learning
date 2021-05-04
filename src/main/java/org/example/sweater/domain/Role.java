package org.example.sweater.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 В данном "классе" добавляем пользователям роли.
 Интерфейс GrantedAuthority - представляет полномочия, предоставленные Authenticationобъекту.
 A GrantedAuthority должен либо представлять себя как a, String либо иметь специальную поддержку AccessDecisionManager.

 Добавили ADMIN-а, чтобы можно было как-то руководить пользователями.
 Для того, чтобы редактировать роли, создадим новый контроллер UserController
 */

public enum Role implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
