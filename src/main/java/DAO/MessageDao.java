package DAO;

import Util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

import Model.Message;


public class MessageDao {
    public List<Message> getAllMessages(){
        List<Message> messages = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        
        String sql = "Select * from book";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
        
    }
    public Message createMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            
            String sql = "Insert into message (message_id, posted_by,message_text,time_posted_epoch) values (?,?,?,?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1,message.getMessage_id());
            preparedStatement.setInt(2,message.getPosted_by());
            preparedStatement.setString(3,message.getMessage_text());
            preparedStatement.setLong(4,message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            return message;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    
    }
    // public Message retrieveMessage(int id) {
    //     try {

    //     }
    //     catch {

    //     }
    //     return null;
    // }

    public void deleteMessage(int id) {

    }

    public void updateMessageText(int id) {

    }

    public List<Message> retrieveMessagesByUser(){
        List<Message> messages = new ArrayList<>();
        return messages;
        
    }
}
