package model.prank;

public class Prank {
    private String subject;
    private String body;

    public Prank(String subject, String body){
        this.subject = subject;
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
