package com.robothaver.torrentfileparser.encoder.types;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class BDictionary extends BType<Map<BString, BType<?>>> {
    public BDictionary(Map<BString, BType<?>> value) {
        super(value);

        List<BString> sortedKeys = value.keySet().stream().sorted().toList();
        int totalLength = 2;
        for (BString key : sortedKeys) {
            totalLength += key.getLength() + value.get(key).getLength();
        }
        ByteBuffer buffer = ByteBuffer.allocate(totalLength)
                .put("d".getBytes(StandardCharsets.UTF_8));
        for (BString key : sortedKeys) {
            buffer
                    .put(key.getBytes())
                    .put(value.get(key).getBytes());
        }
        buffer.put("e".getBytes(StandardCharsets.UTF_8));
        bytes = buffer.array();
    }

    @Override
    public String toString() {
        return "BDictionary{" + value + '}';
    }
}
