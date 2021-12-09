/**
 * API-SMTP
 * 09.12.2021
 *
 * @author Guilain Mbayo
 * @author Mehdi Salhi
 */
package model.mail;

import java.util.Random;

public class Group {
    private final Person[] receivers;
    private Person sender;

    public Group(Person... victims) {
        if (victims.length < 3) {
            throw new RuntimeException("A group should at least contain 3 people.");
        }
        receivers = new Person[victims.length - 1];
        //choisi un sender aléatoire parmi les victims
        int idSender = selectSender(victims.length);
        //boucle qui répartit les victims en sender ou receiver[]
        for (int i = 0, j = 0; i < victims.length; ++i) {
            if (i == idSender) {
                sender = victims[i];
            } else {
                receivers[j] = victims[i];
                ++j;
            }
        }

    }

    /**
     * Select a sender among all the victims
     * @param size number of victims
     * @return The index of the sender
     */
    private int selectSender(int size) {
        Random rand = new Random();
        return rand.nextInt(size);
    }

    /**
     * Gets the receivers
     * @return The receivers
     */
    public Person[] getReceivers() {
        return receivers;
    }

    /**
     * Gets the sender
     * @return The sender
     */
    public Person getSender() {
        return sender;
    }
}