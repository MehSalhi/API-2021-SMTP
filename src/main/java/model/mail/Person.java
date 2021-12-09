/**
 * API-SMTP
 * 09.12.2021
 * @author Guilain Mbayo
 * @author Mehdi Salhi
 */
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

    /**
     * Gets the address of a Person
     *
     * @return The address
     */
    public String getAddress() {
        return address;
    }


    /**
     * Validates an email format with a regex expression
     * This is a simple regex email address format validator taken from the
     * web. This is enough for our purpose but for production code this
     * should be tested further. We ran a few test with a few solutions from
     * the web and some didn't work as expected. This one seems to work.
     * Source: https://www.geeksforgeeks.org/check-email-address-valid-not-java/
     *
     * @param email The email address to validate
     * @return true if the email is correctly formatted
     */
    public static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        return email.matches(regex);
    }
}
