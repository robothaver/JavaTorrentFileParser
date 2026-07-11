package com.robothaver.torrentfileparser.encoder;

import com.robothaver.torrentfileparser.domain.TorrentMetadata;
import com.robothaver.torrentfileparser.encoder.types.*;

import java.util.*;

public class TorrentEncoderImpl implements TorrentEncoder {
    private final TorrentMetadataMapper metadataMapper;

    public TorrentEncoderImpl(TorrentMetadataMapper metadataMapper) {
        this.metadataMapper = metadataMapper;
    }

    public TorrentEncoderImpl() {
        this.metadataMapper = new TorrentMetadataMapperImpl();
    }

    @Override
    public byte[] encodeTorrentMap(Map<String, Object> torrentMap) {
        BType<?> baseMap = convertType(torrentMap);
        return baseMap.getBytes();
    }

    @Override
    public byte[] encodeTorrentMetadata(TorrentMetadata torrentMetadata) {
        return convertType(metadataMapper.metadataToMap(torrentMetadata)).getBytes();
    }

    @SuppressWarnings("unchecked")
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
            bValue = encodeList((List<Object>) list);
        } else if (value instanceof Map<?, ?> map) {
            bValue = encodeDictionary((Map<String, Object>) map);
        } else {
            throw new IllegalArgumentException(value + " is not a valid type");
        }
        return bValue;
    }

    private BList encodeList(List<Object> list) {
        List<BType<?>> bTypes = new ArrayList<>();
        for (Object o : list) {
            bTypes.add(convertType(o));
        }
        return new BList(bTypes);
    }

    private BDictionary encodeDictionary(Map<String, Object> map) {
        Map<BString, BType<?>> bMap = new HashMap<>();
        for (Map.Entry<?, ?> mapEntry : map.entrySet()) {
            if (mapEntry.getKey() instanceof String stringKey) {
                bMap.put(new BString(stringKey), convertType(mapEntry.getValue()));
            } else throw new IllegalArgumentException("BDictionary can only have String keys");
        }
        return new BDictionary(bMap);
    }
}
