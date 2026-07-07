package com.robothaver.torrentfileparser.parser;

import com.robothaver.torrentfileparser.domain.TorrentMetadata;
import com.robothaver.torrentfileparser.exception.MalformedTorrentFileException;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface TorrentFileParser {
    TorrentMetadata parseToMetadata(byte[] bytes, InfoHashCalculator infoHashCalculator) throws MalformedTorrentFileException, NoSuchAlgorithmException;

    Map<String, Object> parseToMap(byte[] bytes, InfoHashCalculator infoHashCalculator) throws MalformedTorrentFileException, NoSuchAlgorithmException;
}
