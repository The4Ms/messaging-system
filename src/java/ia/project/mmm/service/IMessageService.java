package ia.project.mmm.service;

import ia.project.mmm.model.Message;

/**
 *
 * @author Mohamed Kamal
 */
public interface IMessageService {
    Message getMessageById(Long messageId);
    
    Message[] getInboxOf(String username) throws UsernameNotFound;
    Message[] getDraftsOf(String username) throws UsernameNotFound;
    Message[] getTrashOf(String username) throws UsernameNotFound;
    
    boolean sendMessage(String senderUsername, String[] receiversUsernames, String subject, String body) throws UsernameNotFound;
}
