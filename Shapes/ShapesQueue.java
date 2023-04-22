package Shapes;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ShapesQueue implements Iterable<Shape> {
    private Node head;
    private Node tail;
    private int size;

    public ShapesQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void add(Shape shape) {
        Node newNode = new Node(shape);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public void addAtIndex(int index, Shape shape) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        Node newNode = new Node(shape);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else if (index == size) {
            tail.next = newNode;
            tail = newNode;
        } else {
            Node prev = getNodeAtIndex(index - 1);
            newNode.next = prev.next;
            prev.next = newNode;
        }
        size++;
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    public Shape remove() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Shape removedShape = head.shape;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return removedShape;
    }

    public Shape removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Shape removedShape;
        if (index == 0) {
            removedShape = head.shape;
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            Node prev = getNodeAtIndex(index - 1);
            removedShape = prev.next.shape;
            prev.next = prev.next.next;
            if (prev.next == null) {
                tail = prev;
            }
        }
        size--;
        return removedShape;
    }

    private Node getNodeAtIndex(int index) {
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    public Iterator<Shape> iterator() {
        return new QueueIterator(head);
    }

    private class QueueIterator implements Iterator<Shape> {
        private Node current;

        public QueueIterator(Node start) {
            current = start;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Shape next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Shape shape = current.shape;
            current = current.next;
            return shape;
        }
    }
}




class Node {
    Shape shape;
    Node next;

    public Node(Shape shape) {
        this.shape = shape;
        this.next = null;
    }

    public Node() {
        this.shape = null;
        this.next = null;
    }
}