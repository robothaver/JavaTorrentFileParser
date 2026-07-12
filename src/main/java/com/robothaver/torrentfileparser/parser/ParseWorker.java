package com.robothaver.torrentfileparser.parser;

import com.robothaver.torrentfileparser.domain.TorrentMetadata;
import com.robothaver.torrentfileparser.exception.MalformedTorrentFileException;

import java.nio.charset.StandardCharsets;
import java.util.*;

class ParseWorker {
    private final byte[] bytes;
    private final InfoHashCalculator infoHashCalculator;
    private TorrentBuilder torrentBuilder;
    private int iterator;
    private int infoDictStartIndex;

    public ParseWorker(byte[] bytes, InfoHashCalculator infoHashCalculator) {
        this.bytes = bytes;
        this.infoHashCalculator = infoHashCalculator;
        this.iterator = 0;
        this.infoDictStartIndex = -1;
    }

    public TorrentMetadata parseToMetadata() throws MalformedTorrentFileException {
        torrentBuilder = new TorrentBuilder();
        parse();
        return torrentBuilder.getTorrent();
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> parseToMap() throws MalformedTorrentFileException {
        Object parse = parse();

        if (!(parse instanceof Map)) {
            throw new MalformedTorrentFileException("Root element is not a dictionary");
        }

        return (Map<String, Object>) parse;
    }

    private Object parse() throws MalformedTorrentFileException {
        return switch (bytes[iterator]) {
            case 'i' -> parseInt();
            case 'l' -> parseList();
            case 'd' -> parseDict();
            default -> {
                if (isInt(bytes[iterator])) {
                    yield parseString();
                } else {
                    throw new MalformedTorrentFileException("Illegal character " + (char) bytes[iterator] + " at " + iterator);
                }
            }
        };
    }

    private boolean isInt(byte currentByte) {
        return (byte) '0' <= currentByte && currentByte <= (byte) '9';
    }

    private List<Object> parseList() throws MalformedTorrentFileException {
        iterator++; // Skipping the l
        List<Object> list = new ArrayList<>();
        while (bytes[iterator] != 'e') {
            list.add(parse());
        }
        iterator++; // Skipping closing e
        return list;
    }

    private Map<String, Object> parseDict() throws MalformedTorrentFileException {
        iterator++; // Skipping the d
        Map<String, Object> map = new HashMap<>();
        String key = null;
        while (bytes[iterator] != 'e') {
            Object parseValue = parse();
            if (key == null) {
                key = parseDictionaryKey(parseValue);
                if (key.equals("info")) {
                    infoDictStartIndex = iterator;
                }
            } else {
                map.put(key, parseValue);
                if (key.equals("info")) tryCalculateInfoHash(map);

                if (torrentBuilder != null) {
                    torrentBuilder.processKeyValue(key, parseValue);
                }
                key = null;
            }
        }
        iterator++; // Skipping closing e
        return map;
    }

    private Object parseString() throws MalformedTorrentFileException {
        int startIndex = iterator;
        int colonIndex = 0;
        while (iterator < bytes.length) {
            if (bytes[iterator] == (byte) ':') {
                colonIndex = iterator;
                break;
            } else {
                iterator++;
            }
        }
        int length;
        try {
            length = Integer.parseInt(new String(Arrays.copyOfRange(bytes, startIndex, colonIndex), StandardCharsets.UTF_8));
        } catch (NumberFormatException e) {
            throw new MalformedTorrentFileException();
        }

        iterator += length + 1;
        return Arrays.copyOfRange(bytes, colonIndex + 1, colonIndex + length + 1);
    }

    private Long parseInt() {
        int startIndex = iterator + 1;
        while (bytes[iterator] != 'e') {
            iterator++;
        }
        byte[] valueBytes = Arrays.copyOfRange(bytes, startIndex, iterator);
        iterator++; // Skipping closing e
        return Long.parseLong(new String(valueBytes, StandardCharsets.UTF_8));
    }

    private void tryCalculateInfoHash(Map<String, Object> map) {
        if (infoHashCalculator == null) return;

        byte[] infoDictionaryBytes = Arrays.copyOfRange(bytes, infoDictStartIndex, iterator);
        String infoHash = infoHashCalculator.calculateInfoHash(infoDictionaryBytes);
        if (torrentBuilder != null) torrentBuilder.setInfoHash(infoHash); else map.put("infoHash", infoHash);
    }

    private String parseDictionaryKey(Object object) throws MalformedTorrentFileException {
        if (!(object instanceof byte[])) {
            throw new MalformedTorrentFileException("Dictionary key must be a string. Key: " + object);
        }
        return new String((byte[]) object, StandardCharsets.UTF_8);
    }
}
