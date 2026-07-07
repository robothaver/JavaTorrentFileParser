package com.robothaver.torrentfileparser.parser;

import com.robothaver.torrentfileparser.domain.TorrentMetadata;
import com.robothaver.torrentfileparser.exception.MalformedTorrentFileException;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class TorrentFileParserImpl implements TorrentFileParser {
    @Override
    public TorrentMetadata parseToMetadata(byte[] bytes, InfoHashCalculator infoHashCalculator) throws MalformedTorrentFileException, NoSuchAlgorithmException {
        return new ParseWorker(bytes, new InfoHashCalculatorImpl()).parseToMetadata();
    }

    @Override
    public Map<String, Object> parseToMap(byte[] bytes, InfoHashCalculator infoHashCalculator) throws MalformedTorrentFileException, NoSuchAlgorithmException {
        return new ParseWorker(bytes, new InfoHashCalculatorImpl()).parseToMap();
    }
}
