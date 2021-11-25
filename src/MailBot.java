import model.mail.*;
import model.prank.*;
import smtp.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;

public class MailBot {

    public static void main(String[] args){
        //récupère toutes le personnes
        Person[] victims = retrievePeople();
        Group[] groups;
        //récupère le nombre de groupes
        int nbGroups = retrieveNbGroup();
        int peoplePerGroups;
        //vérifie que le nombre de groupe n'est pas trop élevé
        if(victims.length < nbGroups){
            throw new RuntimeException("pas assez de victims pour autant de groupes");
        }
        groups = new Group[nbGroups];
        peoplePerGroups = victims.length/nbGroups;

        //crée les groupes avec les personnes dedans
        putPeopleInGroups(victims, groups, peoplePerGroups);
        SmtpClient smtp = new SmtpClient();
        //génére un message par groupe et l'envoi via le client smtp
        for(Group g : groups){
            Message message = new PrankGenerator(g).getMessage();
            smtp.sendMail(message);

        }

    }

    /**
     * Cette fonction crée un groupe composé du nombre voulu de personnes,
     * puis entre le groupe dans le tableau groups[]
     * @param people: les personnes à répartir
     * @param groups: le tableau de groupes à remplir
     * @param peoplePerGroups: le nombre de personnes par groupe
     *                          ATTENTION: le dernier groupe peut être composé
     *                                      de plus de personnes si nécessaire
     */
    private static void putPeopleInGroups(Person[] people, Group[] groups, int peoplePerGroups){
        int cnt = 0, nbInGroup, groupNumber = 0;
        Person[] sameGroup;
        while(cnt < people.length) {
            if((nbInGroup = people.length-cnt-peoplePerGroups) < 3){
                sameGroup = new Person[peoplePerGroups + nbInGroup];
            }else if( nbInGroup <= peoplePerGroups){
                sameGroup = new Person[nbInGroup];
            }else{
                sameGroup = new Person[peoplePerGroups];
            }

            for (int i = 0; i < sameGroup.length; ++i) {
                sameGroup[i] = people[cnt++];
            }

            groups[groupNumber++] = new Group(sameGroup);
        }
    }

    private static int retrieveNbGroup(){
        BufferedReader reader;
        int cnt = 1;
        int nbGroups = 0;
        try{
            reader =new BufferedReader(
                    new FileReader("./config/properties.properties", StandardCharsets.UTF_8));
            //Récupère le nombre de groupe
            while(reader.ready()){
                if(cnt == 3){
                    //TODO: faire plus évolutif
                    nbGroups = Integer.parseInt(reader.readLine().substring(15));
                    break;
                }else{
                    reader.readLine();
                }
            }
        }catch(Exception e){
            //TODO: Catch exception
        }
        return nbGroups;
    }

    private static Person[] retrievePeople(){
        BufferedReader reader;
        Person[] victims;
        int cnt = 0;
        try{
            reader =new BufferedReader(
                    new FileReader("./config/victims.UTF8", StandardCharsets.UTF_8));
            //compte le nombre de personnes
            while(reader.ready()){
                ++cnt;
            }
            reader.reset();
            victims = new Person[cnt];
            cnt = 0;
            //Entre chaque personne dans le tableau de personnes
            while(reader.ready()){
                victims[cnt] = new Person(reader.readLine());
            }

            reader.close();
            return victims;
        }catch(Exception e){
            //TODO: catch exceptions
            return null;    //TODO: A VERIFIER
        }

    }
}
