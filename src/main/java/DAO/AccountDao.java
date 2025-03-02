package DAO;
import Util.ConnectionUtil;
import Model.Account;

import java.sql.*;
// import java.util.ArrayList;
// import java.util.List;
public class AccountDao {
    public Account newUser(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            
            
            String sql = "Insert into account (username,password) values (?,?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            
            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2,account.getPassword());
            
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(),account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Account processLogin(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            
            
            String sql = "Select * from account where username=? and password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2,account.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account match = new Account(rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password"));

                return match;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        

        return null;
    }

}
