package org.example.sweater;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 Controller - програмный модуль, который по этому пути ("/greeting")
 слушает запросы от пользователя и возвращает какие-то данные, в нашем случае файл шаблона (greeting.html)
 */

@Controller
public class GreetingController  {

    // метод ожидает на вход параметр name
    // дефолтное значение у этого параметра - World, он не обязательный и у него имя "name"
    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name,
            // model - это то куда мы будем складывать данные, которые мы хотим вернуть пользователю
            Model model
    ) {
        // в поле "name" ложим то, что мы получили из этого name-a
        model.addAttribute("name", name);
        // возвращаем имя файла спринговому контейнеру, который мы хотим отобразить
        return "greeting";
    }
}
