package com.robothaver.torrentfileparser;

import com.robothaver.torrentfileparser.domain.Torrent;
import com.robothaver.torrentfileparser.exception.MalformedTorrentFileException;
import com.robothaver.torrentfileparser.parser.TorrentFileParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        Path path = Paths.get("Path to torrent file");
        try {
            byte[] bytes = Files.readAllBytes(path);
            Instant start = Instant.now();

            TorrentFileParser torrentParser = new TorrentFileParser(bytes, true);
            Torrent torrent = torrentParser.parseToTorrent();
            System.out.println(torrent.getName());

            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            System.out.println("Parsing finished in " + timeElapsed + "ms");
        } catch (IOException | MalformedTorrentFileException e) {
            throw new RuntimeException(e);
        }
    }
}