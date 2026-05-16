package com.robothaver.torrentfileparser.parser;

import com.robothaver.torrentfileparser.domain.TorrentMetadata;
import com.robothaver.torrentfileparser.exception.MalformedTorrentFileException;

import java.util.Map;

public interface TorrentFileParser {
    TorrentMetadata parseToMetadata(byte[] bytes, boolean computeInfoHash) throws MalformedTorrentFileException;

    Map<String, Object> parseToMap(byte[] bytes, boolean computeInfoHash) throws MalformedTorrentFileException;
}
