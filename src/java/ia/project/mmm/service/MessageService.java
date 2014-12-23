/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia.project.mmm.service;

import ia.project.mmm.model.Message;
import ia.project.mmm.model.UserInfo;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Eng. Mahmoud
 */
public class MessageService implements IMessageService{
    public final static int NOT_SEEN = 0;
    public final static int SEEN = 1;
    public final static int NOT_SEEN_TRASH = 2;
    public final static int SEEN_TRASH = 3;
    public final static int DELETED = 4;
    
    @Override
    public Message getMessageById(int messageId) {
        DatabaseHandler databaseHandler = DatabaseHandlerProvider.getDatabaseHandler();
        IUserService userService = ServiceLocater.getUserService();
        String query = "SELECT * from Message where id = ?";
        ResultSet resultSet = databaseHandler.excuteParameterizedQueryRes(query, messageId);
        
        int senderUserId;
        String messageSubject;
        String messageBody;
        Date messageSentDate;
        
        UserInfo messageSender;
        UserInfo messageReceivers[];
        ArrayList<UserInfo> tempMessageReceivers = new ArrayList<>();
        
        try{
            if(!resultSet.next())
                return null;
            
            senderUserId = resultSet.getInt("sender_User_id");
            messageSubject = resultSet.getString("subject");
            messageBody = resultSet.getString("body");
            messageSentDate = resultSet.getDate("sent_date");
        }
        catch(Exception ex){ throw new RuntimeException(ex); }
        
        messageSender = userService.getUserById(senderUserId);
        
        query = "SELECT receiver_User_id from Receiver where Message_id = ?";
        resultSet = databaseHandler.excuteParameterizedQueryRes(query, messageId);
        
        try{
            while(resultSet.next())
                tempMessageReceivers.add(
                        userService.getUserById(
                                resultSet.getInt("receiver_User_id")));
        }
        catch(Exception ex){ throw new RuntimeException(ex); }
        
        messageReceivers = (UserInfo[]) tempMessageReceivers.toArray();
        
        databaseHandler.closeConnection();
        return new Message(messageId, messageReceivers, messageSender,
                            messageSubject, messageBody, messageSentDate);
    }

    @Override
    public Message[] getInboxOf(String username) {
        DatabaseHandler databaseHandler = DatabaseHandlerProvider.getDatabaseHandler();
        String query = "SELECT id from User where username = ?";
        ResultSet resultSet = databaseHandler.excuteParameterizedQueryRes(query, username);
        
        int receiverUserId;
        
        try{ 
            if(!resultSet.next())
                return null;
            receiverUserId = resultSet.getInt("id");
        }
        catch(Exception ex){ throw new RuntimeException(ex); }
        
        query = "SELECT id, Message_id from Receiver where receiver_User_id = ?";
        resultSet = databaseHandler.excuteParameterizedQueryRes(query, receiverUserId);
        
        Message inboxMessages[];
        ArrayList<Message> tempInboxMessages = new ArrayList<>();
        
        try{
            while(resultSet.next()){
                int receivalId = resultSet.getInt("id");
                int messageId = resultSet.getInt("Message_id");
                
                String receiverStatusQuery = "SELECT status from ReceiverMessageStatus where Reciever_id = ?";
                ResultSet receiverStatusResultSet = databaseHandler.excuteParameterizedQueryRes(receiverStatusQuery, receivalId);
                if(!receiverStatusResultSet.next())
                    continue;
                
                int receiverStatus = receiverStatusResultSet.getInt("status");
                if(receiverStatus != SEEN && receiverStatus != NOT_SEEN)
                    continue;
                
                tempInboxMessages.add(getMessageById(messageId));
            }
        }
        catch(Exception ex){ throw new RuntimeException(ex); }
        
        inboxMessages = (Message[]) tempInboxMessages.toArray();
        databaseHandler.closeConnection();
        return inboxMessages;
    }

    @Override
    public Message[] getSentOf(String username) {
        DatabaseHandler databaseHandler = DatabaseHandlerProvider.getDatabaseHandler();
        IUserService userService = ServiceLocater.getUserService();
        String query = "SELECT * from User where username = ?";
        ResultSet resultSet = databaseHandler.excuteParameterizedQueryRes(query, username);
        
        int senderUserId;
        String senderFullName;
        
        try{ 
            if(!resultSet.next())
                return null;
            
            senderUserId = resultSet.getInt("id");
            senderFullName = resultSet.getString("fullname");
        }
        catch(Exception ex){ throw new RuntimeException(ex); }
        
        query = "SELECT * from Message where sender_User_id = ?";
        resultSet = databaseHandler.excuteParameterizedQueryRes(query, senderUserId);
        
        Message sentMessages[];
        ArrayList<Message> tempSentMessages = new ArrayList<>();
        
        try{
            while(resultSet.next()){
                int messageId = resultSet.getInt("id");
                String senderStatusQuery = "SELECT status from SenderMessageStatus where Message_id = ?";
                ResultSet senderStatusResultSet = databaseHandler.excuteParameterizedQueryRes(senderStatusQuery, messageId);
                if(!senderStatusResultSet.next())
                    continue;
                
                int senderStatus = senderStatusResultSet.getInt("status");
                if(senderStatus != SEEN)
                    continue;
                
                String messageSubject = resultSet.getString("subject");
                String messageBody = resultSet.getString("body");
                Date messageSentDate = resultSet.getDate("sent_date");
                UserInfo messageSender = new UserInfo(username, senderFullName);
                UserInfo messageReceivers[];
                ArrayList<UserInfo> tempMessageReceivers = new ArrayList<>();
                
                String receiversQuery = "SELECT receiver_User_id from Receiver where Message_id = ?";
                ResultSet receiversResultSet = databaseHandler.excuteParameterizedQueryRes(receiversQuery, messageId);
                while(receiversResultSet.next())
                    tempMessageReceivers.add(
                            userService.getUserById(
                                    receiversResultSet.getInt("receiver_User_id")));
                messageReceivers = (UserInfo[]) tempMessageReceivers.toArray();
                tempSentMessages.add(new Message(messageId, messageReceivers, messageSender,
                                                    messageSubject, messageBody, messageSentDate));
            }
        }
        catch(Exception ex){ throw new RuntimeException(ex); }
        
        sentMessages = (Message[]) tempSentMessages.toArray();
        databaseHandler.closeConnection();
        return sentMessages;
    }

    @Override
    public Message[] getDraftsOf(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Message[] getTrashOf(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void trashMessage(String username, int messageId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteMessageForever(String username, int messageId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[] sendMessage(String senderUsername, String[] receiversUsernames, String subject, String body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
