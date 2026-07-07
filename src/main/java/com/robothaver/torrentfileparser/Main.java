package com.robothaver.torrentfileparser;

import com.robothaver.torrentfileparser.domain.TorrentMetadata;
import com.robothaver.torrentfileparser.exception.MalformedTorrentFileException;
import com.robothaver.torrentfileparser.parser.InfoHashCalculatorImpl;
import com.robothaver.torrentfileparser.parser.TorrentFileParser;
import com.robothaver.torrentfileparser.parser.TorrentFileParserImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) throws IOException, MalformedTorrentFileException, NoSuchAlgorithmException {
        Path path = Path.of("Path to torrent file");
        byte[] bytes = Files.readAllBytes(path);
        Instant start = Instant.now();

        TorrentFileParser torrentParser = new TorrentFileParserImpl();
        TorrentMetadata torrentMetadata = torrentParser.parseToMetadata(bytes, new InfoHashCalculatorImpl());
        Instant finish = Instant.now();

        System.out.println(torrentMetadata.getName());
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println("Parsing finished in " + timeElapsed + "ms");
    }
}