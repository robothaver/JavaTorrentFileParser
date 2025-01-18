package com.robothaver.torrentfileparser.domain;

import java.util.List;
import java.util.Map;

public class TorrentBuilder {
    private final Torrent torrent = new Torrent();
    private Long lastLength;

    public void processKeyValue(String key, Object value) {
        switch (key) {
            case "announce" -> torrent.setAnnounce(String.valueOf(value));
            case "name" -> torrent.setName(String.valueOf(value));
            case "announce-list" -> torrent.setAnnounceList((List<List<String>>) value);
            case "azureus_properties" -> torrent.setAzureusProperties((Map<String, Object>) value);
            case "created by" -> torrent.setCreator(String.valueOf(value));
            case "creation date" -> torrent.setCreationDate((long) value);
            case "encoding" -> torrent.setEncoding(String.valueOf(value));
            case "files" -> torrent.setSingleFile(false);
            case "length" -> {
                lastLength = (long) value;
                torrent.setTotalLength(torrent.getTotalLength() + lastLength);
            }
            case "path" -> torrent.getFiles().add(new TorrentFile(lastLength, formatFilePath(String.valueOf(value))));
            case "piece length" -> torrent.setPieceLength((long) value);
            case "source" -> torrent.setSource(String.valueOf(value));
            case "pieces" -> torrent.setPieces(String.valueOf(value));
            case "comment" -> torrent.setComment(String.valueOf(value));
            case "private" -> torrent.setPrivate((long) value == 1);
        }
    }

    public void setInfoHash(byte[] infoBytes) {
        torrent.setInfoHash(InfoHasCompute.getInfoHash(infoBytes));
    }

    private String formatFilePath(String path) {
        return path.replace("[", "").replace("]", "").replace(", ", "/");
    }

    public Torrent getTorrent() {
        return torrent;
    }
}
