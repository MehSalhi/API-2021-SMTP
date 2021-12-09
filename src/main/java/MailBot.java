/**
 * API-SMTP
 * 09.12.2021
 * @author Guilain Mbayo
 * @author Mehdi Salhi
 */
import model.mail.*;
import model.prank.*;
import smtp.*;
import util.*;

public class MailBot {

    /**
     * Main application that will retrieve the email campaign's information
     * from the configuration files, create groups and prank and then send
     * the email via our SMTP client.
     * @param args Application arguments
     */
    public static void main(String[] args) {
        //récupère toutes les personnes
        Person[] victims = Util.retrievePeople();
        Group[] groups;
        //récupère le nombre de groupes
        int nbGroups = Util.retrieveNbGroup();
        int peoplePerGroups;
        String host = Util.retrieveSmtpHostname();
        int port = Util.retrieveSmtpPort();
        //vérifie que le nombre de groupes n'est pas trop élevé
        if (victims.length < nbGroups) {
            throw new RuntimeException("pas assez de victims pour autant de groupes");
        }
        groups = new Group[nbGroups];
        peoplePerGroups = victims.length / nbGroups;

        //crée les groupes avec les personnes dedans
        Util.putPeopleInGroups(victims, groups, peoplePerGroups);

        //généré un message par groupe et l'envoi via le client smtp
        for (Group g : groups) {
            SmtpClient smtp = new SmtpClient(host, port);
            Message message = new PrankGenerator(g).getMessage();
            smtp.sendMail(message);

        }

    }

}
