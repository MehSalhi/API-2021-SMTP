package model.prank;
import model.mail.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class PrankGenerator {
    private Group group;
    private Prank prank;
    final String SEPARATOR = "<==========>";
    private Message message;
    private boolean test;

    /**
     * This constructor is used only for test purposes and allows to
     * launch each functions separately
     * @param victims
     * @param test
     */
    public PrankGenerator(Group victims, boolean test){
        this.group = victims;
        this.test = test;
    }

    public PrankGenerator(Group victims){
        this.group = victims;
        message = generateMessage();
        this.test = false;
    }


    public Prank getPrank() {
        return prank;
    }

    public Message getMessage() {
        return message;
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

    public void generatePrank(){
        File file;
        FileReader fr;
        BufferedReader reader;
        int nbPrank = 0;
        try {
            if(test){
                //Si on effectue un test
                file = new File("message.UTF8");
            }else{
                file = new File("../../config/message.UTF8");
            }

            fr = new FileReader(file, StandardCharsets.UTF_8);
            System.out.println("tatata");
            reader =new BufferedReader(fr);

            while(reader.ready()){
                if(reader.readLine() == SEPARATOR){
                    ++nbPrank;
                }
            }
            reader.reset();

            if(test){
                //séléctionne un prank fix si il s'agit d'un test
                prank = new Prank(selectOnePrank(reader, 2));
            }else{
                prank = new Prank(selectOnePrank(reader, selectRandom(nbPrank)));
            }



            reader.close();
        }catch(Exception e){
            //TODO: catch exceptions
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
