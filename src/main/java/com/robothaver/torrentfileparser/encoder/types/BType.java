package com.robothaver.torrentfileparser.encoder.types;

public abstract class BType<T> {
    protected final T value;
    protected byte[] bytes;

    protected BType(T value) {
        this.value = value;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public int getLength() {
        return bytes.length;
    }

    public T getValue() {
        return value;
    }
}
