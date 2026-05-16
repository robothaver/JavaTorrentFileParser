package com.robothaver.torrentfileparser.parser;

import com.robothaver.torrentfileparser.domain.TorrentMetadata;
import com.robothaver.torrentfileparser.exception.MalformedTorrentFileException;

import java.util.Map;

public class TorrentFileParserImpl implements TorrentFileParser {
    @Override
    public TorrentMetadata parseToMetadata(byte[] bytes, boolean computeInfoHash) throws MalformedTorrentFileException {
        return new ParseWorker(bytes, computeInfoHash).parseToMetadata();
    }

    @Override
    public Map<String, Object> parseToMap(byte[] bytes, boolean computeInfoHash) throws MalformedTorrentFileException {
        return new ParseWorker(bytes, computeInfoHash).parseToMap();
    }
}
