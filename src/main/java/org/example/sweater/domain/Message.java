package org.example.sweater.domain;

import javax.persistence.*;

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

    // здесь хранится автор
    // @ManyToOne - указываем базе данных, что у нас в этой связи
    // одному пользователю соответсвует множество сообщений (Message)
    // если мы захотим добавить связь со сторны пользователя, то это будет - OneToMany
    // добавили fetch - режим, и установили его в EAGER (что подразумевает, что
    // каждый раз когда мы получаем сообщение мы хотим получать инф-ию об авторе вместе с этим сообщением
    // @JoinColumn - здесь напишем название колонки (как она должна быть записана в базе данных)
    // это нужно для того, чтобы в БД у нас это поле называлось user_id, а не author_id, как это было бы по умолчанию
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public Message() {}

    public Message(String text, String tag, User user) {
        this.author = user;
        this.text = text;
        this.tag = tag;
    }

    // проверяем есть у нас автор или нет
    public String getAuthorName() {
        if (author != null) {
            return author.getUsername();
        } else {
            return "<none>";
        }
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
