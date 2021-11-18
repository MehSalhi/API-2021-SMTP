package test;
import model.mail.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {

    @Test
    public void messageShouldHaveTheCorrectReceiversFormat() {
        Person s = new Person("toto@toto.ch");
        Person p1 = new Person("tata@tata.ch");
        Person p2 = new Person("titi@tata.ch");

        Message m = new Message(s, "test subject", "test content", p1, p2);
        String expected = "tata@tata.ch, titi@tata.ch";
        assertEquals(expected, m.getReceivers());

    }

}
