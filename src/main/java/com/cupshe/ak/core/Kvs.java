package com.cupshe.ak.core;

import org.springframework.lang.NonNull;

import java.util.Iterator;
import java.util.StringJoiner;

/**
 * Kvs
 *
 * @author zxy
 */
public class Kvs implements Iterable<Kv> {

    /**
     * Empty Kvs
     */
    public static final Kvs EMPTY = new EmptyKvs();

    private final Kv head;
    private Kv curr;
    private int size;

    public Kvs() {
        head = Kv.empty();
        curr = head;
        size = 0;
    }

    public static Kvs emptyKvs() {
        return EMPTY;
    }

    public void add(Kv kv) {
        curr.setNext(kv);
        curr = curr.getNext();
        ++size;
    }

    public void addAll(Kvs kvs) {
        kvs.forEach(this::add);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return this == EMPTY || size == 0;
    }

    @Override
    @NonNull
    public Iterator<Kv> iterator() {
        return new Itr();
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ");
        this.forEach(t -> joiner.add(t.toString()));
        return "Kvs[" + joiner.toString() + ']';
    }

    /**
     * Iterator
     */
    public class Itr implements Iterator<Kv> {

        private Kv _curr;

        public Itr() {
            _curr = head;
        }

        @Override
        public boolean hasNext() {
            return _curr.getNext() != null;
        }

        @Override
        public Kv next() {
            return _curr = _curr.getNext();
        }
    }

    /**
     * EmptyKvs
     */
    private static class EmptyKvs extends Kvs {

        @Override
        public void add(Kv kv) {}

        @Override
        public void addAll(Kvs kvs) {}

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Iterator<Kv> iterator() {
            return new Itr() {

                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public Kv next() {
                    return null;
                }
            };
        }
    }
}
