package ia.project.mmm.model;

/**
 *
 * @author Mohamed Kamal
 */
public class Message {
    private Long Id;
    private UserInfo receivers;
    private UserInfo sender;
    private String body;
    private String subject;

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }
    
    public UserInfo getReceivers() {
        return receivers;
    }

    public void setReceivers(UserInfo receivers) {
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
