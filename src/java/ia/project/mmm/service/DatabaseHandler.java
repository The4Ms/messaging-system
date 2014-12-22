/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ia.project.mmm.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mohamed Kamal
 */
public class DatabaseHandler {
    private Connection c;
    private static final String CONNECTION_STR = "jdbc:mysql://localhost/%s";
    
    public DatabaseHandler(String databaseName, String username, String password){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            String url = String.format(CONNECTION_STR, databaseName);
            
            c = DriverManager.getConnection(url, username, password);
            
        } catch (Exception e) {
            throw new RuntimeException("couldn't open a connection to the db", e);
        }
    }
    
    public void closeConnection(){
        try {
            c.close();
        } catch (SQLException ex) {
            throw new RuntimeException("error closing the connection", ex);
        }
    }
    
    public int excuteParameterizedQuery(String query, Object...parameters){
        PreparedStatement stmt = GetPreparedStatement(query, parameters);
        try {
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("couldn't excute the query " + query, ex);
        }
    }
    
    public ResultSet excuteParameterizedQueryRes(String query, Object...parameters){
        PreparedStatement stmt = GetPreparedStatement(query, parameters);
        try {
            return stmt.executeQuery();
        } catch (SQLException ex) {
            throw new RuntimeException("couldn't excute the query " + query, ex);
        }
    }
    private PreparedStatement GetPreparedStatement(String query, Object[] parameters){
        try{
            PreparedStatement stmt = c.prepareStatement(query);
            for(int i=0; i<parameters.length; i++){
                stmt.setObject(i, parameters[i]);
            }
            return stmt;
        } catch (SQLException ex) {
            throw new RuntimeException("couldn't creating the prepared statment for query " + query, ex);
        }
    }
    
//    private ResultSet excuteQueryRes(String query){
//        try {
//            Statement stmt = c.createStatement();
//            
//            return stmt.executeQuery(query);
//        } catch (SQLException ex) {
//            throw new RuntimeException("couldn't excute the query " + query, ex);
//        }
//        return null;
//    }
}
