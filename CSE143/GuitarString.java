/*
 * Brian Kang
 * 4/7/2018
 * CSE143
 * TA: Preston Crowe
 * Assignment #2: GuitarString.java
 *
 * This program is suppose to model a vibrating
 * guitar string with a given frequency, allows to
 * do stuff to the string by carrying out tasks on
 * the ring buffer
 */

import java.util.LinkedList;
import java.util.Queue;

public class GuitarString {
    private Queue<Double> buffer;   // the ring buffer structure
    public static final double DECAY_FACTOR = 0.996;

    // Constructs a guitar string with a given frequency
    // and makes a ring buffer with a capacity
    // parameters needed:
    //  double frequency = the frequency of the guitar string
    // pre: if frequency is less than or equal to 0 or if
    //      the capacity of the buffer is less than 2,
    //      throw IllegalArgumentException
    // post: ring buffer represent a string at rest
    public GuitarString(double frequency) {
        if (frequency <= 0) {
            throw new IllegalArgumentException();
        }
        buffer = new LinkedList<Double>();
        for (int i = 0; i < (int) Math.round(StdAudio.SAMPLE_RATE / frequency); i++) {
            buffer.add(0.0);
        }
        checkSize(buffer.size());
    }

    // Constructs a guitar string and a ring buffer with the
    // contents of the array
    // parameters needed:
    //  double[] init = array of values
    // pre: if the length of the array is less than 2,
    //      throw IllegalArgumentException
    // post: ring buffer has the values of the init array
    public GuitarString(double[] init) {
        checkSize(init.length);
        buffer = new LinkedList<Double>();
        for (int i = 0; i < init.length; i++) {
            buffer.add(init[i]);
        }
    }

    // Checks if the size is less than two or not
    // parameters needed:
    //  int size = the length or size of something being checked
    // post: if size is less than 2 throw IllegalArgumentException
    private void checkSize(int size) {
        if (size < 2) {
            throw new IllegalArgumentException();
        }
    }

    // Plucks the guitar string to vibrate and have a displacement
    // post: elements of the buffer have a displacement between
    //       -0.5 inclusive and 0.5 exclusive
    public void pluck() {
        for (int i = 0; i < buffer.size(); i++) {
            buffer.remove();
            buffer.add(Math.random() - 0.5);
        }
    }

    // Applies the Karplus-Strong update once
    // post: the first sample from the buffer is deleted and
    //       the average of the first two samples times the energy
    //       decay factor is added at the end of buffer
    public void tic() {
        double first = buffer.remove();
        double second = buffer.peek();
        buffer.add(DECAY_FACTOR * (first + second) / 2);
    }

    // post: returns double value at the front of the buffer
    //       (the current sample)
    public double sample() {
        return buffer.peek();
    }
}
