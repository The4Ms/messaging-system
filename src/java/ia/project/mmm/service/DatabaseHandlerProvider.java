/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ia.project.mmm.service;

/**
 *
 * @author Mohamed Kamal
 */
class DatabaseHandlerProvider {
    String databaseName = "";
    String username = "";
    String password = "";
    
    public static DatabaseHandler getDatabaseHandler(){
        return new DatabaseHandler("msg", "msg", "msg");
    }
}
