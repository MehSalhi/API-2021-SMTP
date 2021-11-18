package model.mail;

public class Group {
    private Person[] victims;

    public Group(Person ...victims){
        this.victims = victims;
    }

    public Person[] getVictims() {
        return victims;
    }

}
