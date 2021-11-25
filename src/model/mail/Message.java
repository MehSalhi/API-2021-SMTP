package model.mail;

public class Message {
    private String body;
    private String subject;
    private String sender;
    private String receivers;

    public Message(Person sender, String subject, String body, Person ... receivers) {
        this.body = body;
        this.subject = subject;
        this.sender = sender.getAdress();
        this.receivers = personArrayToString(receivers);
    }

    /**
     * Converts an array of string to a string
     * Used to format the receiver list from a list of argument to a String
     * "email1, email2, emai3"
     * @param stringArray
     * @return
     */
    public String personArrayToString(Person ... stringArray) {
        StringBuilder strB = new StringBuilder();
        
        if(stringArray.length == 0) {
            throw new RuntimeException("Receivers list cannot be empty.");
        }

        for(int i = 0; i < stringArray.length; ++i) {
            if(i != 0) {
                strB.append(", ");
            }
            strB.append(stringArray[i].getAdress());
        }

        return strB.toString();
    }

    public String getReceivers() {
        return receivers;
    }

    public String getBody() {
        return body;
    }

    public String getSubject() {
        return subject;
    }

    public String getSender() {
        return sender;
    }

    public String toString() {
        StringBuilder strB = new StringBuilder();
        strB.append("Sender: ");
        strB.append(getSender());
        strB.append("\n");

        strB.append("Receivers: ");
        strB.append(getReceivers());
        strB.append("\n");

        strB.append("Subject: ");
        strB.append(getSubject());
        strB.append("\n");

        strB.append("Body: \n");
        strB.append(getBody());
        strB.append("\n");

        return strB.toString();
    }


}
