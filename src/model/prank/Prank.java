package model.prank;

public class Prank {
    private String subject;
    private String body;

    public Prank(String message){
        separateBodySubject(message);
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public void separateBodySubject(String message){
        //TODO: séparer les message en un subject et un body
    }
}
