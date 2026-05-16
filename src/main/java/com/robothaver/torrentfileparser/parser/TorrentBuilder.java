package com.robothaver.torrentfileparser.parser;

import com.robothaver.torrentfileparser.domain.TorrentMetadata;
import com.robothaver.torrentfileparser.domain.TorrentFile;

import java.util.List;
import java.util.Map;

public class TorrentBuilder {
    private final TorrentMetadata torrentMetadata = new TorrentMetadata();
    private Long lastLength;

    @SuppressWarnings("unchecked")
    public void processKeyValue(String key, Object value) {
        switch (key) {
            case "announce" -> torrentMetadata.setAnnounce(String.valueOf(value));
            case "name" -> torrentMetadata.setName(String.valueOf(value));
            case "announce-list" -> torrentMetadata.setAnnounceList((List<List<String>>) value);
            case "azureus_properties" -> torrentMetadata.setAzureusProperties((Map<String, Object>) value);
            case "created by" -> torrentMetadata.setCreator(String.valueOf(value));
            case "creation date" -> torrentMetadata.setCreationDate((long) value);
            case "encoding" -> torrentMetadata.setEncoding(String.valueOf(value));
            case "files" -> torrentMetadata.setSingleFile(false);
            case "length" -> {
                lastLength = (long) value;
                torrentMetadata.setTotalLength(torrentMetadata.getTotalLength() + lastLength);
            }
            case "path" -> parseFile(value);
            case "piece length" -> torrentMetadata.setPieceLength((long) value);
            case "source" -> torrentMetadata.setSource(String.valueOf(value));
            case "pieces" -> torrentMetadata.setPieces(String.valueOf(value));
            case "comment" -> torrentMetadata.setComment(String.valueOf(value));
            case "private" -> torrentMetadata.setPrivate((long) value == 1);
            default -> {
                if (key.equals("info")) return;
                torrentMetadata.getOtherValues().put(key, value);
            }
        }
    }

    public void setInfoHash(byte[] infoBytes) {
        torrentMetadata.setInfoHash(InfoHasCompute.getInfoHash(infoBytes));
    }

    private void parseFile(Object value) {
        @SuppressWarnings("unchecked")
        List<String> pathElements = (List<String>) value;
        String fullPath = String.join("/", pathElements);
        torrentMetadata.getFiles().add(new TorrentFile(lastLength, fullPath));
    }

    public TorrentMetadata getTorrent() {
        return torrentMetadata;
    }
}
