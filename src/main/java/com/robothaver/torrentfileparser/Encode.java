package com.robothaver.torrentfileparser;

import com.robothaver.torrentfileparser.domain.TorrentMetadata;
import com.robothaver.torrentfileparser.encoder.TorrentEncoderImpl;
import com.robothaver.torrentfileparser.exception.MalformedTorrentFileException;
import com.robothaver.torrentfileparser.parser.TorrentFileParserImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class Encode {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, MalformedTorrentFileException {
        Path path = Path.of("Path to torrent file");
        TorrentFileParserImpl torrentFileParser = new TorrentFileParserImpl();
        byte[] fileBytes = Files.readAllBytes(path);

        // Encode a map to bencoded byte array
        Map<String, Object> torrentMap = torrentFileParser.parseToMap(fileBytes, null);
        TorrentEncoderImpl torrentEncoder = new TorrentEncoderImpl();
        byte[] bytes = torrentEncoder.encodeTorrentMap(torrentMap);

        // Encode metadata object to bencoded byte array
        TorrentMetadata torrentMetadata = torrentFileParser.parseToMetadata(fileBytes, null);
        byte[] metadataBytes = torrentEncoder.encodeTorrentMetadata(torrentMetadata);
    }
}
