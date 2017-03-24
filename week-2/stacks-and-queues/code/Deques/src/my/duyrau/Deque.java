package my.duyrau;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by duytruong on 2/17/17.
 */
public class Deque<Item> implements Iterable<Item> {

    private Node head = null;
    private Node tail = null;
    private int size; // the number of items on the deque

    private class Node {
        private Item data;
        private Node next;
        private Node prev;

        /**
         * Create a new {@code Node}.
         * @param data data contained in this Node
         * @param next next {@code Node} of this Node
         * @param prev previous {@code Node} of this Node
         */
        public Node(Item data, Node next, Node prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    private class DoubleLinkedListIterator implements Iterator<Item> {

        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (hasNext()) {
                Item item = current.data;
                current = current.next;
                return item;
            } else {
                throw new NoSuchElementException("No more item in the list");
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    public Deque() { }

    private boolean isInputNull(Item item) {
        return item == null;
    }

    public boolean isEmpty() {
        return head == null && tail == null;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (isInputNull(item)) {
            throw new NullPointerException();
        }
        Node oldHead = head;
        head = new Node(item, oldHead, null);
        if (oldHead != null) {
            oldHead.prev = head;
        }
        // the list is empty when addFirst is called.
        if (tail == null) {
            tail = head;
        }
        size++;
    }

    public void addLast(Item item) {
        if (isInputNull(item)) {
            throw new NullPointerException();
        }
        Node oldTail = tail;
        tail = new Node(item, null, oldTail);
        if (oldTail != null) {
            oldTail.next = tail;
        }
        if (head == null) {
            head = tail;
        }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove an item from empty deque");
        } else {
            Item item = head.data;
            head = head.next;
            if (head == null) {
                tail = null;
            } else {
                head.prev = null;
            }
            size--;
            return item;
        }
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove an item from empty deque");
        } else {
            Item item = tail.data;
            tail = tail.prev;
            if (tail == null) {
                head = null;
            } else {
                tail.next = null;
            }
            size--;
            return item;
        }
    }

    @Override
    public Iterator<Item> iterator() {
        return new DoubleLinkedListIterator();
    }
}