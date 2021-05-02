package org.example.sweater.controller;

import org.example.sweater.domain.Role;
import org.example.sweater.domain.User;
import org.example.sweater.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 @Controller - помечаем, что это контроллер
 @GetMapping - создаем новый getMapping
 */

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {

        // здесь будем искать пользователя
        User userFromDb = userRepository.findByUsername(user.getUsername());
        // если userFromDb не null, то нам надо об этом сообщить на экране (на странице регистрации)
        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "registration";
        }

        // еслм такого пользователя еще нет
        user.setActive(true);
        // задаем ему роль
        // так как на вход ожидается коллекция в виде Set-a, но так как у нас всего одно значение
        // мы используем Collections.singleton() - который создает set c одним единственным значением
        user.setRoles(Collections.singleton(Role.USER));
        // сохраняем пользователя
        userRepository.save(user);

        // при успешной авторизации буждем делать redirect на страницу логина
        return "redirect:/login";
    }
}
