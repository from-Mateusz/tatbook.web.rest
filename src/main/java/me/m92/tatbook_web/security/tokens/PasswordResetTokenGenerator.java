package me.m92.tatbook_web.security.tokens;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.StringJoiner;

public class PasswordResetTokenGenerator implements TokenGenerator<PasswordResetToken> {

    private static SecureRandom PRNG;

    static {
        try {
            PRNG = SecureRandom.getInstanceStrong();
        }catch (NoSuchAlgorithmException ex) {
            PRNG = new SecureRandom();
        }
    }

    @Override
    public PasswordResetToken generate(Object... extras) {
        Base64.Encoder urlEncoder = Base64.getUrlEncoder().withoutPadding();

        byte[] salt = new byte[30];
        PRNG.nextBytes(salt);
        StringJoiner hexSaltJoiner = new StringJoiner("");
        for(byte saltByte : salt) {
            hexSaltJoiner.add(String.format("%02x", saltByte));
        }

        StringJoiner combinedSalt = new StringJoiner("");
        for(Object extra : extras) {
            if(null != extra) {
                combinedSalt.add(extra.toString());
            }
        }

        combinedSalt.add(hexSaltJoiner.toString());
        combinedSalt.add("" + System.nanoTime());
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] tokenBytes = messageDigest.digest(combinedSalt.toString().getBytes());
            return PasswordResetToken.of(String.valueOf(urlEncoder.encodeToString(tokenBytes)));
        } catch (NoSuchAlgorithmException ex) {
            return PasswordResetToken.of(String.valueOf(urlEncoder.encodeToString(combinedSalt.toString().getBytes())));
        }
    }
}
