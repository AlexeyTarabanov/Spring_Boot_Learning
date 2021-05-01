package org.example.sweater.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 @Entity - дает знать Spring-у, что это не просто кусок програмного кода, а сущность,
 которую нам необходимо сохранять в базе данных.
 C этой аннотацией обязательно должен быть конструктор без параметров
 */

@Entity
public class Message {

    // @Id - объясняет фреймворку, что поле (public Integer id) будет идендифиатором
    // @GeneratedValue - а здесь говорим, чтобы фреймворк сам озадачился в каком порядке будет генерировать идентификаторы
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String text;
    private String tag;

    public Message() {}

    public Message(String text, String tag) {
        this.text = text;
        this.tag = tag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
