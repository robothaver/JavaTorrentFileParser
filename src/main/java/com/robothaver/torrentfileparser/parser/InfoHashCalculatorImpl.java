package com.robothaver.torrentfileparser.parser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class InfoHashCalculatorImpl implements InfoHashCalculator {
    private final MessageDigest messageDigest;

    public InfoHashCalculatorImpl() throws NoSuchAlgorithmException {
        this.messageDigest = MessageDigest.getInstance("SHA-1");
    }

    @Override
    public String calculateInfoHash(byte[] infoBytes) {
        return HexFormat.of().formatHex(messageDigest.digest(infoBytes));
    }
}
