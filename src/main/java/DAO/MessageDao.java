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
           
            
            String sql = "Insert into message (message_id, posted_by,message_text,time_posted_epoch) values (?,?,?,?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

           
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
    public Message retrieveMessage(int id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
        
            
            String sql = "Select * from book where message_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);

          
           
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                
                return message;
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessage(int id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
        
            
            String sql = "Select * from book where message_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);

          
           
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                String delete_sql = "Delete from message where message_id=?";
                PreparedStatement deleteStatement = connection.prepareStatement(delete_sql);
                deleteStatement.setInt(1,id);
    
            
                deleteStatement.executeUpdate();
                return message;
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message updateMessageText(int id, String message_text) {
        Connection connection = ConnectionUtil.getConnection();
        if (message_text.length()>255 || message_text.length()>0) {
            return null;
        }
        try {
            String sql = "Select * from book where message_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        message_text,
                        rs.getLong("time_posted_epoch"));
                String update_sql = "Update message SET message_text=? where message_id=?";
                PreparedStatement updateStatement = connection.prepareStatement(update_sql);
                updateStatement.setInt(2,id);
                updateStatement.setString(1,message_text);
    
            
                updateStatement.executeUpdate();
                return message;
                
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> retrieveMessagesByUser(int user_id){
        List<Message> messages = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        
        
        try {
            String sql = "Select * from book where posted_by=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,user_id);
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
}
