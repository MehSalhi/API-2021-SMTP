package model.mail;

import java.util.Random;

public class Group {
    private Person[] receivers;
    private Person sender;

    public Group(Person ...victims){
       if (victims.length < 3) {
           throw new RuntimeException("A group should at least contain 3 people.");
       }
        //choisi un sender aléatoire parmi les victims
        int idSender = selectSender(victims.length);
        //boucle qui répartit les victims en sender ou receiver[]
        for(int i = 0, j = 0; i < victims.length; ++i){
            if(i == idSender){
                sender = victims[i];
            } else{
                receivers[j] = victims[i];
                ++j;
            }
        }

    }

    private int selectSender(int size){
        Random rand = new Random();
        return rand.nextInt(size);
    }

    public Person[] getReceivers() {
        return receivers;
    }

    public Person getSender() {
        return sender;
    }
}