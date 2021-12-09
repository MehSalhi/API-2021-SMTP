/**
 * API-SMTP
 * 09.12.2021
 * @author Guilain Mbayo
 * @author Mehdi Salhi
 */
package smtp;

import model.mail.Message;

import java.io.*;
import java.net.Socket;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SmtpClient {

    private final String host;
    private final int port;
    private InputStream is = null;
    private final int BUFFER_SIZE = 1024;
    private final byte[] buffer = new byte[BUFFER_SIZE];
    private final ByteArrayOutputStream inBuffer = new ByteArrayOutputStream();

    private final static Logger LOGGER = Logger.getLogger(SmtpClient.class.getName());

    public SmtpClient(String host, int port) {
        this.host = host;
        this.port = port;
        LOGGER.setLevel(Level.INFO);
    }

    /**
     * Simple error handling function
     * Throw an exception if the answer from the server doesn't match the
     * expected String. Since the communication with the SMTP server is
     * straightforward, we expected to
     * receive something like "250 Ok" each time we sent something to the
     * server.
     *
     * @param expected The expected answer from the server
     */
    private void throwExceptionIfAnswerIsNot(String expected) {
        if (!inBuffer.toString().startsWith(expected)) {
            throw new RuntimeException("Error while communicating with " +
                    "the server");
        }
    }

    /**
     * Sends the message using the smtp client
     *
     * @param m the message
     * @return True if the message was sent correctly
     */
    public boolean sendMail(Message m) {
        Socket clientSocket;
        boolean messageOK;
        try {
            // Establishes the connexion with the SMTP server
            clientSocket = new Socket(host, port);
            //System.out.println("DEBUG: connected");
            // initializes the in/out streams
            PrintWriter os = new PrintWriter(clientSocket.getOutputStream());
            is = clientSocket.getInputStream();

            // Reads the server's welcome message and extract its name
            int newBytes = is.read(buffer);
            inBuffer.write(buffer, 0, newBytes);
            throwExceptionIfAnswerIsNot("220");
            LOGGER.log(Level.INFO, inBuffer.toString());

            StringBuilder clientMsg = new StringBuilder("EHLO " + inBuffer.toString().split(" ")[1] + "\r");
            LOGGER.log(Level.INFO, "Sending: " + clientMsg);
            os.println(clientMsg);
            os.flush();

            // Reads the server's available options
            inBuffer.reset();
            newBytes = is.read(buffer);
            inBuffer.write(buffer, 0, newBytes);
            throwExceptionIfAnswerIsNot("250");
            LOGGER.log(Level.INFO, "Receiving: " + inBuffer);

            // starts sending the email

            clientMsg = new StringBuilder("MAIL FROM:<" + m.getSender() + ">" + "\r");
            LOGGER.log(Level.INFO, "Sending: " + clientMsg);
            os.println(clientMsg);
            os.flush();

            inBuffer.reset();
            newBytes = is.read(buffer);
            inBuffer.write(buffer, 0, newBytes);
            throwExceptionIfAnswerIsNot("250");
            LOGGER.log(Level.INFO, "Receiving: " + inBuffer);

            // send each recipient
            for (String rec : m.getReceivers()) {
                clientMsg = new StringBuilder("RCPT TO:<" + rec + ">" + "\r");
                LOGGER.log(Level.INFO, "Sending: " + clientMsg);
                os.println(clientMsg);
                os.flush();

                inBuffer.reset();
                newBytes = is.read(buffer);
                inBuffer.write(buffer, 0, newBytes);
                throwExceptionIfAnswerIsNot("250");
                LOGGER.log(Level.INFO, "Receiving: " + inBuffer);
            }

            // sends DATA
            clientMsg = new StringBuilder("DATA" + "\r");
            LOGGER.log(Level.INFO, "Sending: " + clientMsg);
            os.println(clientMsg);
            os.flush();

            // attend: 354 Start mail input; end with <CRLF>.<CRLF>
            inBuffer.reset();
            newBytes = is.read(buffer);
            inBuffer.write(buffer, 0, newBytes);
            throwExceptionIfAnswerIsNot("354");
            LOGGER.log(Level.INFO, "Receiving: " + inBuffer);

            clientMsg = new StringBuilder("From: " + m.getSender());
            LOGGER.log(Level.INFO, "Sending: " + clientMsg);
            os.println(clientMsg);
            os.flush();


            clientMsg = new StringBuilder("To: ");
            for (int i = 0; i < m.getReceivers().length; ++i) {
                if (i != 0) {
                    clientMsg.append(", ");
                }
                clientMsg.append(m.getReceivers()[i]);

            }
            clientMsg.append("\r");
            LOGGER.log(Level.INFO, "Sending: " + clientMsg);
            os.println(clientMsg);
            os.flush();

            clientMsg = new StringBuilder("Subject: " + m.getSubject() + "\r");
            clientMsg.append("Content-Type: text/plain; charset=utf-8\r");
            LOGGER.log(Level.INFO, "Sending: " + clientMsg);
            os.println(clientMsg);
            os.flush();

            os.println();
            os.flush();

            clientMsg = new StringBuilder(m.getBody() + "\r");
            LOGGER.log(Level.INFO, "Sending: " + clientMsg);
            os.println(clientMsg);
            os.flush();

            clientMsg = new StringBuilder(".\r");
            LOGGER.log(Level.INFO, "Sending: " + clientMsg);
            os.println(clientMsg);
            os.flush();

            // attend confirmation
            inBuffer.reset();
            newBytes = is.read(buffer);
            inBuffer.write(buffer, 0, newBytes);
            throwExceptionIfAnswerIsNot("250");
            LOGGER.log(Level.INFO, "Receiving: " + inBuffer);

            // termine avec CRLF . CRLF
            // attend "250 OK"
            // envoie QUIT
            clientMsg = new StringBuilder("QUIT \r");
            LOGGER.log(Level.INFO, "Sending: " + clientMsg);
            os.println(clientMsg);
            os.flush();

            // Attend "221"
            inBuffer.reset();
            newBytes = is.read(buffer);
            inBuffer.write(buffer, 0, newBytes);

            messageOK = inBuffer.toString().startsWith("221");
            throwExceptionIfAnswerIsNot("221");
            LOGGER.log(Level.INFO, "Receiving: " + inBuffer);


        } catch (Exception e) {
            throw new RuntimeException("Erreur de communication avec le " +
                    "serveur SMTP.");
        } finally {
            //os.close();
            try {
                is.close();
            } catch (Exception e) {
                System.out.println("Erreur de fermeture des streams");
            }

        }
        return messageOK;
    }
}
