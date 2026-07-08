package com.robothaver.torrentfileparser.encoder;

import com.robothaver.torrentfileparser.domain.TorrentFile;
import com.robothaver.torrentfileparser.domain.TorrentMetadata;
import com.robothaver.torrentfileparser.encoder.types.*;

import java.util.*;

public class TorrentEncoderImpl implements TorrentEncoder {
    @Override
    public byte[] encodeTorrentMap(Map<String, Object> torrentMap) {
        BType<?> baseMap = convertType(torrentMap);
        return baseMap.getBytes();
    }

    @Override
    public byte[] encodeTorrentMetadata(TorrentMetadata torrentMetadata) {
        Map<String, Object> torrentMap = new HashMap<>(torrentMetadata.getOtherValues());

        getTopLevelFields(torrentMetadata, torrentMap);
        Map<String, Object> infoDict = createInfoDictionary(torrentMetadata);

        torrentMap.put("info", infoDict);

        return convertType(torrentMap).getBytes();
    }

    private void getTopLevelFields(TorrentMetadata torrentMetadata, Map<String, Object> torrentMap) {
        if (torrentMetadata.getAnnounce() != null) {
            torrentMap.put("announce", torrentMetadata.getAnnounce());
        }

        if (torrentMetadata.getAnnounceList() != null) {
            torrentMap.put("announce-list", torrentMetadata.getAnnounceList());
        }

        if (torrentMetadata.getCreator() != null) {
            torrentMap.put("created by", torrentMetadata.getCreator());
        }

        if (torrentMetadata.getCreationDate() != null) {
            torrentMap.put("creation date", torrentMetadata.getCreationDate());
        }

        if (torrentMetadata.getComment() != null) {
            torrentMap.put("comment", torrentMetadata.getComment());
        }

        if (torrentMetadata.getEncoding() != null) {
            torrentMap.put("encoding", torrentMetadata.getEncoding());
        }

        if (torrentMetadata.getAzureusProperties() != null) {
            torrentMap.put("azureus_properties", torrentMetadata.getAzureusProperties());
        }
    }

    private Map<String, Object> createInfoDictionary(TorrentMetadata torrentMetadata) {
        Map<String, Object> infoDict = new HashMap<>();

        infoDict.put("name", torrentMetadata.getName());
        infoDict.put("piece length", torrentMetadata.getPieceLength());
        infoDict.put("pieces", torrentMetadata.getPieces());

        if (torrentMetadata.isSingleFile()) {
            infoDict.put("length", torrentMetadata.getTotalLength());
        } else {
            List<Map<String, Object>> fileList = new ArrayList<>();

            for (TorrentFile file : torrentMetadata.getFiles()) {
                Map<String, Object> fileMap = new HashMap<>();

                fileMap.put("length", file.length());
                fileMap.put("path", Arrays.asList(file.path().split("/")));

                fileList.add(fileMap);
            }

            infoDict.put("files", fileList);
        }

        infoDict.put("private", torrentMetadata.isPrivate() ? 1 : 0);

        if (torrentMetadata.getSource() != null) {
            infoDict.put("source", torrentMetadata.getSource());
        }

        return infoDict;
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
