package com.legerdy.databot.repositories;

import java.util.Optional;

import com.legerdy.databot.models.Guild;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuildRepository extends CrudRepository<Guild, String> {

    Optional<Guild> findByAccessCode(String accessCode);
    
}