package com.robothaver.torrentfileparser.parser;

public interface InfoHashCalculator {
    String calculateInfoHash(byte[] infoBytes);
}
