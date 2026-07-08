package com.robothaver.torrentfileparser.encoder;

import com.robothaver.torrentfileparser.domain.TorrentMetadata;

import java.util.Map;

public interface TorrentEncoder {
    byte[] encodeTorrentMap(Map<String, Object> torrentMap);
    byte[] encodeTorrentMetadata(TorrentMetadata torrentMetadata);
}
