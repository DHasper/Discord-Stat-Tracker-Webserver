package com.legerdy.databot.controllers;

import com.legerdy.databot.models.Presence;
import com.legerdy.databot.models.VoiceActivity;
import com.legerdy.databot.repositories.PresenceRepository;
import com.legerdy.databot.repositories.VoiceActivityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
public class ActivityController {

    @Autowired
    private VoiceActivityRepository voiceActivityRepository;

    @Autowired
    private PresenceRepository presenceRepository;
    
    /**
     * Creates a new voice activity object and saves it in the database. This can be used to check
     * how long a user uses a voice channel.
     * 
     * @param user
     * @param channel
     * @param guild
     * @param interval
     * @return a ResponseEntity with the VoiceActivity created.
     */
    @PostMapping("/activity/voice")
    public ResponseEntity<VoiceActivity> newVoiceActivity(@RequestParam(name="user") final String user
                                                    , @RequestParam(name="channel") final String channel
                                                    , @RequestParam(name="guild") final String guild
                                                    , @RequestParam(name="interval") final int interval) {
        
        VoiceActivity activity = new VoiceActivity(user, channel, guild, interval);
        
        try {
            voiceActivityRepository.save(activity);
        } catch (DataIntegrityViolationException e){
            // CONFLICT if parameters violate data integrity
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // CREATED
        return ResponseEntity.status(HttpStatus.CREATED).body(activity);
    }

    /**
     * Creates a new presence object and saved it in the database. This can be used 
     * to check how long a user has a specific presence.
     * 
     * @param user
     * @param interval
     * @param type
     * @param name
     * @return a ResponseEntity with the presence created.
     */
    @PostMapping("/activity/presence")
    public ResponseEntity<Presence> newPresence(@RequestParam(name="user") final String user
                                            , @RequestParam(name="interval") final int interval
                                            , @RequestParam(name="type") final String type
                                            , @RequestParam(name="name") final String name
                                            , @RequestParam(name="details") final String details) {

        Presence presence = new Presence(user, interval, type, name, details);
        
        try {
            presenceRepository.save(presence);
        } catch (DataIntegrityViolationException e){
            // CONFLICT if parameters violate data integrity
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // CREATED
        return ResponseEntity.status(HttpStatus.CREATED).body(presence);
    }
}