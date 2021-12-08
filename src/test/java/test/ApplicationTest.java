package test;
import model.mail.*;
import model.prank.*;
import org.junit.Test;
import smtp.*;
import util.Util;

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
        assertEquals(expected, m.getReceiversToString());

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
        assertEquals(expectedReceivers, m.getReceiversToString());

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
        Person p1 = new Person("tata@tata.ch");
        Person p2 = new Person("titi@tata.ch");

        Group g = new Group(p1, p2);
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

    /*************************************************************
     *  PrankGenerator
     *************************************************************/

    @Test
    public void prankSubjectShouldBeCorrect(){
        Prank prank = new Prank("mon Subject de Test\nmon body de Test\nrépartit sur plusieurs\nlignes");
        String expectedSubject = "mon Subject de Test";
        assertEquals(expectedSubject, prank.getSubject());
    }

    @Test
    public void prankBodyShouldBeCorrect(){
        Prank prank = new Prank("mon Subject de Test\nmon body de Test\nrépartit sur plusieurs\nlignes");
        String expectedBody = "mon body de Test\nrépartit sur plusieurs\nlignes";
        assertEquals(expectedBody, prank.getBody());
    }

    @Test
    public void prankShouldGenerateCorrectly(){
        Person p1 = new Person("tata@tata.ch");
        Person p2 = new Person("titi@tata.ch");
        Person p3 = new Person("toto@toto.ch");

        Group g = new Group(p1, p2, p3);
        PrankGenerator generator = new PrankGenerator(g, true);
        generator.generatePrank();

        String expectedSubject = "mon Subject de Test";
        String expectedBody = "mon body de Test\nrépartit sur plusieurs\nlignes\n";
        Prank prank = generator.getPrank();
        String resultSubject = prank.getSubject();

        assertEquals(expectedSubject, resultSubject);
        assertEquals(expectedBody, generator.getPrank().getBody());
    }

    @Test
    public void messageShouldBeCorrect() {
        Person p1 = new Person("toto@toto.ch");
        Person p2 = new Person("tata@tata.ch");
        Person p3 = new Person("titi@tata.ch");

        Group g = new Group(p1, p2, p3);
        PrankGenerator generator = new PrankGenerator(g, true);

        Message actual = generator.generateMessage();
        Message expected = new Message(g.getSender(), "mon Subject de Test", "mon body de Test\n" +
                "répartit sur plusieurs\n" +
                "lignes\n", g.getReceivers());

        assertEquals(expected.toString(), actual.toString());

    }

    /*************************************************************
     *  Util
     *************************************************************/
    @Test
    public void nbGroupShouldBeCorrect(){
        assertEquals(2, Util.retrieveNbGroup());
    }

    @Test
    public void peopleShouldBeCorrect(){
        Person[] actual = Util.retrievePeople();
        Person[] p = new Person[6];
        p[0] = new Person("toto@toto123.ch");
        p[1] = new Person("tata@tata123.ch");
        p[2] = new Person("titi@titi123.ch");
        p[3] = new Person("tutu@tutu123.ch");
        p[4] = new Person("tete@tete123.ch");
        p[5] = new Person("tvtv@tvtv123.ch");

        for(int i = 0; i < 6; ++i){
            assertEquals(p[i].getAdress(), actual[i].getAdress());
        }
    }


    /*************************************************************
     *  SMTP Client
     *************************************************************/
    /**
     * Tests that correctly formatted mail can be sent
     */
    @Test
    public void smtpClientShouldWork() {
        Person s = new Person("toto@toto.ch");
        Person p1 = new Person("tata@tata.ch");
        Person p2 = new Person("titi@tata.ch");
        Person p3 = new Person("tutu@tata.ch");

        Person[] pT = new Person[]{p1, p2, p3};

        Message m = new Message(s, "test subject", "body content", pT);

        SmtpClient client = new SmtpClient("localhost", 25000);
        assertTrue(client.sendMail(m));
    }

}
