package com.legerdy.databot.repositories;

import com.legerdy.databot.models.Message;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, String> {
    
    Iterable<Message> findAllByGuildId(String guildId);

}