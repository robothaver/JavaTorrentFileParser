package com.robothaver.torrentfileparser.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class InfoHasCompute {
    public static String getInfoHash(byte[] infoBytes) {
        try {
            return getSHAsum(infoBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getSHAsum(byte[] input) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        return byteArrayToHex(messageDigest.digest(input));
    }

    private static String byteArrayToHex(byte[] bytes) {
        HexFormat hex = HexFormat.of();
        return hex.formatHex(bytes);
    }
}
