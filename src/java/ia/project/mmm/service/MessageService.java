/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia.project.mmm.service;

import ia.project.mmm.model.Message;
import ia.project.mmm.model.UserInfo;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    public final static int DRAFT = 4;
    public final static int DELETED = 5;
    
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
        catch(Exception ex){ 
            databaseHandler.closeConnection();
            throw new RuntimeException(ex);
        }
        
        messageSender = userService.getUserById(senderUserId);
        
        query = "SELECT receiver_User_id from Receiver where Message_id = ?";
        resultSet = databaseHandler.excuteParameterizedQueryRes(query, messageId);
        
        try{
            while(resultSet.next())
                tempMessageReceivers.add(
                        userService.getUserById(
                                resultSet.getInt("receiver_User_id")));
        }
        catch(Exception ex){ 
            databaseHandler.closeConnection();
            throw new RuntimeException(ex);
        }
        
        messageReceivers = new UserInfo[tempMessageReceivers.size()];
        for(int i=0;i<tempMessageReceivers.size();++i)
            messageReceivers[i] = tempMessageReceivers.get(i);
        
        databaseHandler.closeConnection();
        return new Message(messageId, messageReceivers, messageSender,
                            messageSubject, messageBody, messageSentDate);
    }

    @Override
    public Message[] getInboxOf(String username) {
        return getSentTo(username, SEEN, NOT_SEEN);
    }

    @Override
    public Message[] getSentOf(String username) {
        return getSentOf(username, SEEN);
    }

    @Override
    public Message[] getDraftsOf(String username) {
        return getSentOf(username, DRAFT);
    }

    @Override
    public Message[] getTrashOf(String username) {
        Message trashSentMessages[] = getSentOf(username, SEEN_TRASH);
        Message trashReceivedMessages[] = getSentTo(username, SEEN_TRASH, NOT_SEEN_TRASH);
        Message trashMessages[] = new Message[trashSentMessages.length + trashReceivedMessages.length];
        
        for(int i=0;i<trashSentMessages.length;++i)
            trashMessages[i] = trashSentMessages[i];
        
        for(int i=0;i<trashReceivedMessages.length;++i)
            trashMessages[trashSentMessages.length + i] = trashReceivedMessages[i];
        
        return trashMessages;
    }

    @Override
    public void trashMessage(String username, int messageId) {
        DatabaseHandler databaseHandler = DatabaseHandlerProvider.getDatabaseHandler();
        
        String query = "SELECT id from User where username = ?";
        ResultSet resultSet = databaseHandler.excuteParameterizedQueryRes(query, username);
        int userId;
        
        try{ 
            if(!resultSet.next())
                return;
            userId = resultSet.getInt("id");
        }
        catch(Exception ex){ 
            databaseHandler.closeConnection();
            throw new RuntimeException(ex);
        }
        
        try{
            query = "SELECT id from Message where sender_User_id = ? and id = ?";
            resultSet = databaseHandler.excuteParameterizedQueryRes(query, userId, messageId);
            if(resultSet.next()){
                query = "UPDATE SenderMessageStatus set status = ? where Message_id = ?";
                databaseHandler.excuteParameterizedQuery(query, SEEN_TRASH, messageId);
                return;
            }
            
            query = "SELECT id from Receiver where receiver_User_id = ? and Message_id = ?";
            resultSet = databaseHandler.excuteParameterizedQueryRes(query, userId, messageId);
            if(resultSet.next()){
                int receivalId = resultSet.getInt("id");
                query = "SELECT status from ReceiverMessageStatus where Reciever_id = ?";
                resultSet = databaseHandler.excuteParameterizedQueryRes(query, receivalId);
                resultSet.next();
                int status = resultSet.getInt("status");
                int newStatus = status;
                if(status == SEEN)
                    newStatus = SEEN_TRASH;
                else if(status == NOT_SEEN)
                    newStatus = NOT_SEEN_TRASH;
                
                query = "UPDATE ReceiverMessageStatus set status = ? where Reciever_id = ?";
                databaseHandler.excuteParameterizedQuery(query, newStatus, receivalId);
                return;
            }
        }
        catch(Exception ex){ 
            databaseHandler.closeConnection();
            throw new RuntimeException(ex);
        }
        databaseHandler.closeConnection();
    }

    @Override
    public void deleteMessageForever(String username, int messageId) {
        DatabaseHandler databaseHandler = DatabaseHandlerProvider.getDatabaseHandler();
        
        String query = "SELECT id from User where username = ?";
        ResultSet resultSet = databaseHandler.excuteParameterizedQueryRes(query, username);
        int userId;
        
        try{ 
            if(!resultSet.next())
                return;
            userId = resultSet.getInt("id");
        }
        catch(Exception ex){ 
            databaseHandler.closeConnection();
            throw new RuntimeException(ex);
        }
        
        try{
            query = "SELECT id from Message where sender_User_id = ? and id = ?";
            resultSet = databaseHandler.excuteParameterizedQueryRes(query, userId, messageId);
            if(resultSet.next()){
                query = "UPDATE SenderMessageStatus set status = ? where Message_id = ?";
                databaseHandler.excuteParameterizedQuery(query, DELETED, messageId);
                return;
            }
            
            query = "SELECT id from Receiver where receiver_User_id = ? and Message_id = ?";
            resultSet = databaseHandler.excuteParameterizedQueryRes(query, userId, messageId);
            if(resultSet.next()){
                int receivalId = resultSet.getInt("id");
                query = "UPDATE ReceiverMessageStatus set status = ? where Reciever_id = ?";
                databaseHandler.excuteParameterizedQuery(query, DELETED, receivalId);
                return;
            }
        }
        catch(Exception ex){ 
            databaseHandler.closeConnection();
            throw new RuntimeException(ex);
        }
        checkMessageForDeletion(messageId);
        databaseHandler.closeConnection();
    }
    
    @Override
    public void markMessageAsSeen(String username, int messageId) {
        DatabaseHandler databaseHandler = DatabaseHandlerProvider.getDatabaseHandler();
        
        String query = "SELECT id from User where username = ?";
        ResultSet resultSet = databaseHandler.excuteParameterizedQueryRes(query, username);
        int userId;
        
        try{ 
            if(!resultSet.next())
                return;
            userId = resultSet.getInt("id");
        }
        catch(Exception ex){ 
            databaseHandler.closeConnection();
            throw new RuntimeException(ex);
        }
        
        try{
            query = "SELECT id from Receiver where receiver_User_id = ? and Message_id = ?";
            resultSet = databaseHandler.excuteParameterizedQueryRes(query, userId, messageId);
            if(resultSet.next()){
                int receivalId = resultSet.getInt("id");
                query = "UPDATE ReceiverMessageStatus set status = ? where Reciever_id = ?";
                databaseHandler.excuteParameterizedQuery(query, SEEN, receivalId);
                return;
            }
        }
        catch(Exception ex){ 
            databaseHandler.closeConnection();
            throw new RuntimeException(ex);
        }
        databaseHandler.closeConnection();
    }

    @Override
    public String[] sendMessage(String senderUsername, String[] receiversUsernames, String subject, String body) {
        DatabaseHandler databaseHandler = DatabaseHandlerProvider.getDatabaseHandler();
        
        int messageId = 0;
        int senderUserId = 0;
        ArrayList<Integer> receiversIds = new ArrayList<>();
        ArrayList<String> failedUsernames = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        Date date = new Date();
        
        String query = "SELECT id from User where username = ?";
        ResultSet resultSet = null;
        
        try{
            resultSet = databaseHandler.excuteParameterizedQueryRes(query,senderUsername);
            if(resultSet.next())
                senderUserId = resultSet.getInt("id");
            else return receiversUsernames;
            
            for(int i=0;i<receiversUsernames.length;++i){
                resultSet = databaseHandler.excuteParameterizedQueryRes(query,receiversUsernames[i]);
                if(resultSet.next())
                    receiversIds.add(resultSet.getInt("id"));
                else failedUsernames.add(receiversUsernames[i]);
            }
        }
        catch(Exception ex){ 
            databaseHandler.closeConnection();
            throw new RuntimeException(ex);
        }
        
        if(receiversIds.size() == 0)
            return receiversUsernames;
        
        query = "INSERT into Message (sender_User_id,subject,body,sent_date) "
                + "values (?,?,?,?)";
        databaseHandler.excuteParameterizedQuery(query, senderUserId, subject, body, dateFormat.format(date));
        
        query = "SELECT id from Message where sender_User_id = ? and sent_date = ?";
        resultSet = databaseHandler.excuteParameterizedQueryRes(query, senderUserId, dateFormat.format(date));
        try{
            resultSet.next();
            messageId = resultSet.getInt("id");
        }
        catch(Exception ex){ 
            databaseHandler.closeConnection();
            throw new RuntimeException(ex);
        }
        
        query = "INSERT into SenderMessageStatus (Message_id,status) values (?,?)";
        databaseHandler.excuteParameterizedQuery(query, messageId, SEEN);
        
        for(int i=0;i<receiversIds.size();++i){
            query = "INSERT into Receiver (receiver_User_id,Message_id) values (?,?)";
            databaseHandler.excuteParameterizedQuery(query, receiversIds.get(i), messageId);
            
            query = "SELECT id from Receiver where receiver_User_id = ? and Message_id = ?";
            resultSet = databaseHandler.excuteParameterizedQueryRes(query, receiversIds.get(i), messageId);
            int receivalId = 0;
            try{
                resultSet.next();
                receivalId = resultSet.getInt("id");
            }
            catch(Exception ex){ 
                databaseHandler.closeConnection();
                throw new RuntimeException(ex);
            }
            
            query = "INSERT into ReceiverMessageStatus (Reciever_id,status) values (?,?)";
            databaseHandler.excuteParameterizedQuery(query, receivalId, NOT_SEEN);
        }
        
        String failedUsernamesArray[] = new String[failedUsernames.size()];
        for(int i=0;i<failedUsernames.size();++i)
            failedUsernamesArray[i] = failedUsernames.get(i);
        databaseHandler.closeConnection();
        return failedUsernamesArray;
    }
    
    private Message[] getSentOf(String username, int messageStatus){
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
        catch(Exception ex){ 
            databaseHandler.closeConnection();
            throw new RuntimeException(ex);
        }
        
        query = "SELECT * from Message where sender_User_id = ?";
        resultSet = databaseHandler.excuteParameterizedQueryRes(query, senderUserId);
        
        Message sentMessages[];
        ArrayList<Message> tempSentMessages = new ArrayList<>();
        
        try{
            String senderStatusQuery = "SELECT status from SenderMessageStatus where Message_id = ?";
            String receiversQuery = "SELECT receiver_User_id from Receiver where Message_id = ?";
            while(resultSet.next()){
                int messageId = resultSet.getInt("id");
                ResultSet senderStatusResultSet = databaseHandler.excuteParameterizedQueryRes(senderStatusQuery, messageId);
                if(!senderStatusResultSet.next())
                    continue;
                
                int senderStatus = senderStatusResultSet.getInt("status");
                if(senderStatus != messageStatus)
                    continue;
                
                String messageSubject = resultSet.getString("subject");
                String messageBody = resultSet.getString("body");
                Date messageSentDate = resultSet.getDate("sent_date");
                UserInfo messageSender = new UserInfo(username, senderFullName);
                UserInfo messageReceivers[];
                ArrayList<UserInfo> tempMessageReceivers = new ArrayList<>();
                
                ResultSet receiversResultSet = databaseHandler.excuteParameterizedQueryRes(receiversQuery, messageId);
                while(receiversResultSet.next())
                    tempMessageReceivers.add(
                            userService.getUserById(
                                    receiversResultSet.getInt("receiver_User_id")));
                
                messageReceivers = new UserInfo[tempMessageReceivers.size()];
                for(int i=0;i<tempMessageReceivers.size();++i)
                    messageReceivers[i] = tempMessageReceivers.get(i);
                tempSentMessages.add(new Message(messageId, messageReceivers, messageSender,
                                                    messageSubject, messageBody, messageSentDate));
            }
        }
        catch(Exception ex){ 
            databaseHandler.closeConnection();
            throw new RuntimeException(ex);
        }
        
        sentMessages = new Message[tempSentMessages.size()];
        for(int i=0;i<tempSentMessages.size();++i)
            sentMessages[i] = tempSentMessages.get(i);
        databaseHandler.closeConnection();
        return sentMessages;
    }
    
    private Message[] getSentTo(String username, int...messageStatus){
        ArrayList<Integer> messageStatusList = new ArrayList<>();
        for(int i=0;i<messageStatus.length;++i)
            messageStatusList.add(messageStatus[i]);
        
        DatabaseHandler databaseHandler = DatabaseHandlerProvider.getDatabaseHandler();
        
        String query = "SELECT id from User where username = ?";
        ResultSet resultSet = databaseHandler.excuteParameterizedQueryRes(query, username);
        int receiverUserId;
        
        try{ 
            if(!resultSet.next())
                return null;
            receiverUserId = resultSet.getInt("id");
        }
        catch(Exception ex){ 
            databaseHandler.closeConnection();
            throw new RuntimeException(ex);
        }
        
        query = "SELECT id, Message_id from Receiver where receiver_User_id = ?";
        resultSet = databaseHandler.excuteParameterizedQueryRes(query, receiverUserId);
        
        Message inboxMessages[];
        ArrayList<Message> tempInboxMessages = new ArrayList<>();
        
        try{
            String receiverStatusQuery = "SELECT status from ReceiverMessageStatus where Reciever_id = ?";
            while(resultSet.next()){
                int receivalId = resultSet.getInt("id");
                int messageId = resultSet.getInt("Message_id");
                
                ResultSet receiverStatusResultSet = databaseHandler.excuteParameterizedQueryRes(receiverStatusQuery, receivalId);
                if(!receiverStatusResultSet.next())
                    continue;
                
                int receiverStatus = receiverStatusResultSet.getInt("status");
                if(!messageStatusList.contains(receiverStatus))
                    continue;
                
                tempInboxMessages.add(getMessageById(messageId));
            }
        }
        catch(Exception ex){ 
            databaseHandler.closeConnection();
            throw new RuntimeException(ex);
        }
        
        inboxMessages = new Message[tempInboxMessages.size()];
        for(int i=0;i<tempInboxMessages.size();++i)
            inboxMessages[i] = tempInboxMessages.get(i);
        
        databaseHandler.closeConnection();
        return inboxMessages;
    }
    
    private void checkMessageForDeletion(int messageId){
        DatabaseHandler databaseHandler = DatabaseHandlerProvider.getDatabaseHandler();
        String query = "SELECT COUNT(Message_id) as IdCount "
                        + "from SenderMessageStatus "
                        + "where status != ? and Message_id = ?";
        ResultSet resultSet = databaseHandler.excuteParameterizedQueryRes(query, DELETED, messageId);
        
        try{
            resultSet.next();
            if(resultSet.getInt("IdCount") != 0)
                return;
        }
        catch(Exception ex){ 
            databaseHandler.closeConnection();
            throw new RuntimeException(ex);
        }
        
        query = "SELECT COUNT(Receiver.id) as IdCount "
                + "from Receiver inner join ReceiverMessageStatus "
                + "on Receiver.id = ReceiverMessageStatus.Reciever_id "
                + "where ReceiverMessageStatus.status != ? and Receiver.Message_id = ?";
        resultSet = databaseHandler.excuteParameterizedQueryRes(query, DELETED, messageId);
        
        try{
            resultSet.next();
            if(resultSet.getInt("IdCount") != 0)
                return;
        }
        catch(Exception ex){ 
            databaseHandler.closeConnection();
            throw new RuntimeException(ex);
        }
        
        query = "SELECT Receiver.id as id "
                + "from Receiver inner join ReceiverMessageStatus "
                + "on Receiver.id = ReceiverMessageStatus.Reciever_id "
                + "where Receiver.Message_id = ?";
        resultSet = databaseHandler.excuteParameterizedQueryRes(query, messageId);
        
        try{
            query = "DELETE from ReceiverMessageStatus where Reciever_id = ?";
            while(resultSet.next()){
                int receivalId = resultSet.getInt("id");
                databaseHandler.excuteParameterizedQuery(query,receivalId);
            }
        }
        catch(Exception ex){ 
            databaseHandler.closeConnection();
            throw new RuntimeException(ex);
        }
        
        query = "DELETE from Message where id = ?";
        databaseHandler.excuteParameterizedQuery(query,messageId);
        
        query = "DELETE from SenderMessageStatus where Message_id = ?";
        databaseHandler.excuteParameterizedQuery(query,messageId);
        
        query = "DELETE from Receiver where Message_id = ?";
        databaseHandler.excuteParameterizedQuery(query,messageId);
        databaseHandler.closeConnection();
    }
}
