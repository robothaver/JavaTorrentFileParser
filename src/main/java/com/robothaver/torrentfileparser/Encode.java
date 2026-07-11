package com.robothaver.torrentfileparser;

import com.robothaver.torrentfileparser.domain.TorrentMetadata;
import com.robothaver.torrentfileparser.encoder.TorrentEncoderImpl;
import com.robothaver.torrentfileparser.encoder.types.*;
import com.robothaver.torrentfileparser.exception.MalformedTorrentFileException;
import com.robothaver.torrentfileparser.parser.InfoHashCalculatorImpl;
import com.robothaver.torrentfileparser.parser.TorrentFileParserImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

public class Encode {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, MalformedTorrentFileException {
        Path path = Path.of("[nCore][game_iso]Forza.Horizon.6-RUNE.torrent");
        TorrentFileParserImpl torrentFileParser = new TorrentFileParserImpl();
        byte[] fileBytes = Files.readAllBytes(path);
        Map<String, Object> torrentMap = torrentFileParser.parseToMap(fileBytes, null);
        TorrentMetadata torrentMetadata = torrentFileParser.parseToMetadata(fileBytes, null);
        TorrentEncoderImpl torrentEncoder = new TorrentEncoderImpl();

        byte[] bytes = torrentEncoder.encodeTorrentMap(torrentMap);
        byte[] metadataBytes = torrentEncoder.encodeTorrentMetadata(torrentMetadata);

        System.out.println("Encoded map byte length: " + bytes.length);
        System.out.println("Metadata byte length: " + metadataBytes.length);
        System.out.println("Torrent file byte length: " + fileBytes.length);
        System.out.println("Bytes equal: " + Arrays.equals(bytes, fileBytes));
        Files.write(Path.of("encoded.torrent"), bytes);
        Files.write(Path.of("metadata.torrent"), metadataBytes);
    }
}
