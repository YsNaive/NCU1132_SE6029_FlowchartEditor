package core;

import java.util.*;

public class BucketSet<T> implements Iterable<T> {
    private final Map<Integer, HashSet<T>> buckets;

    public BucketSet() {
        this.buckets = new HashMap<>();
    }

    public void add(int priority, T item) {
        buckets.computeIfAbsent(priority, k -> new HashSet<>()).add(item);
    }

    public void remove(int priority, T item) {
        HashSet<T> set = buckets.get(priority);
        if (set != null) {
            set.remove(item);
            if (set.isEmpty()) {
                buckets.remove(priority);
            }
        }
    }

    public void remove(T item) {
        Iterator<Map.Entry<Integer, HashSet<T>>> iterator = buckets.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, HashSet<T>> entry = iterator.next();
            entry.getValue().remove(item);
            if (entry.getValue().isEmpty()) {
                iterator.remove();
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private final List<Integer> sortedKeys = new ArrayList<>(buckets.keySet());
            private int keyIndex = 0;
            private Iterator<T> currentIterator = sortedKeys.isEmpty() ? Collections.emptyIterator() : buckets.get(sortedKeys.get(0)).iterator();

            {
                Collections.sort(sortedKeys);
            }

            @Override
            public boolean hasNext() {
                while (!currentIterator.hasNext() && keyIndex < sortedKeys.size() - 1) {
                    keyIndex++;
                    currentIterator = buckets.get(sortedKeys.get(keyIndex)).iterator();
                }
                return currentIterator.hasNext();
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return currentIterator.next();
            }
        };
    }

    public Iterable<T> reverseIterable() {
        return () -> new Iterator<T>() {
            private final List<Integer> sortedKeys = new ArrayList<>(buckets.keySet());
            private int keyIndex;
            private Iterator<T> currentIterator;

            {
                sortedKeys.sort(Collections.reverseOrder());
                keyIndex = sortedKeys.isEmpty() ? -1 : 0;
                currentIterator = keyIndex == -1 ? Collections.emptyIterator() : buckets.get(sortedKeys.get(0)).iterator();
            }

            @Override
            public boolean hasNext() {
                while (!currentIterator.hasNext() && keyIndex < sortedKeys.size() - 1) {
                    keyIndex++;
                    currentIterator = buckets.get(sortedKeys.get(keyIndex)).iterator();
                }
                return currentIterator.hasNext();
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return currentIterator.next();
            }
        };
    }
}
