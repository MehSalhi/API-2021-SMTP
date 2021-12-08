import model.mail.*;
import model.prank.*;
import smtp.*;
import util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;

public class MailBot {

    public static void main(String[] args) {
        //récupère toutes le personnes
        Person[] victims = Util.retrievePeople();
        Group[] groups;
        //récupère le nombre de groupes
        int nbGroups = Util.retrieveNbGroup();
        int peoplePerGroups;
        String host = Util.retrieveSmtpHostname();
        int port = Util.retrieveSmtpPort();
        //vérifie que le nombre de groupe n'est pas trop élevé
        if (victims.length < nbGroups) {
            throw new RuntimeException("pas assez de victims pour autant de groupes");
        }
        groups = new Group[nbGroups];
        peoplePerGroups = victims.length / nbGroups;

        //crée les groupes avec les personnes dedans
        Util.putPeopleInGroups(victims, groups, peoplePerGroups);

        //génére un message par groupe et l'envoi via le client smtp
        for (Group g : groups) {
            SmtpClient smtp = new SmtpClient(host, port);
            Message message = new PrankGenerator(g).getMessage();
            smtp.sendMail(message);

        }

    }

}
