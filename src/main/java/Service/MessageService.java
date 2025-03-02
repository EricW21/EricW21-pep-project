package Service;
import Util.ConnectionUtil;
import Model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import DAO.MessageDao;
public class MessageService {
    MessageDao MessageDao;
    public MessageService() {
        this.MessageDao = new MessageDao();
    }
    public MessageService(MessageDao Message) {
        this.MessageDao= Message;
    }
    public List<Message> getAllMessages(){
        return this.MessageDao.getAllMessages();
    }
    public Message createMessage(Message Message) {
        if (Message.message_text=="") {
            return null;
        }
        return this.MessageDao.createMessage(Message);
    }
    public Message retrieveMessage(int id) {
        return this.MessageDao.retrieveMessage(id);
    }
    public Message deleteMessage(int id) {
        return this.MessageDao.deleteMessage(id);
    }
    public Message updateMessageText(int id, String message_text) {
        
        return this.MessageDao.updateMessageText(id, message_text);
    }
    public List<Message> retrieveMessagesByUser(int user_id){
        return this.MessageDao.retrieveMessagesByUser(user_id);
    }
}
