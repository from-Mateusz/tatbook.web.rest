package me.m92.tatbook_web.security.jwt;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface EncodedKeyTool {
    KeyPair generate();
    PublicKey recreatePublicKey(byte[] originalEncodedPublicKey);
    PrivateKey recreatePrivateKey(byte[] originalEncodedPrivateKey);
}
