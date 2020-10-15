package com.legerdy.databot.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;

import com.legerdy.databot.models.Channel;
import com.legerdy.databot.models.Message;
import com.legerdy.databot.repositories.ChannelRepository;
import com.legerdy.databot.repositories.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
public class StatsController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @GetMapping("stats/{gid}/messages")
    public ResponseEntity<ArrayList<MessageResponse>> getMessages(@PathVariable final String gid) {
        
        ArrayList<MessageResponse> result = new ArrayList<>();
        Iterator<Message> queryResult = messageRepository.findAllByGuildId(gid).iterator();

        while(queryResult.hasNext()){

            Message message = queryResult.next();

            MessageResponse messageResponse = new MessageResponse();
            messageResponse.date = message.getDate();
            messageResponse.meme = message.isMeme();
            messageResponse.hasFile = message.isHasFile();
            messageResponse.channel = message.getChannelId();

            result.add(messageResponse);
        }

        if(result.isEmpty()){
            // NOT FOUND if no messages found for given parameters
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

       // OK
       return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    private class MessageResponse {
        public Date date;
        public boolean meme;
        public boolean hasFile;
        public String channel;
    }
}