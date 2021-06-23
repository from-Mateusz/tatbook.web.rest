package me.m92.tatbook_web.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class StrongEncodedKeyTool implements EncodedKeyTool {

    private final static int KEY_SIZE = 2048;

    private final static Logger LOGGER = LoggerFactory.getLogger(StrongEncodedKeyTool.class);

    @Override
    public KeyPair generate() {
        try {
            java.security.KeyPairGenerator keyPairGenerator = java.security.KeyPairGenerator.getInstance("RSA");
            SecureRandom rng = SecureRandom.getInstanceStrong();
            keyPairGenerator.initialize(KEY_SIZE, rng);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            return keyPair;
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }

    @Override
    public PublicKey recreatePublicKey(byte[] originalEncodedPublicKey) {
        try {
            EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(originalEncodedPublicKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(encodedKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            LOGGER.error("Could not recreate public key: ", ex);
            return null;
        }
    }

    @Override
    public PrivateKey recreatePrivateKey(byte[] originalEncodedPrivateKey) {
        try {
            EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(originalEncodedPrivateKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(encodedKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            LOGGER.error("Could not recreate private key: ", ex);
            return null;
        }
    }
}
