/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ia.project.mmm.service;

import ia.project.mmm.model.UserInfo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mohamed Kamal
 */
public class UserService implements IUserService{

    private static final String CHECK_USER_QUERY = "SELECT count(*) FROM user WHERE username=? AND password=?";
    private static final String CREATE_USER_QUERY = "INSERT INTO user(username, password, fullname) VALUES(?, ?, ?)";
    private static final String UPDATE_USER_QUERY = "UPDATE user SET fullname=?, password=? WHERE user.username=?";
    

    @Override
    public boolean isValidUser(String username, String password) {
        DatabaseHandler db = DatabaseHandlerProvider.getDatabaseHandler();
        ResultSet result = db.excuteParameterizedQueryRes(CHECK_USER_QUERY, username, password);
        
        boolean res = false;
        try {
            if(result.next()){
                res = result.getInt(1) == 1; 
            }
        } catch (SQLException ex) { throw new RuntimeException(ex); }
        
        db.closeConnection();
        return res;
    }

    @Override
    public boolean addNewUser(String username, String password, String fullname) {
        DatabaseHandler db = DatabaseHandlerProvider.getDatabaseHandler();
        int updated = 0;
        try{
            updated = db.excuteParameterizedQuery(CREATE_USER_QUERY, username, password, fullname);
        }
        catch(Exception ex){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "error", ex);
        }
        
        db.closeConnection();
        
        return updated == 1;
    }    

    @Override
    public void editUser(String username, String newFullname, String newPassword) {
        DatabaseHandler db = DatabaseHandlerProvider.getDatabaseHandler();
        int updated = db.excuteParameterizedQuery(UPDATE_USER_QUERY, newFullname, newPassword, username);
        
        db.closeConnection();
    }

    @Override
    public UserInfo getUserById(int userId) {
        DatabaseHandler databaseHandler = DatabaseHandlerProvider.getDatabaseHandler();
        String query = "SELECT username, fullname from user where id = ?";
        ResultSet resultSet = databaseHandler.excuteParameterizedQueryRes(query, userId);
        
        String username;
        String fullname;
        
        try{
            if(!resultSet.next())
                return null;
            
            username = resultSet.getString("username");
            fullname = resultSet.getString("fullname");
        }
        catch(Exception ex){ throw new RuntimeException(ex); }
        
        return new UserInfo(username, fullname);
    }
    
    @Override
    public UserInfo getUserByUsername(String username) {
        DatabaseHandler databaseHandler = DatabaseHandlerProvider.getDatabaseHandler();
        String query = "SELECT fullname from user where username = ?";
        ResultSet resultSet = databaseHandler.excuteParameterizedQueryRes(query, username);
        
        String fullname;
        
        try{
            if(!resultSet.next())
                return null;
            
            fullname = resultSet.getString("fullname");
        }
        catch(Exception ex){ throw new RuntimeException(ex); }
        
        return new UserInfo(username, fullname);
    }
}
