package org.example.sweater.repository;

import org.example.sweater.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// базовый репозиторий
// позволяет получить полный список полей, либо найти их по id
public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findByTag(String tag);
}
