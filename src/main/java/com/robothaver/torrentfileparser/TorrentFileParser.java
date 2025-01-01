package com.robothaver.torrentfileparser;

import com.robothaver.torrentfileparser.domain.Torrent;
import com.robothaver.torrentfileparser.domain.TorrentBuilder;
import com.robothaver.torrentfileparser.exception.MalformedTorrentFileException;

import java.util.*;

public class TorrentFileParser {
    private final byte[] bytes;
    private int iterator = 0;
    private TorrentBuilder torrentBuilder = null;

    public TorrentFileParser(byte[] bytes) {
        this.bytes = bytes;
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

    public Torrent parseToTorrent() throws MalformedTorrentFileException {
        torrentBuilder = new TorrentBuilder();
        parse();
        iterator = 0;
        return torrentBuilder.getTorrent();
    }

    public Map<String, Object> parseToMap() throws MalformedTorrentFileException {
        return (Map<String, Object>) parse();
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
                key = String.valueOf(parseValue);
            } else {
                map.put(key, parseValue);
                if (torrentBuilder != null) {
                    torrentBuilder.processKeyValue(key, parseValue);
                }
                key = null;
            }
        }
        iterator++; // Skipping closing e
        return map;
    }

    private String parseString() throws MalformedTorrentFileException {
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
            length = Integer.parseInt(new String(Arrays.copyOfRange(bytes, startIndex, colonIndex)));
        } catch (NumberFormatException e) {
            throw new MalformedTorrentFileException();
        }

        String value = new String(Arrays.copyOfRange(bytes, colonIndex + 1, colonIndex + length + 1));
        iterator += length + 1;
        return value;
    }

    private Long parseInt() {
        int startIndex = iterator + 1;
        while (bytes[iterator] != 'e') {
            iterator++;
        }
        byte[] valueBytes = Arrays.copyOfRange(bytes, startIndex, iterator);
        iterator++; // Skipping closing e
        return Long.parseLong(new String(valueBytes));
    }
}
