package org.example;

public class App {
    public static void main(String[] args) {
        demonstrateRingBuffer();
        System.out.println();
        demonstrateObjectPoolBulkAdd();
        System.out.println();
        demonstrateObjectPoolOneByOne();
    }

    private static void demonstrateRingBuffer() {
        System.out.println("=== RingBuffer (Circular Queue) ===");
        RingBuffer<String> buffer = new RingBuffer<>(3);

        buffer.add("First");
        buffer.add("Second");
        buffer.add("Third");
        System.out.println("Added 3 items, size: " + buffer.size());
        System.out.println(buffer.toString());

        buffer.add("Fourth");
        System.out.println("Added 4th item (overwrites oldest), size: " + buffer.size());
        System.out.println(buffer.toString());

        System.out.println("Removed: " + buffer.remove());
        System.out.println("Removed: " + buffer.remove());
        System.out.println("Size after 2 removes: " + buffer.size());
        System.out.println(buffer.toString());
    }

    private static void demonstrateObjectPoolBulkAdd() {
        System.out.println("=== ObjectPoolRingBuffer (Bulk Add with Supplier) ===");

        final int[] counter = new int[] { 1 };
        ObjectPoolRingBuffer<Connection> pool = new ObjectPoolRingBuffer<>(
                5,
                () -> new Connection("Thread-" + counter[0]++));

        System.out.println("Pool filled: " + pool.isFilled());
        System.out.println("Getting connections in cycle:");
        for (int i = 0; i < 8; i++) {
            System.out.println("  " + pool.getNext().name);
        }
    }

    private static void demonstrateObjectPoolOneByOne() {
        System.out.println("=== ObjectPoolRingBuffer (One by One Add) ===");

        ObjectPoolRingBuffer<Connection> pool = new ObjectPoolRingBuffer<>(3);

        pool.add(new Connection("Master"));
        pool.add(new Connection("Slave-1"));
        pool.add(new Connection("Slave-2"));

        System.out.println("Pool filled: " + pool.isFilled());
        System.out.println("Getting connections in cycle:");
        for (int i = 0; i < 6; i++) {
            System.out.println("  " + pool.getNext().name);
        }
    }

    static class Connection {
        final String name;

        Connection(String name) {
            this.name = name;
        }
    }
}
