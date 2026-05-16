package com.robothaver.torrentfileparser.parser;

import com.robothaver.torrentfileparser.domain.Torrent;
import com.robothaver.torrentfileparser.exception.MalformedTorrentFileException;

import java.util.Map;

public interface TorrentFileParser {
    Torrent parseToTorrent(byte[] bytes, boolean computeInfoHash) throws MalformedTorrentFileException;

    Map<String, Object> parseToMap(byte[] bytes, boolean computeInfoHash) throws MalformedTorrentFileException;
}
