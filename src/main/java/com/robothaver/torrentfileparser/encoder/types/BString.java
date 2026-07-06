package com.robothaver.torrentfileparser.encoder.types;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class BString extends BType<String> {
    public BString(String value) {
        super(value);
        byte[] prefix = (value.length() + ":").getBytes(StandardCharsets.US_ASCII);

        bytes = ByteBuffer.allocate(prefix.length + value.length())
                .put(prefix)
                .put(value.getBytes())
                .array();
    }
}
