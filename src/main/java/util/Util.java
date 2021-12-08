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
        BufferedReader reader = null;
        int cnt = 1;
        int nbGroups = 0;
        try{
            reader =new BufferedReader(
                    new FileReader("./src/main/java/config/properties" +
                            ".properties", StandardCharsets.UTF_8));
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
            throw new RuntimeException("La récupération du nombre de groupe a échoué");
        }finally {
            try{
                reader.close();
            }catch(Exception e){
                System.out.println(e);
            }
        }
        return nbGroups;
    }

    public static String retrieveSmtpHostname(){
        BufferedReader reader = null;
        int cnt = 1;
        StringBuilder hostname = new StringBuilder();
        try{
            reader =new BufferedReader(
                    new FileReader("./src/main/java/config/properties" +
                            ".properties", StandardCharsets.UTF_8));
            //Récupère le nombre de groupe
            while(reader.ready()){
                if(cnt == 1){
                    hostname.append(reader.readLine().substring(18));
                    break;
                }else{
                    reader.readLine();
                }
                ++cnt;
            }
        }catch(Exception e){
            throw new RuntimeException("La récupération du hostname a échoué");
        }finally {
            try{
                reader.close();
            }catch(Exception e){
                System.out.println(e);
            }
        }
        return hostname.toString();
    }

    public static int retrieveSmtpPort(){
        BufferedReader reader = null;
        int cnt = 1;
        int port = 0;
        try{
            reader =new BufferedReader(
                    new FileReader("./src/main/java/config/properties" +
                            ".properties", StandardCharsets.UTF_8));
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
            throw new RuntimeException("La récupération du port à échoué");
        }finally {
            try{
                reader.close();
            }catch(Exception e){
                System.out.println(e);
            }
        }
        return port;
    }

    public static Person[] retrievePeople(){
        BufferedReader reader = null;
        Person[] victims;
        int cnt = 0;
        try{
            reader =new BufferedReader(
                    new FileReader("./src/main/java/config/victims.UTF8",
                            StandardCharsets.UTF_8));
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

            return victims;
        }catch(Exception e){

            throw new RuntimeException("La récupération des victimes a échoué");
        }finally {
            try{
                reader.close();
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
}
