package com.legerdy.databot.repositories;

import com.legerdy.databot.models.VoiceActivity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoiceActivityRepository extends CrudRepository<VoiceActivity, Integer> {

}