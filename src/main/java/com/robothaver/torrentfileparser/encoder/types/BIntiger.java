package com.robothaver.torrentfileparser.encoder.types;

import java.nio.charset.StandardCharsets;

public class BIntiger extends BType<Integer> {
    public BIntiger(int value) {
        super(value);
        bytes = ("i" + value + "e").getBytes(StandardCharsets.UTF_8);
    }
}
