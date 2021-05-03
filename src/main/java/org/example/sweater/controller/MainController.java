package org.example.sweater.controller;

import org.example.sweater.domain.Message;
import org.example.sweater.domain.User;
import org.example.sweater.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 Controller - програмный модуль, который по этому пути ("/greeting")
 слушает запросы от пользователя и возвращает какие-то данные, в нашем случае файл шаблона (greeting.mustache)
 */

@Controller
public class MainController {

    // здесь будем хранить список месседжей
    @Autowired
    private MessageRepository messageRepository;

    // метод ожидает на вход параметр name
    // дефолтное значение у этого параметра - World, он не обязательный и у него имя "name"
    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages", messages);
        return "main";
    }

    // add - добавляет сообщения
    // @RequestParam -
    // выдергивает с наших запросов либо из <form>, если мы передаем post-ом
    // либо из url-a, если мы передаем get запрос (как в методе greeting)
    // Spring вытягиваеит поля по имени, которе указали в аргументах
    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag, Map<String, Object> model
    ) {
        // сначала сохранили
        Message message = new Message(text, tag, user);
        messageRepository.save(message);
        // взяли из репозитория
        Iterable<Message> messages = messageRepository.findAll();
        // положили в модель
        model.put("messages", messages);
        // и отдали пользователю
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {

        Iterable<Message> messages;

        // нам нужно найти по переданному тэгу все подходящие сообщения
        // НО метода такого в РЕПОЗИТОРИИ пока что нет
        // поэтому в репо создаем новый метод - findByTag
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }
        model.put("messages", messages);
        return "main";
    }
}
