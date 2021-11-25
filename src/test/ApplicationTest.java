package test;
import model.mail.*;
import org.junit.Test;

import static org.junit.Assert.*;


public class ApplicationTest {

    /*************************************************************
     *  E-mail
     *************************************************************/

    /**
     * Checks that a message reseivers field is correctly formatted from an
     * array of string to a String with the format "email1, email2, email3"
     */

    @Test
    public void messageShouldHaveTheCorrectReceiversFormat() {
        Person s = new Person("toto@toto.ch");
        Person p1 = new Person("tata@tata.ch");
        Person p2 = new Person("titi@tata.ch");
        Person p3 = new Person("tutu@tata.ch");
        Person p4 = new Person("tete@tata.ch");

        Message m = new Message(s, "test subject", "test content", p1, p2, p3
                , p4);
        String expected = "tata@tata.ch, titi@tata.ch, tutu@tata.ch, " +
                "tete@tata.ch";
        assertEquals(expected, m.getReceivers());

    }

    /**
     * Checks that an email has the correct structure and fields
     */
    @Test
    public void emailShouldHaveTheCorrectStructure(){
        Person s = new Person("toto@toto.ch");
        Person p1 = new Person("tata@tata.ch");
        Person p2 = new Person("titi@tata.ch");

        Message m = new Message(s, "test subject", "body content", p1, p2);
        String expectedBody = "body content";
        String expectedSubject = "test subject";
        String expectedSender = s.getAdress();
        String expectedReceivers = "tata@tata.ch, titi@tata.ch";
        assertEquals(expectedBody, m.getBody());
        assertEquals(expectedSubject, m.getSubject());
        assertEquals(expectedSender, m.getSender());
        assertEquals(expectedReceivers, m.getReceivers());

    }

    @Test (expected = RuntimeException.class)
    public void emailWithoutASenderShouldThrowRuntimeException() {
        Person s = new Person("");
        Person p1 = new Person("tata@tata.ch");
        Person p2 = new Person("titi@tata.ch");

        Message m = new Message(s, "test subject", "body content", p1, p2);
    }

    @Test (expected = RuntimeException.class)
    public void emailWithoutAReceiverShouldThrowRuntimeException() {
        Person s = new Person("toto@toto.ch");
        Message m = new Message(s, "test subject", "test content");
        String expected = "";
        assertEquals(expected, m.getReceivers());
    }

    @Test (expected = RuntimeException.class)
    public void wrongEmailFormatShouldThrowRuntimeException() {
        Person s = new Person("totototo.ch");
    }

    @Test (expected = RuntimeException.class)
    public void wrongEmailFormatShouldThrowRuntimeException2() {
        Person s = new Person("toto@@toto.ch");
    }

    @Test (expected = RuntimeException.class)
    public void wrongEmailFormatShouldThrowRuntimeException3() {
        Person s = new Person("toto@totoch");
    }

    @Test
    public void variousEmailFormatTest() {
        assertTrue(Person.isValidEmail("toto@toto.ch"));
        assertTrue(Person.isValidEmail("toto.toto@toto.ch"));
        assertFalse(Person.isValidEmail(""));
        assertFalse(Person.isValidEmail(".toto@toto.ch"));
        assertFalse(Person.isValidEmail("toto@@toto.ch"));
        assertFalse(Person.isValidEmail("toto"));
    }

    @Test
    public void messageToStringShouldBeCorrect() {
        Person s = new Person("toto@toto.ch");
        Person p1 = new Person("tata@tata.ch");
        Person p2 = new Person("titi@tata.ch");

        Message m = new Message(s, "test subject", "body content", p1, p2);

        String expected =
                "Sender: " + "toto@toto.ch\n" + "Receivers: " + "tata" +
                        "@tata.ch, titi@tata.ch\n" + "Subject: " + "test " +
                        "subject\n" + "Body: \n" + "body content\n";
        assertEquals(expected, m.toString());

    }

    /*************************************************************
     *  Groups
     *************************************************************/

    @Test (expected = RuntimeException.class)
    public void groupSmallerThan3ShouldThrowRuntimeException() {

    }

    @Test
    public void groupShouldHaveOneSenderAndNReceivers() {
        Person p1 = new Person("tata@tata.ch");
        Person p2 = new Person("titi@tata.ch");
        Person p3 = new Person("toto@toto.ch");

        Group g = new Group(p1, p2, p3);
        assertNotNull(g.getSender());
        assertTrue(g.getReceivers().length == 2);
    }
}
