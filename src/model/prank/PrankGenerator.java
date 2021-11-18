package model.prank;
import model.mail.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Random;

public class PrankGenerator {
    private Person[] receiver;
    private Person sender;
    private Prank prank;

    public PrankGenerator(Group victims){
        int idSender = selectSender(victims.getVictims().length);
        for(int i = 0, j = 0; i < victims.getVictims().length; ++i){
            if(i== idSender){
                sender = victims.getVictims()[i];
            } else{
                receiver[j] = victims.getVictims()[i];
                ++j;
            }
        }
    }

    public Message generateMessage(){
        Message message = new Message();
        return message;
    }

    private int selectSender(int size){
        Random rand = new Random();
        return rand.nextInt(size);
    }

    private void generatePrank(){
        File file = new File("message.UTF8");
       
    }
}
