package com.legerdy.databot.repositories;

import com.legerdy.databot.models.Presence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresenceRepository extends CrudRepository<Presence, String> {
    
}