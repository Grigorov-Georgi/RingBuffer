package org.example;

import java.util.function.Supplier;

public class ObjectPoolRingBuffer<T> {
    private final T[] pool;
    private final int capacity;
    private int currentIndex = 0;
    private int filled = 0;
    private boolean bulkAdd;

    // Add one by one constructor
    @SuppressWarnings("unchecked")
    public ObjectPoolRingBuffer(int capacity) {
        this.capacity = capacity;
        this.pool = (T[]) new Object[capacity];
        this.bulkAdd = false;
    }

    // Bulk add constructor
    @SuppressWarnings("unchecked")
    public ObjectPoolRingBuffer(int capacity, Supplier<T> factory) {
        this.capacity = capacity;
        this.pool = (T[]) new Object[capacity];
        this.bulkAdd = true;
        fill(factory);
    }

    // for adding elements in bulk
    public void fill(Supplier<T> factory) {
        if (!bulkAdd)
            throw new IllegalStateException("This method cannot be used with add one by one constructor");

        for (int i = 0; i < capacity; i++) {
            pool[i] = factory.get();
            filled++;
        }
    }

    // for adding the elements one by one
    public void add(T element) {
        if (bulkAdd)
            throw new IllegalStateException("This method cannot be used with bulk add constructor");

        if (filled < capacity && pool[filled] == null) {
            pool[filled] = element;
            filled++;
            return;
        }

        throw new IllegalStateException("Pool is full");
    }

    public T getNext() {
        if (!isFilled())
            throw new IllegalStateException("Pool is not fully initialized");

        T element = pool[currentIndex];
        currentIndex = (currentIndex + 1) % capacity;
        return element;
    }

    public boolean isFilled() {
        return filled == capacity;
    }
}
