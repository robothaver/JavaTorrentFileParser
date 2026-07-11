package com.robothaver.torrentfileparser.encoder.types;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class BString extends BType<byte[]> implements Comparable<BString> {
    public BString(byte[] value) {
        super(value);
        byte[] prefix = (value.length + ":").getBytes(StandardCharsets.UTF_8);

        bytes = ByteBuffer.allocate(prefix.length + value.length)
                .put(prefix)
                .put(value)
                .array();
    }

    public BString(String value) {
        this(value.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public int compareTo(BString o) {
        return Arrays.compareUnsigned(value, o.getValue());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BString bString) {
            return Arrays.equals(value, bString.value);
        }
        else return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }

    @Override
    public String toString() {
        if (value.length > 500) return "BString{binary data, length=" + value.length + "}";

        return "BString{" + new String(value, StandardCharsets.UTF_8) + '}';
    }
}
