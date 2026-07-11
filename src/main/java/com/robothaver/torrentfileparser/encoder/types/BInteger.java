package com.robothaver.torrentfileparser.encoder.types;

import java.nio.charset.StandardCharsets;

public class BInteger extends BType<Long> {
    public BInteger(long value) {
        super(value);
        bytes = ("i" + value + "e").getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String toString() {
        return "BInteger{" + value + '}';
    }
}
