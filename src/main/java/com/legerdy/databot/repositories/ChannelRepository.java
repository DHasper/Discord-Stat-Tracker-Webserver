package com.legerdy.databot.repositories;

import com.legerdy.databot.models.Channel;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends CrudRepository<Channel, String> {
    
}