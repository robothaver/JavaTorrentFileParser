package com.robothaver.torrentfileparser.parser;

import com.robothaver.torrentfileparser.domain.Torrent;
import com.robothaver.torrentfileparser.exception.MalformedTorrentFileException;

import java.util.Map;

public class TorrentFileParserImpl implements TorrentFileParser {
    @Override
    public Torrent parseToTorrent(byte[] bytes, boolean computeInfoHash) throws MalformedTorrentFileException {
        return new ParseWorker(bytes, computeInfoHash).parseToTorrent();
    }

    @Override
    public Map<String, Object> parseToMap(byte[] bytes, boolean computeInfoHash) throws MalformedTorrentFileException {
        return new ParseWorker(bytes, computeInfoHash).parseToMap();
    }
}
