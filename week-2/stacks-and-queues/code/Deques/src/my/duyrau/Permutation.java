package my.duyrau;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by duyrau on 3/24/17.
 */
public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        // reservoir sampling
        int i = 1;
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            if (i <= k) {
                // number of chosen items is no greater than k (choosing sample of k items from n items)
                // so we just add items to the sample.
                randomizedQueue.enqueue(input);
            } else if (StdRandom.uniform() < (double) k / i) {
                randomizedQueue.dequeue();
                randomizedQueue.enqueue(input);
            }
            i++;
        }

        for (int j = 0; j < k; j++) {
            System.out.println(randomizedQueue.dequeue());
        }
    }
}
