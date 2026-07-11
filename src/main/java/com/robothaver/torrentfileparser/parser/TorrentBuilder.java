package com.robothaver.torrentfileparser.parser;

import com.robothaver.torrentfileparser.domain.TorrentFile;
import com.robothaver.torrentfileparser.domain.TorrentMetadata;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TorrentBuilder {
    private final TorrentMetadata torrentMetadata = new TorrentMetadata();
    private Long lastLength;

    @SuppressWarnings("unchecked")
    public void processKeyValue(String key, Object value) {
        switch (key) {
            case "announce" -> torrentMetadata.setAnnounce(parseString(value));
            case "name" -> torrentMetadata.setName(parseString(value));
            case "announce-list" -> torrentMetadata.setAnnounceList(parseStringList(value));
            case "azureus_properties" -> {
                Map<String, Object> parsedMap = (Map<String, Object>) value;
                torrentMetadata.setAzureusProperties(parsedMap);
                cleanLeakedKeys(parsedMap);
            }
            case "created by" -> torrentMetadata.setCreator(parseString(value));
            case "creation date" -> torrentMetadata.setCreationDate((long) value);
            case "encoding" -> torrentMetadata.setEncoding(parseString(value));
            case "files" -> torrentMetadata.setSingleFile(false);
            case "length" -> {
                lastLength = (long) value;
                torrentMetadata.setTotalLength(torrentMetadata.getTotalLength() + lastLength);
            }
            case "path" -> parseFile(value);
            case "piece length" -> torrentMetadata.setPieceLength((long) value);
            case "source" -> torrentMetadata.setSource(parseString(value));
            case "pieces" -> torrentMetadata.setPieces((byte[]) value);
            case "comment" -> torrentMetadata.setComment(parseString(value));
            case "private" -> torrentMetadata.setPrivate((long) value == 1);
            default -> {
                if (key.equals("info")) return;
                torrentMetadata.getOtherValues().put(key, value);
            }
        }
    }

    public void setInfoHash(String infoHash) {
        torrentMetadata.setInfoHash(infoHash);
    }

    public TorrentMetadata getTorrent() {
        return torrentMetadata;
    }

    @SuppressWarnings("unchecked")
    private void cleanLeakedKeys(Map<String, Object> azureusProperties) {
        azureusProperties.forEach((key, value) -> {
            torrentMetadata.getOtherValues().remove(key);

            if (value instanceof Map<?, ?>) {
                cleanLeakedKeys((Map<String, Object>) value);
            }
        });
    }

    private List<List<String>> parseStringList(Object value) {
        @SuppressWarnings("unchecked")
        List<List<byte[]>> stringBytes = (List<List<byte[]>>) value;
        List<List<String>> stringList = new ArrayList<>();
        for (List<byte[]> byteList : stringBytes) {
            ArrayList<String> innerStringList = new ArrayList<>();
            for (byte[] bytes : byteList) {
                innerStringList.add(parseString(bytes));
            }
            stringList.add(innerStringList);
        }
        return stringList;
    }

    private void parseFile(Object value) {
        List<String> pathElements = new ArrayList<>();
        @SuppressWarnings("unchecked")
        List<byte[]> stringBytes = (List<byte[]>) value;
        for (byte[] bytes : stringBytes) pathElements.add(parseString(bytes));

        String fullPath = String.join("/", pathElements);
        torrentMetadata.getFiles().add(new TorrentFile(lastLength, fullPath));
    }

    private String parseString(Object object) {
        if (object instanceof byte[] bytes) {
            return new String(bytes, StandardCharsets.UTF_8);
        }
        throw new IllegalArgumentException("String value is not an instance of byte[]. Object: " + object);
    }
}
