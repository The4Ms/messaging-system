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
        db.closeConnection();

        try { return result.getInt("total") == 1; 
        } catch (SQLException ex) { throw new RuntimeException(ex); }
    }

    @Override
    public boolean addNewUser(String username, String password, String fullname) {
        DatabaseHandler db = DatabaseHandlerProvider.getDatabaseHandler();
        int updated = db.excuteParameterizedQuery(CREATE_USER_QUERY, username, password, fullname);
        db.closeConnection();
        
        return updated == 1;
    }    
}
