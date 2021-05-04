package org.example.sweater.controller;

import jdk.jfr.Registered;
import org.example.sweater.domain.Role;
import org.example.sweater.domain.User;
import org.example.sweater.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 Помечаем его как @Controller, помечаем @RequestMapping
 (используется для мапинга (связывания) с URL для всего класса или для конкретного метода обработчика)
 В нашем случае пометили на уровне класса.
 Это сделано для того, чтобы у каждого метода не подписывать, что он содержит в своем пути "/user"
 Теперь, когда мы пишем @GetMapping, мы можем не указывать мэпинг
 (это будет означать, что при get-запросе к текущему методу - путь будет содержать "/user")
 "Автоварим" UserRepository и создаем метод:
 userList() - возвращать он будет userList - файл, который мы создадим в ресурсах (templates)

 */

@Controller
@RequestMapping("/user")
// эта аннотация будет для каждого из методов в данном контроллере проверять
// перед выполнением этого метода
// наличие у пользователя прав, которые мы указываем в скобочках (ADMIN)
// чтобы данная аннотация заработала
// необходимо зайти в WebSecurityConfig
// и добавить аннотацию @EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String userList(Model model) {
        // "пропихиваем" список пользователей в виде моделей
        // после этого выведем его в списке пользователей в файле userList
        model.addAttribute("users", userRepository.findAll());
        return "userList";
    }

    // добавляем "мэпинг" для редактора пользователя
    // здесь, для данного метода мы ожидаем "мэпинг" помимо ("/user")
    // через "/" будет еще идти идентификатор, который мы и запрашиваем
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        // заполняем модель
        model.addAttribute("user", user);
        // выведем список ролей
        model.addAttribute("roles", Role.values());
        // теперь создаем шаблон userEdit
        return "userEdit";
    }

    // добавляем метод сохранения
    @PostMapping
    public String userSave(
            // чтобы сохранить данные пользователя нам необходимо получить некоторые
            // аргументы с сервера
            // 1. параметр userId (по нему мы будем получать пользователя из базы данных)
            // 2. Map<String, String> form - список полей, которые передаются в этой форме
            // (где ключ и значение являются строками)
            // 3. username - имя пользователя (мы можем его менять и его нужно после этого сохранить)
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {
        // 1. возьмем пользователя и установим ему новое имя
        user.setUsername(username);

        // 3. прежде чем обновлять роли пользователя, нам нужно получить список ролей
        // для того, чтобы проверить, что они установлены данному пользователю
        // для этого:
        // - запрашиваем роли: Role.values()
        // - переводим их из enum в список String
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        // - проверим, что данная форма содержит роли для нашего пользователя

        // 4. очищаем все его роли
        user.getRoles().clear();

        // получаем список ключей и итерируем по нему
        for (String key : form.keySet()) {
            // проверяем, что роли содержат данный ключ
            if (roles.contains(key)) {
                // в этом случаем нашему пользователю добавляем  такую роль
                // НО! это сработает только в том случае, если у нас роль добавлена
                // поэтому для этого нужно перед этим у пользователя очистить все роли (4)
                user.getRoles().add(Role.valueOf(key));
            }
        }

        // 2. после этого пользователя уже можно сохранять
        userRepository.save(user);

        // сделаем redirect на список пользователей (redirect:/user)
        return "redirect:/user";
    }
}
