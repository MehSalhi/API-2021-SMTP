package model.prank;
import model.mail.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class PrankGenerator {
    private Person[] receiver;
    private Person sender;
    private Prank prank;

    public PrankGenerator(Group victims){
        //choisi un sender aléatoire parmi les victims
        int idSender = selectSender(victims.getVictims().length);
        //boucle qui répartit les victims en sender ou receiver[]
        for(int i = 0, j = 0; i < victims.getVictims().length; ++i){
            if(i == idSender){
                sender = victims.getVictims()[i];
            } else{
                receiver[j] = victims.getVictims()[i];
                ++j;
            }
        }
    }

    public Message generateMessage(){
        Message message = new Message(sender, prank.getSubject(), prank.getBody(), receiver);
        return message;
    }

    private int selectSender(int size){
        Random rand = new Random();
        return rand.nextInt(size);
    }

    private void generatePrank(){
        BufferedReader reader;
        StringBuilder sb = new StringBuilder();
        try {
            reader =new BufferedReader(new FileReader("message.UTF8", StandardCharsets.UTF_8));
            //TODO: changer la condition du while pour ne lire qu'un prank
            while(reader.ready()){
                sb.append(reader.readLine());
            }
            //TODO: séparer entre le subject et le body
            prank = new Prank(sb.toString(), sb.toString());

        }catch(Exception e){
            //TODO: catche exceptions
        }
    }
}
