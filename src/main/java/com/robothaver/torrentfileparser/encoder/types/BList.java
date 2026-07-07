package com.robothaver.torrentfileparser.encoder.types;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class BList extends BType<List<BType<?>>> {
    public BList(List<BType<?>> value) {
        super(value);

        int totalLength = 2;
        for (BType<?> bType : value) {
            totalLength += bType.getLength();
        }
        ByteBuffer buffer = ByteBuffer.allocate(totalLength)
                .put("l".getBytes(StandardCharsets.UTF_8));
        for (BType<?> bType : value) {
            buffer.put(bType.getBytes());
        }
        buffer.put("e".getBytes(StandardCharsets.UTF_8));
        bytes = buffer.array();
    }

    @Override
    public String toString() {
        return "BList{" + value + '}';
    }
}
