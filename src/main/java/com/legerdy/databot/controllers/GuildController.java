package com.legerdy.databot.controllers;

import java.util.List;
import java.util.Optional;

import com.legerdy.databot.models.Channel;
import com.legerdy.databot.models.Guild;
import com.legerdy.databot.models.GuildHasUser;
import com.legerdy.databot.repositories.ChannelRepository;
import com.legerdy.databot.repositories.GuildHasUserRepository;
import com.legerdy.databot.repositories.GuildRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
public class GuildController {
    
    @Autowired
    private GuildRepository guildRepository;

    @Autowired
    private GuildHasUserRepository guildHasUserRespository;

    @Autowired
    private ChannelRepository channelRepository;

    /**
     * Creates a new guild object and saves it in the database.
     * 
     * @param id 
     * @param name
     * @return ResponseEntity with the guild object created.
     */
    @PostMapping("/guild")
    public ResponseEntity<Guild> newGuild(@RequestParam(name="id") final String id
                                    , @RequestParam(name="name") final String name
                                    , @RequestParam(name="icon") final String icon) {

        Guild newGuild;

        Optional<Guild> oldGuildResult = guildRepository.findById(id);
        if(oldGuildResult.isPresent()){
            newGuild = oldGuildResult.get();
            newGuild.setIconURL(icon);
            newGuild.setName(name);
        } else {
            newGuild = new Guild(id, name, icon);
        }

        try {
            guildRepository.save(newGuild);
        } catch (DataIntegrityViolationException e){
            // CONFLICT if parameters violate data integrity
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // CREATED
        return ResponseEntity.status(HttpStatus.CREATED).body(newGuild);
    }

    /**
     * Creates a new guild has user relation and saves it in the database.
     * 
     * @param user
     * @param guild
     * @param nickname
     * @return ResponseEntity with GuildHasUser object.
     */
    @PostMapping("/guild/user")
    public ResponseEntity<GuildHasUser> newGuildUser(@RequestParam(name="user") final String user
                                                , @RequestParam(name="guild") final String guild
                                                , @RequestParam(name="nickname") final String nickname){
        
        GuildHasUser newGuildUser = new GuildHasUser(user, guild, nickname);

        try {
            guildHasUserRespository.save(newGuildUser);
        } catch (DataIntegrityViolationException e){
            // BAD REQUEST
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // CREATED
        return ResponseEntity.status(HttpStatus.CREATED).body(newGuildUser);
    }

    /**
     * Creates a new channel and saves it in the database.
     * 
     * @param id
     * @param guild
     * @param name
     * @param type
     * @return ResponseEntity with created channel object.
     */
    @PostMapping("/guild/channel")
    public ResponseEntity<Channel> newGuildChannel(@RequestParam(name="id") final String id
                                                , @RequestParam(name="guild") final String guild
                                                , @RequestParam(name="name") final String name
                                                , @RequestParam(name="type") final String type) {

        Channel newChannel = new Channel(id, guild, name, type);

        try {
            channelRepository.save(newChannel);
        } catch (DataIntegrityViolationException e){
            // BAD REQUEST
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // CREATED
        return ResponseEntity.status(HttpStatus.CREATED).body(newChannel);
    }

    /**
     * Find a channel object by given channel id.
     * 
     * @param cid channel id.
     * @return ResponseEntity with channel object.
     */
    @GetMapping("/channel/{cid}")
    public ResponseEntity<Channel> getChannel(@PathVariable final String cid){

        Optional<Channel> channelResponse = channelRepository.findById(cid);

        if(channelResponse.isPresent()){

            Channel channel = channelResponse.get();

            // OK
            return ResponseEntity.status(HttpStatus.OK).body(channel);
        }

        // NOT FOUND if no channel object found with given channel id.
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * Updates a guild name or icon and saves it to the repository.
     * 
     * @param id
     * @param name
     * @return a ResponseEntity with the updates guild.
     */
    @PatchMapping("/guild")
    public ResponseEntity<Guild> updateGuild(@RequestParam(name="id") final String id
                                        , @RequestParam(name="name") final Optional<String> name
                                        , @RequestParam(name="icon") final Optional<String> icon) {

        Optional<Guild> guildResponse = guildRepository.findById(id);

        if(guildResponse.isPresent()){

            Guild guild = guildResponse.get();
            if(name.isPresent()){
                guild.setName(name.get());
            };
            if(icon.isPresent()){
                guild.setIconURL(icon.get());
            };

            try{
                guildRepository.save(guild);
            } catch(DataIntegrityViolationException e) {
                // BAD REQUEST
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            // OK
            return ResponseEntity.status(HttpStatus.OK).body(guild);
        }

        // NOT FOUND if no guild with this id is found
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * Updates a channel name.
     * 
     * @param id
     * @param name
     * @return a ResponseEntity with the updated channel.
     */
    @PatchMapping("/guild/channel")
    public ResponseEntity<Channel> updateGuildChannel(@RequestParam(name="id") final String id
                                                    , @RequestParam(name="name") final String name) {

        Optional<Channel> channelResponse = channelRepository.findById(id);

        if(channelResponse.isPresent()){

            Channel channel = channelResponse.get();
            channel.setName(name);

            try{
                channelRepository.save(channel);
            } catch (DataIntegrityViolationException e) {
                // BAD REQUEST
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            // OK
            return ResponseEntity.status(HttpStatus.OK).body(channel);
        }

        // NOT FOUND if no channel found with given channel id
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * Delete a guild has user relation from the repository.
     * 
     * @param uid
     * @return ResponseEntity
     */
    @DeleteMapping("/guild/user/{uid}/{gid}")
    public ResponseEntity deleteGuildUser(@PathVariable final String uid
                                        , @PathVariable final String gid){

        Optional<List<GuildHasUser>> guildUserResult = guildHasUserRespository.findByUserId(uid);
        
        if(guildUserResult.isPresent()){

            guildUserResult.get().forEach(guildUser -> {
                if(guildUser.getGuildId().equals(gid)){
                    guildHasUserRespository.deleteById(guildUser.getId());
                }
            });

            // OK
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        // NOT FOUND if no user with this uid is found
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * Update a guild user nickname.
     * 
     * @param user
     * @param guild
     * @param nickname
     * @return ResponseEntity
     */
    @PatchMapping("/guild/user")
    public ResponseEntity updateGuildUser(@RequestParam(name="user") final String user
                                        , @RequestParam(name="guild") final String guild
                                        , @RequestParam(name="nickname") final String nickname){

        Optional<List<GuildHasUser>> guildUserResult = guildHasUserRespository.findByUserId(user);
        
        if(guildUserResult.isPresent()){

            guildUserResult.get().forEach(guildUser -> {
                if(guildUser.getGuildId().equals(guild)){
                    guildUser.setNickname(nickname);
                    guildHasUserRespository.save(guildUser);
                }
            });

            // OK
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        // NOT FOUND if no user with this uid is found
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * Find a guild using a access code.
     * 
     * @param code access code to find the guild with.
     * @return a quiz object.
     */
    @GetMapping("/guild/code/{code}")
    public ResponseEntity<Guild> getGuildWithCode(@PathVariable final String code) {

        // check if guild with access code exists
        Optional<Guild> guildResult = guildRepository.findByAccessCode(code);

        if(guildResult.isPresent()){

            // OK
            return ResponseEntity.status(HttpStatus.OK).body(guildResult.get());
        }

        // NOT FOUND if no guild found with given access code
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * Find a guild using an id.
     * 
     * @param id to find the guild with.
     * @return a quiz object.
     */
    @GetMapping("/guild/id/{id}")
    public ResponseEntity<Guild> getGuildWithID(@PathVariable final String id) {

        // check if guild with id exists
        Optional<Guild> guildResult = guildRepository.findById(id);

        if(guildResult.isPresent()){

            // OK
            return ResponseEntity.status(HttpStatus.OK).body(guildResult.get());
        }

        // NOT FOUND if no guild found with given id
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}