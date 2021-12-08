package model.mail;

public class Person {
    private String adress;

    public Person(String adress){
        if(adress.isEmpty()) {
            throw new RuntimeException("The adress cannot be empty.");
        }

        if(isValidEmail(adress)) {
            this.adress = adress;
        }
        else {
            throw new RuntimeException("Email adress not valid.");
        }
    }

    public String getAdress() {
        return adress;
    }

    // This is a simple regex email adress format validator taken from the
    // web. This is enough for our purpose but for production code this
    // should be tested further. We ran a few test with a few solutions from
    // the web and some didn't work as expected. This one seems to work.
    // Source: https://www.geeksforgeeks.org/check-email-address-valid-not-java/
    public static boolean isValidEmail(String email) {
        //String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        return email.matches(regex);
    }
}
