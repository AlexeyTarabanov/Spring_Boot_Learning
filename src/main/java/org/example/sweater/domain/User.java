package org.example.sweater.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

/**
 1. Помечаем, что это Entity
 И хранится он будет в табличке usr
 2. Создаем поля id, которые будут GeneratedValue
 У пользователя будет имя userName, пароль, признак активнсти
 3. Так же у пользователя будет ролевая система: admin-пользователь и привелигированный пользватель (модератор)
 private Set<Role> roles - будет содержать роли.
 Здесь добавили аннотацию ElementCollection, которая означает, что коллекция не является совокупностью объектов,
 а представляет собой набор простых типов (строки и т.д.) или набор встраиваемых элементов
 (класс, аннотированный с помощью @Embeddable).
 fetch - параметр, который определяет как данные значения будут подгружаться относительно основной сущности.
 Когда мы загружаем пользователя, роли его хранятся в отдельной тблице и нам необходимо их загружать либо ЖАДНЫМ
 (Hibernate при зпросе пользователя будет подгружать все его роли) способом
 либо ЛЕНИВЫМ (подгрузит роли, только, когда пользователь реально обратится к этому полю)
 4. @CollectionTable - описывает, что данное поле будет хранится в отдельной таблице.
 Название таблицы - user_role
 Будет соединяться с текущей таблицей через user_id
 5. @Enumerated - устанавливаем, что Enum будем хранить в видже строки

 Модифицируем User
 1. Имплементируем интерфейс UserDetails - он предоставляет основную информацию о пользователе.
 Реализации не используются Spring Security напрямую в целях безопасности.
 Они просто хранят информацию о пользователе, которая позже инкапсулируется в Authentication объекты.
 Это позволяет хранить информацию о пользователях, не связанную с безопасностью
 (такую как адреса электронной почты, номера телефонов и т. Д.), В удобном месте.
 */

@Entity
@Table(name = "usr")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    // указывает, истек ли срок действия учетной записи пользователя
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // указывает, заблокирован пользователь или разблокирован
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // указывает, истек ли срок действия учетных данных (пароля) пользователя
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // указывает, включен или отключен пользователь
    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    // возвращает полномочия, предоставленные пользователю
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
