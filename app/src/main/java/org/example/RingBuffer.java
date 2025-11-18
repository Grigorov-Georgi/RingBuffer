package org.example;

public class RingBuffer<T> {
    private int size = 0;
    private final int capacity;
    private final T[] arr;

    private int headIndex = 0;
    private int tailIndex = 0;

    @SuppressWarnings("unchecked")
    public RingBuffer() {
        this.capacity = 20;
        this.arr = (T[]) new Object[capacity];
    }

    @SuppressWarnings("unchecked")
    public RingBuffer(int capacity) {
        this.capacity = capacity;
        this.arr = (T[]) new Object[capacity];
    }

    public void add(T element) {
        arr[tailIndex] = element;
        tailIndex = (tailIndex + 1) % capacity;

        if (size < capacity) {
            size++;
        } else {
            headIndex = (headIndex + 1) % capacity;
        }
    }

    public T remove() {
        if (size == 0) {
            return null;
        }

        T element = arr[headIndex];
        arr[headIndex] = null;
        headIndex = (headIndex + 1) % capacity;
        size--;

        return element;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");

        for (int i = 0; i < size; i++) {
            var index = (headIndex + i) % capacity;
            sb.append(arr[index].toString() + " ");
        }

        sb.append("]");
        return sb.toString();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }
}