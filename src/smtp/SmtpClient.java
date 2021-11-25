package smtp;

import model.mail.Message;

import java.io.*;
import java.net.Socket;
import model.mail.*;

public class SmtpClient {
    public SmtpClient() {

    }

    public void sendMail(Message m) {
        Socket clientSocket = null;
        String host = "localhost";
        int port = 25;
        PrintWriter os = null;
        InputStream is = null;

        try {
            // Establishes the connexion with the SMTP server
            clientSocket = new Socket(host, port);
            System.out.println("DEBUG: connected");

            os = new PrintWriter(clientSocket.getOutputStream());
            is = clientSocket.getInputStream();
            final int BUFFER_SIZE = 1024;
            byte[] buffer = new byte[BUFFER_SIZE];
            ByteArrayOutputStream inBuffer = new ByteArrayOutputStream();

            boolean comm = true;
            String clientMsg = "";
            int newBytes;

            while(comm) {
                // le serveur parle en premier
                System.out.println("Receiving:");
                newBytes = is.read(buffer);
                inBuffer.write(buffer, 0, newBytes);
                System.out.println(inBuffer);

                // recoit une liste des commandes "250-xxx" gérées par le serveur
                // la dernier commande à le format
                // 250 sans le "-"

                // Récupérer le nom du serveur pour faire le EHLO
                // envoie de ehlo
                clientMsg = "EHLO " + inBuffer.toString().split(" ")[1] + "\r";
                System.out.println("Sending:");
                System.out.println(clientMsg);
                os.println(clientMsg);
                os.flush();

                System.out.println("Receiving:");
                inBuffer.reset();
                newBytes = is.read(buffer);
                inBuffer.write(buffer, 0, newBytes);
                System.out.println(inBuffer);

                // client envoie le sender

                clientMsg = "MAIL FROM:<" + m.getSender() + ">" + "\r";
                System.out.println("Sending:");
                System.out.println(clientMsg);
                os.println(clientMsg);
                os.flush();

                System.out.println("Receiving:");
                inBuffer.reset();
                newBytes = is.read(buffer);
                inBuffer.write(buffer, 0, newBytes);
                System.out.println(inBuffer);

                // client envoie les destinataires
                for(String rec : m.getReceivers()) {
                    clientMsg = "RCPT TO:<" + rec + ">" + "\r";
                    System.out.println("Sending:");
                    System.out.println(clientMsg);
                    os.println(clientMsg);
                    os.flush();

                    System.out.println("Receiving:");
                    inBuffer.reset();
                    newBytes = is.read(buffer);
                    inBuffer.write(buffer, 0, newBytes);
                    System.out.println(inBuffer);
                }

                // client envoie DATA
                clientMsg = "DATA" + "\r";
                System.out.println("Sending:");
                System.out.println(clientMsg);
                os.println(clientMsg);
                os.flush();

                // attend: 354 Start mail input; end with <CRLF>.<CRLF>
                System.out.println("Receiving:");
                inBuffer.reset();
                newBytes = is.read(buffer);
                inBuffer.write(buffer, 0, newBytes);
                System.out.println(inBuffer);

                // envoie sujet et body
                clientMsg = "From: " + m.getSender();
                System.out.println(clientMsg);
                os.println(clientMsg);
                os.flush();

                clientMsg = "Subject: " + m.getSubject();
                System.out.println(clientMsg);
                os.println(clientMsg);
                os.flush();

                clientMsg = m.getBody();
                System.out.println(clientMsg);
                os.println(clientMsg + "\r");
                os.flush();

                os.println("." + "\r");
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
                clientMsg = "quit";
                System.out.println("Sending:");
                System.out.println(clientMsg + "\r");
                os.println(clientMsg + "\r");
                os.flush();

                // Attend "221"
                System.out.println("Receiving:");
                inBuffer.reset();
                newBytes = is.read(buffer);
                inBuffer.write(buffer, 0, newBytes);
                System.out.println(inBuffer);

                comm = false;

                // fermer la connexion
            }

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
