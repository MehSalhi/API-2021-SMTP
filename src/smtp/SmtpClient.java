package smtp;

import model.mail.Message;

import java.io.*;
import java.net.Socket;

public class SmtpClient {
    private

    final String host;
    int port;
    PrintWriter os = null;
    InputStream is = null;
    final int BUFFER_SIZE = 1024;
    byte[] buffer = new byte[BUFFER_SIZE];
    ByteArrayOutputStream inBuffer = new ByteArrayOutputStream();

    String clientMsg = "";
    int newBytes;

    public SmtpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void sendMail(Message m) {
        Socket clientSocket;
        try {
            // Establishes the connexion with the SMTP server
            clientSocket = new Socket(host, port);
            System.out.println("DEBUG: connected");

            os = new PrintWriter(clientSocket.getOutputStream());
            is = clientSocket.getInputStream();

            // Reads the server's welcome message and extract it's name
            System.out.println("Receiving:");
            newBytes = is.read(buffer);
            inBuffer.write(buffer, 0, newBytes);
            System.out.println(inBuffer);

            clientMsg = "EHLO " + inBuffer.toString().split(" ")[1] + "\r";
            System.out.println("Sending: " + clientMsg);
            os.println(clientMsg);
            os.flush();

            // Reads the server's available options
            System.out.println("Receiving:");
            inBuffer.reset();
            newBytes = is.read(buffer);
            inBuffer.write(buffer, 0, newBytes);
            System.out.println(inBuffer);

            // starts sending the email

            clientMsg = "MAIL FROM:<" + m.getSender() + ">" + "\r";
            System.out.println("Sending: " + clientMsg);
            os.println(clientMsg);
            os.flush();

            System.out.println("Receiving:");
            inBuffer.reset();
            newBytes = is.read(buffer);
            inBuffer.write(buffer, 0, newBytes);
            System.out.println(inBuffer);

            // send each recipient
            for(String rec : m.getReceivers()) {
                clientMsg = "RCPT TO:<" + rec + ">" + "\r";
                System.out.println("Sending: " + clientMsg);
                os.println(clientMsg);
                os.flush();

                System.out.println("Receiving:");
                inBuffer.reset();
                newBytes = is.read(buffer);
                inBuffer.write(buffer, 0, newBytes);
                System.out.println(inBuffer);
            }

            // sends DATA
            clientMsg = "DATA" + "\r";
            System.out.println("Sending: " + clientMsg);
            os.println(clientMsg);
            os.flush();

            // attend: 354 Start mail input; end with <CRLF>.<CRLF>
            System.out.println("Receiving:");
            inBuffer.reset();
            newBytes = is.read(buffer);
            inBuffer.write(buffer, 0, newBytes);
            System.out.println(inBuffer);

            clientMsg = "From: " + m.getSender();
            System.out.println(clientMsg);
            os.println(clientMsg);
            os.flush();

            clientMsg = "Subject: " + m.getSubject();
            System.out.println(clientMsg);
            os.println(clientMsg);
            os.flush();

            os.println();
            os.flush();

            clientMsg = m.getBody() + "\r";
            System.out.println(clientMsg);
            os.println(clientMsg);
            os.flush();

            clientMsg = ".\r";
            os.println(clientMsg);
            os.flush();

            // attend confirmation
            System.out.println("Receiving:");
            inBuffer.reset();
            newBytes = is.read(buffer);
            inBuffer.write(buffer, 0, newBytes);
            System.out.println(inBuffer);

            // termine avec CRLF . CRLF
            // attend "250 OK"
            // envoie QUIT
            clientMsg = "quit + \r";
            System.out.println(clientMsg);
            os.println(clientMsg);
            os.flush();

            // Attend "221"
            System.out.println("Receiving:");
            inBuffer.reset();
            newBytes = is.read(buffer);
            inBuffer.write(buffer, 0, newBytes);
            System.out.println(inBuffer);

        } catch (Exception e) {

        } finally {
            //os.close();
            try {
                is.close();
            }
            catch (Exception e) {

            }

        }

    }
}
