package model.mail;
import java.io.*;

public class Message {
    private String text;
    private String subject;
    private String sender;
    private String receivers;

    public Message(Person sender, String subject, String text, Person ... receivers) {
        this.text = text;
        this.subject = subject;
        this.sender = sender.getAdress();
        this.receivers = personArrayToString(receivers);
    }

    /**
     * Converts an array of string to a string
     * Used to format the receiver list
     * @param stringArray
     * @return
     */
    String personArrayToString(Person ... stringArray) {
        StringBuilder strB = new StringBuilder();

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
}
