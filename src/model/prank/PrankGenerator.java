package model.prank;
import model.mail.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class PrankGenerator {
    private Group group;
    private Prank prank;
    final String SEPARATOR = "<==========>";

    public PrankGenerator(Group victims){
        this.group = victims;
        generateMessage();
    }

    private Message generateMessage(){
        generatePrank();
        Message message = new Message(group.getSender(), prank.getSubject(), prank.getBody(), group.getReceivers());
        return message;
    }

    private int selectRandom(int size){
        Random rand = new Random();
        return rand.nextInt(size);
    }

    private void generatePrank(){
        BufferedReader reader;
        int nbPrank = 0;
        try {
            reader =new BufferedReader(new FileReader("message.UTF8", StandardCharsets.UTF_8));

            while(reader.ready()){
                if(reader.readLine() == SEPARATOR){
                    ++nbPrank;
                }
            }
            reader.reset();

            prank = new Prank(selectOnePrank(reader, selectRandom(nbPrank)));

        }catch(Exception e){
            //TODO: catche exceptions
        }
    }

    private String selectOnePrank(BufferedReader reader, int numPrank){
        StringBuilder sb = new StringBuilder();
        String tmp;
        int nbPrank = 0;
        try{
            while(reader.ready()){
                tmp = reader.readLine();
                if(tmp == SEPARATOR){
                    ++nbPrank;
                }
                if(nbPrank == numPrank){
                    sb.append(tmp);
                }
                if(nbPrank > numPrank){
                    break;
                }
            }
        }catch(Exception e){
            //TODO: catch exception
        }

        return sb.toString();
    }
}
