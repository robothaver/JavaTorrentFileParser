package com.robothaver.torrentfileparser.parser;

public interface InfoHashCalculator {
    String getInfoHash(byte[] infoBytes);
}
