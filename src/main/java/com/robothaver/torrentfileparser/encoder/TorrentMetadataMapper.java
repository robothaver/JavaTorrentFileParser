package com.robothaver.torrentfileparser.encoder;

import com.robothaver.torrentfileparser.domain.TorrentMetadata;

import java.util.Map;

public interface TorrentMetadataMapper {
    Map<String, Object> metadataToMap(TorrentMetadata torrentMetadata);
}
