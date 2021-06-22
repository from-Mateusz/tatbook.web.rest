package me.m92.tatbook_web.configuration.security.tokens;

import io.netty.handler.codec.base64.Base64Encoder;
import me.m92.tatbook_web.configuration.security.passwords.BCryptPasswordProtector;
import me.m92.tatbook_web.core.models.EmailAddress;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.StringJoiner;

public class EmailAddressConfirmationTokenGenerator implements TokenGenerator<EmailAddressConfirmationToken> {

    private static SecureRandom PRNG;

    static {
        try {
            PRNG = SecureRandom.getInstanceStrong();
        }catch (NoSuchAlgorithmException ex) {
            PRNG = new SecureRandom();
        }
    }

    @Override
    public EmailAddressConfirmationToken generate(Object... extras) {
        Base64.Encoder urlEncoder = Base64.getUrlEncoder().withoutPadding();

        byte[] salt = new byte[20];
        PRNG.nextBytes(salt);
        StringJoiner hexSaltJoiner = new StringJoiner("");
        for(byte saltByte : salt) {
            hexSaltJoiner.add(String.format("%02x", saltByte));
        }

        StringJoiner combinedSalt = new StringJoiner("");
        if(0 != extras.length) {
            for(Object extra : extras) {
                if(null != extra) {
                    combinedSalt.add(extra.toString());
                }
            }
        }

        combinedSalt.add(hexSaltJoiner.toString());
        combinedSalt.add("" + System.nanoTime());
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] tokenBytes = messageDigest.digest(combinedSalt.toString().getBytes());
            return EmailAddressConfirmationToken.of(String.valueOf(urlEncoder.encodeToString(tokenBytes)));
        } catch (NoSuchAlgorithmException ex) {
            return EmailAddressConfirmationToken.of(String.valueOf(urlEncoder.encodeToString(combinedSalt.toString().getBytes())));
        }
    }

    public static void main(String...args) {
        EmailAddressConfirmationTokenGenerator tokenGenerator = new EmailAddressConfirmationTokenGenerator();
        EmailAddressConfirmationToken token = tokenGenerator.generate();
        BCryptTokenProtector tokenProtector = new BCryptTokenProtector();
        EmailAddressConfirmationToken protectedToken = tokenProtector.protect(token);
        System.out.println("Unprotected token: " + token);
        System.out.println("Protected token: " + protectedToken);
    }
}
