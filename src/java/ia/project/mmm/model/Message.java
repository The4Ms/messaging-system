package ia.project.mmm.model;

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

    public Message() {
    }

    public Message(int Id, UserInfo[] receivers, UserInfo sender, String subject, String body) {
        this.Id = Id;
        this.receivers = receivers;
        this.sender = sender;
        this.body = body;
        this.subject = subject;
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
    
    
}
