package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Util.ConnectionUtil;

import static org.mockito.ArgumentMatchers.nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.SQLException;

import Model.Message;
import DAO.AccountDao;
import DAO.MessageDao;
import Model.Account;
import Service.AccountService;
import Service.MessageService;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/messages", this::createMessageHandler);
        app.post("/login",this::processLoginHandler);
        app.post("/register",this::userRegisterHandler);
        app.get("/messages/{message_id}", this::retrieveMessageByIdHandler);
        app.get("/messages",this::retrieveAllMessagesHandler);
        app.delete("/messages/{message_id}",this::deleteMessageHandler);
        app.patch("/messages/{message_id}",this::updateMessageTextHandler);
        app.get("/accounts/{account_id}/messages",this::retrieveMessageHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void createMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.createMessage(message);
        if (addedMessage==null) {
            context.status(400);
        } 
        else {
            context.json(mapper.writeValueAsString(addedMessage));
        }
    }
    private void deleteMessageHandler(Context context) throws JsonProcessingException {
        
        ObjectMapper mapper = new ObjectMapper();
        
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message deleted = messageService.deleteMessage(messageId);
       
        if (deleted!=null) {
            context.json(mapper.writeValueAsString(deleted));
        }
    }

    private void retrieveMessageHandler(Context context) throws JsonProcessingException {
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        ObjectMapper mapper = new ObjectMapper();
        List<Message> userMessages = messageService.retrieveMessagesByUser(accountId);
        context.json(mapper.writeValueAsString(userMessages));
    }

    private void retrieveAllMessagesHandler(Context context) throws JsonProcessingException {
        List<Message> all_messages = messageService.getAllMessages();
        ObjectMapper mapper = new ObjectMapper();
        context.json(mapper.writeValueAsString(all_messages));
    }

    private void retrieveMessageByIdHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message retrieved = messageService.retrieveMessage(messageId);
        if (retrieved!=null) {
            context.json(mapper.writeValueAsString(retrieved));
        }
        return;
       
        
        
    }

    private void updateMessageTextHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        String messageText = mapper.readTree(context.body()).path("message_text").asText();
        Message updated = messageService.updateMessageText(messageId,messageText);
        if (updated==null){
            context.status(400);
        }
        else {
            context.json(mapper.writeValueAsString(updated));
        }
    }

    private void processLoginHandler(Context context) throws JsonProcessingException {
       
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account checkAccount = accountService.processLogin(account);
        if (checkAccount==null) {
            context.status(401);
            context.json("");
        } 
        else {
            context.json(mapper.writeValueAsString(checkAccount));
        }
    }
    private void userRegisterHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.newUser(account);
        if (addedAccount==null) {
            context.status(400);
        } 
        else {
            context.json(mapper.writeValueAsString(addedAccount));
        }
    }
    


}