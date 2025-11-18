# Ring Buffer Implementations

Two ring buffer implementations demonstrating different use cases.

## RingBuffer<T>

**Circular queue with fixed capacity that overwrites oldest elements when full.**

**Features:**
- Fixed-size FIFO queue
- Automatic overwrite of oldest element when full
- O(1) add and remove operations

**Use cases:**
- Log buffering (keep last N messages)
- Streaming data (audio/video buffers)
- Recent history tracking
- Any scenario needing bounded memory with automatic eviction

**Example:**
```java
RingBuffer<String> buffer = new RingBuffer<>(3);
buffer.add("First");
buffer.add("Second");
buffer.add("Third");
buffer.add("Fourth");  // Overwrites "First"
buffer.remove();       // Returns "Second"
```

## ObjectPoolRingBuffer<T>

**Circular iterator for reusing pre-allocated objects.**

**Features:**
- Pre-populate with objects
- `getNext()` cycles through pool indefinitely
- Objects never leave the pool
- Two initialization modes: bulk (Supplier) or one-by-one

**Use cases:**
- Connection pools
- Thread pools
- Pre-allocated buffers
- Round-robin resource allocation

**Example:**
```java
// Bulk initialization
ObjectPoolRingBuffer<Connection> pool = new ObjectPoolRingBuffer<>(
    5, 
    () -> new Connection()
);

// One-by-one initialization
ObjectPoolRingBuffer<Connection> pool = new ObjectPoolRingBuffer<>(3);
pool.add(new Connection("Master"));
pool.add(new Connection("Slave-1"));
pool.add(new Connection("Slave-2"));

Connection conn = pool.getNext();  // Cycles forever
```

## Key Difference

- **RingBuffer**: Elements added/removed dynamically, oldest evicted when full
- **ObjectPoolRingBuffer**: Fixed objects cycle repeatedly, never added/removed during use

## Running Examples

```bash
./gradlew run
```

