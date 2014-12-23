package ia.project.mmm.service;

import ia.project.mmm.model.Message;

/**
 *
 * @author Mohamed Kamal
 */
public interface IMessageService {
    Message getMessageById(int messageId);
    
    Message[] getInboxOf(String username);
    Message[] getSentOf(String username);
    Message[] getDraftsOf(String username);
    Message[] getTrashOf(String username);
    
    void trashMessage(String username, int messageId);
    void deleteMessageForever(String username, int messageId);
    
    /***
     * 
     * @param senderUsername
     * @param receiversUsernames
     * @param subject
     * @param body
     * @return invalid usernames
     * @throws UsernameNotFound
     */
    String[] sendMessage(String senderUsername, String[] receiversUsernames, String subject, String body);
}
