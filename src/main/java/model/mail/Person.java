package model.mail;

public class Person {
    private final String address;

    public Person(String address) {
        if (address.isEmpty()) {
            throw new RuntimeException("The address cannot be empty.");
        }

        if (isValidEmail(address)) {
            this.address = address;
        } else {
            throw new RuntimeException("Email address not valid.");
        }
    }

    public String getAddress() {
        return address;
    }

    // This is a simple regex email address format validator taken from the
    // web. This is enough for our purpose but for production code this
    // should be tested further. We ran a few test with a few solutions from
    // the web and some didn't work as expected. This one seems to work.
    // Source: https://www.geeksforgeeks.org/check-email-address-valid-not-java/
    public static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        return email.matches(regex);
    }
}
