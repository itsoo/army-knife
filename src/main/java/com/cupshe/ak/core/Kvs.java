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

    public static final Kvs EMPTY = new Kvs();

    private final Kv head;
    private Kv curr;
    private int size;

    public Kvs() {
        head = Kv.EMPTY;
        curr = head;
        size = 0;
    }

    public static Kvs emptyKvs() {
        return EMPTY;
    }

    public void add(Kv kv) {
        curr.setNext(kv);
        curr = curr.getNext();
        size++;
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
        for (Kv kv : this) {
            joiner.add(kv.toString());
        }

        return "Kvs[" + joiner.toString() + ']';
    }

    /**
     * Iterator
     */
    public final class Itr implements Iterator<Kv> {

        private Kv _curr;

        Itr() {
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
}
