package crm.model;

public class Lead {
    private int id;
    private int contactId;
    private Status status;
    private String source;
    private String createdAt; 

    public Lead(int id, int contactId, Status status, String source, String createdAt) {
        this.id = id;
        this.contactId = contactId;
        this.status = status;
        this.source = source;
        this.createdAt = createdAt;
    }
    public int getId() {return id;}
    public int getContactId() {return contactId;}
    public Status getStatus() {return status;}
    public String getSource() {return source;}
    public String getCreatedAt() {return createdAt;}

    public void setStatus(Status status) {this.status = status;}
    public void setSource(String source) {this.source = source;}

    public String toString() {
        return "Lead[id=" + id + ", contactId=" + contactId + ", status=" + status + ", source=" + source + ", createdAt=" + createdAt + "]";
    }


}