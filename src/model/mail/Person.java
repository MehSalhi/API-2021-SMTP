package model.mail;

public class Person {
    private String adress = "";

    public Person(String adress){
        //TODO: Véreifier le format de l'adresse
        this.adress = adress;
    }

    public String getAdress() {
        return adress;
    }
}
