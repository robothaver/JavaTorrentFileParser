package com.robothaver.torrentfileparser.encoder;

import com.robothaver.torrentfileparser.encoder.types.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TorrentEncoderImpl implements TorrentEncoder {
    @Override
    public byte[] encodeTorrentMap(Map<String, Object> torrentMap) {
        BType<?> baseMap = convertType(torrentMap);
        return baseMap.getBytes();
    }

    private BType<?> convertType(Object value) {
        BType<?> bValue;
        if (value instanceof String string) {
            bValue = new BString(string);
        } else if (value instanceof byte[] bytes) {
            bValue = new BString(bytes);
        } else if (value instanceof Integer integer) {
            bValue = new BInteger(integer);
        } else if (value instanceof Long longNumber) {
            bValue = new BInteger(longNumber);
        } else if (value instanceof List<?> list) {
            List<BType<?>> bTypes = new ArrayList<>();
            for (Object o : list) {
                bTypes.add(convertType(o));
            }
            bValue = new BList(bTypes);
        } else if (value instanceof Map<?, ?> map) {
            Map<BString, BType<?>> bMap = new HashMap<>();
            for (Map.Entry<?, ?> mapEntry : map.entrySet()) {
                if (mapEntry.getKey() instanceof String stringKey) {
                    bMap.put(new BString(stringKey), convertType(mapEntry.getValue()));
                } else throw new IllegalArgumentException("BDictionary can only have String keys");
            }
            bValue = new BDictionary(bMap);
            System.out.println(bValue);
        } else {
            throw new IllegalArgumentException(value + " is not a valid type");
        }
        return bValue;
    }
}
