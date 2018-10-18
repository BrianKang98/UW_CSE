/*
 * Brian Kang
 * 4/18/2018
 * CSE143
 * TA: Preston Crowe
 * Assignment #3: AssassinManager.java
 *
 * This class allows a client to manage a game of assassin, where
 * every player of the game has a target within a circular kill ring.
 * One case-ignoring name list of surviving players and another of
 * those that are killed will keep tracked, and the game will continue
 * until there is a single survivor.
 */

import java.util.List;

public class AssassinManager {
    private AssassinNode ringFront;    // front of the kill ring
    private AssassinNode graveFront;    // front of the graveyard

    // Constructs an assassin manager and makes a kill ring
    // parameters needed:
    //  List<String> names = the list of the player names
    // pre: the list should include names,
    //      if list is empty throw IllegalArgumentException
    // post: assassin manager is made and kill ring consists of
    //       the players' names
    public AssassinManager(List<String> names) {
        if (names.isEmpty()) {
            throw new IllegalArgumentException();
        }
        for (String name : names) {
            AssassinNode killRing = new AssassinNode(name);
            if (ringFront == null) {
                ringFront = killRing;
            } else {
                AssassinNode temp = ringFront;
                while (temp.next != null) {
                    temp = temp.next;
                }
                temp.next = killRing;
            }
        }
        graveFront = null;
    }

    // Prints the names of alive people and who they are stalking
    // post: names of people in kill ring is printed one per line,
    //       if there is only one person left, the person is stalking
    //       themselves
    public void printKillRing() {
        AssassinNode current = ringFront;
        while (current.next != null) {
            System.out.printf("\t%s is stalking %s\n", current.name, current.next.name);
            current = current.next;
        }
        System.out.printf("\t%s is stalking %s\n", current.name, ringFront.name);
    }

    // Prints the names of killed people and who they were killed by
    // post: names of people in graveyard is printed one per line,
    //       the most recently killed person being printed first,
    //       if graveyard is empty don't print anything
    public void printGraveyard() {
        if (graveFront == null) {
            return;
        }
        AssassinNode current = graveFront;
        while (current.next != null) {
            System.out.printf("\t%s was killed by %s\n", current.name, current.killer);
            current = current.next;
        }
        System.out.printf("\t%s was killed by %s\n", current.name, current.killer);
    }

    // parameters needed:
    //  String name = the name of person to be search
    // post: regardless of case, return true if given name exists
    //       in kill ring, if not return false
    public boolean killRingContains(String name) {
        AssassinNode current = ringFront;
        while (current != null) {
            if (name.equalsIgnoreCase(current.name)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // parameters needed:
    //  String name = the name of person to be search
    // post: regardless of case, return true if given name exists
    //       in graveyard, if not return false
    public boolean graveyardContains(String name) {
        AssassinNode current = graveFront;
        while (current != null) {
            if (name.equalsIgnoreCase(current.name)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // post: return true if game is over (there is one person
    //       left in the kill ring), if not return false
    public boolean gameOver() {
        return ringFront.next == null;
    }

    // post: return the String name of the winner (the last
    //       person left in the kill ring), if game is not over
    //       return null
    public String winner() {
        if (gameOver()) {
            return ringFront.name;
        }
        return null;
    }

    // Kills a person and sends that person to graveyard with
    // the name of the killer recorded
    // parameters needed:
    //  String name = the name of person to be search
    // pre: regardless of case, if given name is not in kill ring
    //      throw IllegalArgumentException, if the game is over
    //      (only one player left in ring) throw IllegalStateException
    //      if both are true, throw one of the Exceptions (doesn't matter)
    // post: the killed person will be sent to graveyard from kill list
    //       with the name of the killer recorded, the kill list now
    //       will not include the killed person and the killer will be
    //       assigned a new target (the target of the killed person)
    //       the front of the graveyard will be the most recently killed person,
    //       (the very first killer person being the last of the list)
    public void kill(String name) {
        if (!killRingContains(name)) {
            throw new IllegalArgumentException();
        }
        if (gameOver()) {
            throw new IllegalStateException();
        }
        AssassinNode current = ringFront;
        AssassinNode temp;
        if (current.name.equalsIgnoreCase(name)) {
            temp = current;
            current = current.next;
            ringFront = current;
            temp.next = null;
            while (current.next != null) {
                current = current.next;
            }
            temp.killer = current.name;
        } else {
            while (!current.next.name.equalsIgnoreCase(name)) {
                current = current.next;
            }
            temp = current.next;
            temp.killer = current.name;
            current.next = current.next.next;
            temp.next = null;
        }
        graveFront = new AssassinNode(temp.name, graveFront);
        graveFront.killer = temp.killer;
    }
}
