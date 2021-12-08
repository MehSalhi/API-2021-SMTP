package model.prank;
import model.mail.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public Message generateMessage(){
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
        BufferedReader reader = null;
        int nbPrank = 0;
        try {
            if(test){
                //permet de visualiser le chemin absolu du répertoir courrant pour le debug
                Path currentRelativePath = Paths.get("");
                String s = currentRelativePath.toAbsolutePath().toString();
                System.out.println("Current absolute path is: " + s);
                //Si on effectue un test
                file = new File("./src/test/message.UTF8");
            }else{
                file = new File("./src/config/message.UTF8");
            }

            fr = new FileReader(file, StandardCharsets.UTF_8);
            reader = new BufferedReader(fr);
            reader.mark(1024);

            while(reader.ready()){
                if(reader.readLine().equals(SEPARATOR)){
                    ++nbPrank;
                }
            }

            reader.reset();

            if(test){
                //séléctionne un prank fix si il s'agit d'un test

                prank = new Prank(selectOnePrank(reader, 1));
            }else{
                prank = new Prank(selectOnePrank(reader, selectRandom(nbPrank)));
            }

        }catch(Exception e){
            throw new RuntimeException("La génération du prank a échoué");
        }finally {
            try {
                reader.close();
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }

    private String selectOnePrank(BufferedReader reader, int numPrank){
        StringBuilder sb = new StringBuilder();
        String tmp;
        int nbPrank = 0;
        try{

            while(reader.ready()){
                tmp = reader.readLine();
                if(SEPARATOR.equals(tmp)){
                    ++nbPrank;
                }else if(nbPrank == numPrank){
                    sb.append(tmp);
                    sb.append("\n");
                }else if(nbPrank > numPrank){
                    return sb.toString();
                }
            }
        }catch(Exception e){
            throw new RuntimeException("La séléction d'un prank a échoué");
        }

        return sb.toString();
    }
}
