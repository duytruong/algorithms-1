package my.duyrau;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by duyrau on 2/27/17.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size = 0;

    private Item[] array;

    public RandomizedQueue() {
        array = (Item[]) new Object[1];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = array[i];
        }
        array = copy;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (size == array.length) {
            resize(2 * array.length);
        }
        array[size++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot dequeue an item from empty queue");
        }
        int removeIdx = StdRandom.uniform(size);
        Item item = array[removeIdx];
        array[removeIdx] = array[size - 1];
        array[size - 1] = null;
        size--;
        if (size > 0 && size == array.length / 4) {
            resize(array.length / 2);
        }
        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot sample an item from empty queue");
        }
        return array[StdRandom.uniform(size)];
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    private class RandomArrayIterator implements Iterator<Item> {

        private int iterated = 0;

        private Item[] temp;

        public RandomArrayIterator() {
            temp = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                temp[i] = array[i];
            }
        }

        @Override
        public boolean hasNext() {
            return iterated < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more item");
            }
            int k = (int) (StdRandom.uniform() * (size - iterated));
            Item item = temp[k];
            temp[k] = temp[size - (++iterated)];
            temp[size - iterated] = null;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }
}
