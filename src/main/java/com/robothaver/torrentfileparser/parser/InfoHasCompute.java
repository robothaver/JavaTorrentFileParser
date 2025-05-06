package com.robothaver.torrentfileparser.parser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class InfoHasCompute {
    private static final MessageDigest messageDigest;

    static {
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    private InfoHasCompute() {
        throw new IllegalStateException("Utility class");
    }

    public static String getInfoHash(byte[] infoBytes) {
        return byteArrayToHex(messageDigest.digest(infoBytes));
    }

    private static String byteArrayToHex(byte[] bytes) {
        HexFormat hex = HexFormat.of();
        return hex.formatHex(bytes);
    }
}
