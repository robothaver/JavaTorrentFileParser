package com.robothaver.torrentfileparser.encoder;

import java.util.Map;

public interface TorrentEncoder {
    byte[] encodeTorrentMap(Map<String, Object> torrentMap);
}
