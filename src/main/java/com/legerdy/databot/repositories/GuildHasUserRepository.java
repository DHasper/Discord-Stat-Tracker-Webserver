package com.legerdy.databot.repositories;

import java.util.List;
import java.util.Optional;

import com.legerdy.databot.models.GuildHasUser;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuildHasUserRepository extends CrudRepository<GuildHasUser, Integer> {

    Optional<List<GuildHasUser>> findByUserId(String userId);
    
}