package com.robothaver.torrentfileparser.exception;

public class MalformedTorrentFileException extends Exception {
    public MalformedTorrentFileException() {

    }

    public MalformedTorrentFileException(String message) {
        super(message);
    }
}
