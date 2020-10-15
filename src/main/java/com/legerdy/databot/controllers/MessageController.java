package com.legerdy.databot.controllers;

import java.util.Optional;

import com.legerdy.databot.models.Message;
import com.legerdy.databot.repositories.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
public class MessageController {
    
    @Autowired
    private MessageRepository messageRepository;

    /**
     * Creates a new message and saves it in the database.
     * 
     * @param user
     * @param channel
     * @param guild
     * @param content
     * @param typingTime
     * @param meme
     * @return a ResponseEntitity with the created Message object.
     */
    @PostMapping("/message")
    public ResponseEntity<Message> newMessage(@RequestParam(name="id") final String id
                                            , @RequestParam(name="user") final String user
                                            , @RequestParam(name="channel") final String channel
                                            , @RequestParam(name="guild") final String guild
                                            , @RequestParam(name="content") final String content
                                            , @RequestParam(name="typingTime") final float typingTime
                                            , @RequestParam(name="meme") final boolean meme
                                            , @RequestParam(name="file") final boolean file) {

        Message newMessage = new Message(id, user, channel, guild, content, typingTime, meme, file);

        try {
            messageRepository.save(newMessage);
        } catch (DataIntegrityViolationException e){
            // CONFLICT if parameters violate data integrity
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // CREATED
        return ResponseEntity.status(HttpStatus.CREATED).body(newMessage);
    }

    /**
     * Updates a message
     * 
     * @param id
     * @param content
     * @param meme
     * @return a ResponseEntity with the updated message.
     */
    @PatchMapping("/message")
    public ResponseEntity<Message> updateMessage(@RequestParam(name="id") final String id
                                            , @RequestParam(name="content") final String content
                                            , @RequestParam(name="meme") final boolean meme
                                            , @RequestParam(name="file") final boolean file) {

        Optional<Message> messageResponse = messageRepository.findById(id);

        if(messageResponse.isPresent()){

            Message message = messageResponse.get();
            message.setContent(content);
            message.setMeme(meme);
            message.setHasFile(file);

            try{
                messageRepository.save(message);
            } catch(DataIntegrityViolationException e) {
                // BAD REQUEST
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            // OK
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }

        // NOT FOUND if no message found with given message id
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * Remove a message from the repository.
     * 
     * @param id
     * @return ResponseEntity
     */
    @DeleteMapping("/message/{id}")
    public ResponseEntity deleteMessage(@PathVariable final String id) {
        
        Optional<Message> messageResult = messageRepository.findById(id);

        if(messageResult.isPresent()){
            
            messageRepository.deleteById(id);

            // OK
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        // NOT FOUND if no message with this id is found
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}