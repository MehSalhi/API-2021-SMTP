/**
 * API-SMTP
 * 09.12.2021
 * @author Guilain Mbayo
 * @author Mehdi Salhi
 */
package model.mail;

public class Message {
    private final String body;
    private final String subject;
    private final String sender;
    private final String[] receivers;

    public Message(Person sender, String subject, String body, Person... receivers) {
        this.body = body;
        this.subject = subject;
        this.sender = sender.getAddress();
        this.receivers = personArrayToStringArray(receivers);
    }

    /**
     * Converts an array of string to a string
     * Used to format the receiver list from a list of argument to a String
     * "email1, email2, email3"
     *
     * @param stringArray Tableau de personnes
     * @return Un tableau de String
     */
    public String[] personArrayToStringArray(Person... stringArray) {
        String[] personStrArray = new String[stringArray.length];

        if (stringArray.length == 0) {
            throw new RuntimeException("Receivers list cannot be empty.");
        }

        for (int i = 0; i < stringArray.length; ++i) {
            personStrArray[i] = stringArray[i].getAddress();
        }

        return personStrArray;
    }

    /**
     * Gets the receivers
     *
     * @return The receivers
     */
    public String[] getReceivers() {
        return receivers;
    }

    /**
     * Gets the receivers and concatenates them into a string
     *
     * @return A string containing all the receivers separated by ", "
     */
    public String getReceiversToString() {
        StringBuilder strB = new StringBuilder();
        for (int i = 0; i < getReceivers().length; ++i) {
            if (i != 0) {
                strB.append(", ");
            }
            strB.append(getReceivers()[i]);
        }
        return strB.toString();
    }

    /**
     * Get the body
     *
     * @return The Body of the message
     */
    public String getBody() {
        return body;
    }

    /**
     * Get the subject
     *
     * @return The subject of the message
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Get the sender
     *
     * @return The sender of the message
     */
    public String getSender() {
        return sender;
    }

    /**
     * Converts a message into a String to print it out
     *
     * @return The message converted into a String
     */
    public String toString() {
        StringBuilder strB = new StringBuilder();
        strB.append("Sender: ");
        strB.append(getSender());
        strB.append("\n");

        strB.append("Receivers: ");
        for (int i = 0; i < getReceivers().length; ++i) {
            if (i != 0) {
                strB.append(", ");
            }
            strB.append(getReceivers()[i]);
        }
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
