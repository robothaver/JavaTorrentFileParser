package com.robothaver.torrentfileparser.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Torrent {
    private String announce;
    private String name;
    private Long pieceLength;
    private boolean isSingleFile = true;
    private boolean isPrivate;
    private Long totalLength = 0L;
    private String pieces;
    private final List<TorrentFile> files = new ArrayList<>();
    private String infoHash;

    // Optional fields
    private List<List<String>> announceList = null;
    private String creator = null;
    private Long creationDate = null;
    private String source = null;
    private String comment = null;
    private String encoding = null;
    private Map<String, Object> azureusProperties = null;

    public String getAnnounce() {
        return announce;
    }

    public void setAnnounce(String announce) {
        this.announce = announce;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPieceLength() {
        return pieceLength;
    }

    public void setPieceLength(Long pieceLength) {
        this.pieceLength = pieceLength;
    }

    public boolean isSingleFile() {
        return isSingleFile;
    }

    public void setSingleFile(boolean singleFile) {
        isSingleFile = singleFile;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Long getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(Long totalLength) {
        this.totalLength = totalLength;
    }

    public String getPieces() {
        return pieces;
    }

    public void setPieces(String pieces) {
        this.pieces = pieces;
    }

    public List<TorrentFile> getFiles() {
        return files;
    }

    public List<List<String>> getAnnounceList() {
        return announceList;
    }

    public void setAnnounceList(List<List<String>> announceList) {
        this.announceList = announceList;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Map<String, Object> getAzureusProperties() {
        return azureusProperties;
    }

    public void setAzureusProperties(Map<String, Object> azureusProperties) {
        this.azureusProperties = azureusProperties;
    }

    public String getInfoHash() {
        return infoHash;
    }

    public void setInfoHash(String infoHash) {
        this.infoHash = infoHash;
    }

    @Override
    public String toString() {
        return "Torrent{" +
                "announce='" + announce + '\'' +
                ", name='" + name + '\'' +
                ", pieceLength=" + pieceLength +
                ", isSingleFile=" + isSingleFile +
                ", isPrivate=" + isPrivate +
                ", totalLength=" + totalLength +
                ", files=" + files +
                ", infoHash='" + infoHash + '\'' +
                ", announceList=" + announceList +
                ", creator='" + creator + '\'' +
                ", creationDate=" + creationDate +
                ", source='" + source + '\'' +
                ", comment='" + comment + '\'' +
                ", encoding='" + encoding + '\'' +
                ", azureusProperties=" + azureusProperties +
                '}';
    }
}
