package util;

import model.mail.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;

public class Util {
    /**
     * Cette fonction crée un groupe composé du nombre voulu de personnes,
     * puis entre le groupe dans le tableau groups[]
     * @param people: les personnes à répartir
     * @param groups: le tableau de groupes à remplir
     * @param peoplePerGroups: le nombre de personnes par groupe
     *                          ATTENTION: le dernier groupe peut être composé
     *                                      de plus de personnes si nécessaire
     */
    public static void putPeopleInGroups(Person[] people, Group[] groups, int peoplePerGroups){
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

    public static int retrieveNbGroup(){
        BufferedReader reader;
        int cnt = 1;
        int nbGroups = 0;
        try{
            reader =new BufferedReader(
                    new FileReader("./src/config/properties.properties", StandardCharsets.UTF_8));
            //Récupère le nombre de groupe
            while(reader.ready()){
                if(cnt == 3){
                    nbGroups = Integer.parseInt(reader.readLine().substring(15));
                    break;
                }else{
                    reader.readLine();
                }
                ++cnt;
            }
        }catch(Exception e){
            //TODO: Catch exception
        }
        return nbGroups;
    }

    public static int retrieveSmtpPort(){
        BufferedReader reader;
        int cnt = 1;
        int port = 0;
        try{
            reader =new BufferedReader(
                    new FileReader("./src/config/properties.properties", StandardCharsets.UTF_8));
            //Récupère le nombre de groupe
            while(reader.ready()){
                if(cnt == 2){
                    port = Integer.parseInt(reader.readLine().substring(15));
                    break;
                }else{
                    reader.readLine();
                }
                ++cnt;
            }
        }catch(Exception e){
            //TODO: Catch exception
        }
        return port;
    }

    public static Person[] retrievePeople(){
        BufferedReader reader;
        Person[] victims;
        int cnt = 0;
        try{
            reader =new BufferedReader(
                    new FileReader("./src/config/victims.UTF8", StandardCharsets.UTF_8));
            reader.mark(1024);
            //compte le nombre de personnes
            while(reader.ready()){
                reader.readLine();
                ++cnt;
            }
            reader.reset();
            victims = new Person[cnt];
            cnt = 0;
            //Entre chaque personne dans le tableau de personnes
            while(reader.ready()){
                victims[cnt] = new Person(reader.readLine());
                ++cnt;
            }

            reader.close();
            return victims;
        }catch(Exception e){
            //TODO: catch exceptions
            return null;    //TODO: A VERIFIER
        }

    }
}
