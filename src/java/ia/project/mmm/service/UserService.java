/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ia.project.mmm.service;

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
}
