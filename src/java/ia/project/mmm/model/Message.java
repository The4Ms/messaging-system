package ia.project.mmm.model;

import java.util.Date;

/**
 *
 * @author Mohamed Kamal
 */
public class Message {
    private int Id;
    private UserInfo[] receivers;
    private UserInfo sender;
    private String body;
    private String subject;
    private Date sentDate;
    private boolean unread;

    public Message() {
    }

    public Message(int Id, UserInfo[] receivers, UserInfo sender, String subject, String body, Date sentDate) {
        this.Id = Id;
        this.receivers = receivers;
        this.sender = sender;
        this.body = body;
        this.subject = subject;
        this.sentDate = sentDate;
    }
    
    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }
    
    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }
    
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }
    
    public UserInfo[] getReceivers() {
        return receivers;
    }

    public void setReceivers(UserInfo[] receivers) {
        this.receivers = receivers;
    }

    public UserInfo getSender() {
        return sender;
    }

    public void setSender(UserInfo sender) {
        this.sender = sender;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReceiversUsernames(){
        String to = receivers[0].getUsername();
        for(int i=1; i<receivers.length; i++){
            to += "," + receivers[i].getUsername();
        }
        return to;
    }
    
    @Override
    public String toString() {
        return "From: " + getSender().getUsername() + "\n" +
                "To: " + getReceiversUsernames() + "\n" +
                "Date: " + getSentDate() + "\n" +
                "Body:\n" + getBody();
    }
}
