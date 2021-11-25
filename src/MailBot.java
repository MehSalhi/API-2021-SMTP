import model.mail.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;

public class MailBot {

    public static void main(String[] args){
        Person[] victims = retrievePeople();
        Group[] groups;
        int nbGroups = retrieveNbGroup();
        int peoplePerGroups;
        if(victims.length < nbGroups){
            throw new RuntimeException("pas assez de victims pour autant de groupes");
        }
        groups = new Group[nbGroups];
        peoplePerGroups = victims.length/nbGroups;

    }

    //TODO: pas sur du tout de cette fonction... paraît très sale
    //TODO: ne foncionne pas
    private static void putPeopleInGroups(Person[] people, Group[] groups, int peoplePerGroups){
        int cnt = 0;
        Person[] sameGroup = new Person[1]; //TODO: ça pique les yeux....
        for(Person p : people){
            if(cnt == 0 || cnt%peoplePerGroups == 0){
                if(cnt == people.length-1){
                    sameGroup[peoplePerGroups] = p;

                }else {
                    sameGroup = new Person[peoplePerGroups];
                    sameGroup[cnt%peoplePerGroups] = p;
                }
            }else{
                sameGroup[cnt%peoplePerGroups] = p;
            }
        }
    }

    private static int retrieveNbGroup(){
        BufferedReader reader;
        int cnt = 1;
        int nbGroups = 0;
        try{
            reader =new BufferedReader(
                    new FileReader("./config/properties.properties", StandardCharsets.UTF_8));
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
            while(reader.ready()){
                ++cnt;
            }
            reader.reset();
            victims = new Person[cnt];
            cnt = 0;
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
