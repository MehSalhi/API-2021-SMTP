/**
 * API-SMTP
 * 09.12.2021
 * @author Guilain Mbayo
 * @author Mehdi Salhi
 */
package model.prank;

public class Prank {
    private String subject;
    private String body;

    public Prank(String message) {
        separateBodySubject(message);
    }

    /**
     * Gets the subject of the prank
     *
     * @return The Subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Gets the body of the prank
     *
     * @return The body
     */
    public String getBody() {
        return body;
    }

    /**
     * Separate the subject and the body from the message
     * This is used when we retrieve the message from the configuration file
     * since each message contains the subject and then the message on the
     * next line
     *
     * @param message The message to separate
     */
    public void separateBodySubject(String message) {
        subject = message.substring(0, message.indexOf("\n"));
        body = message.substring(message.indexOf("\n") + 1);
    }
}
