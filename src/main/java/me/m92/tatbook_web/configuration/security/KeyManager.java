package me.m92.tatbook_web.configuration.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import me.m92.tatbook_web.configuration.security.utils.Moment;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;
import java.util.function.Function;

public class KeyManager {

    public KeyPair generate(int keySize) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            SecureRandom rng = SecureRandom.getInstanceStrong();
            keyPairGenerator.initialize(keySize, rng);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            return keyPair;
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }

//    public static void main(final String...args) throws Exception {
//        long keyPairWithKeySize1024GenerationTime = getOperationTimeSpeed((keyManager) -> {
//            Calendar calendar = Calendar.getInstance();
//            keyManager.generate(1024);
//            long end = calendar.getTimeInMillis();
//            return end;
//        });
//
//        long keyPairWithKeySize2048GenerationTime = getOperationTimeSpeed((keyManager) -> {
//            Calendar calendar = Calendar.getInstance();
//            keyManager.generate(2048);
//            long end = calendar.getTimeInMillis();
//            return end;
//        });
//
//        long encryptionWithKeySize2048GenerationTime = getOperationTimeSpeed((keyManager) -> {
//            long end = System.nanoTime();
//            KeyPair keyPair = keyManager.generate(2048);
//            PrivateKey privateKey = keyPair.getPrivate();
//            Moment expireMoment = Moment.getInstance().delayByMinutes(10L);
//            String digest = JWT.create().withIssuer("test")
//                    .withSubject("test")
//                    .withExpiresAt(expireMoment.getDate())
//                    .withClaim("role", "test")
//                    .sign(Algorithm.RSA256((RSAPublicKey) keyPair.getPublic(), (RSAPrivateKey) keyPair.getPrivate()));
//            if(null != digest) {
//                end = System.nanoTime();
//            }
//            return end;
//        });
//
//        long encryptionWithKeySize1024GenerationTime = getOperationTimeSpeed((keyManager) -> {
//            long end = System.nanoTime();
//            KeyPair keyPair = keyManager.generate(1024);
//            PrivateKey privateKey = keyPair.getPrivate();
//            Moment expireMoment = Moment.getInstance().delayByMinutes(10L);
//            String digest = JWT.create().withIssuer("test")
//                    .withSubject("test")
//                    .withExpiresAt(expireMoment.getDate())
//                    .withClaim("role", "test")
//                    .sign(Algorithm.RSA256((RSAPublicKey) keyPair.getPublic(), (RSAPrivateKey) keyPair.getPrivate()));
//            if(null != digest) {
//                end = System.nanoTime();
//            }
//            return end;
//        });
//
//        KeyManager keyManager = new KeyManager();
//        KeyPair keyPair = keyManager.generate(2048);
//        PrivateKey privateKey = keyPair.getPrivate();
//        JWTVerifier jwtVerifier = JWT.require(Algorithm.RSA256((RSAPublicKey) keyPair.getPublic(), null)).build();
//        Moment expireMoment = Moment.getInstance().delayByMinutes(10L);
//        String token = JWT.create().withIssuer("test")
//                .withSubject("test")
//                .withExpiresAt(expireMoment.getDate())
//                .withClaim("role", "tattooist")
//                .sign(Algorithm.RSA256((RSAPublicKey) keyPair.getPublic(), (RSAPrivateKey) keyPair.getPrivate()));
//        EncodedKeySpec rsaPublicKeySpec = new X509EncodedKeySpec(keyPair.getPublic().getEncoded());
//        EncodedKeySpec rsaPrivateKeySpec = new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded());
//
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        PublicKey originalPublicKey = keyPair.getPublic();
//        PublicKey recreatedPublicKey = keyFactory.generatePublic(rsaPublicKeySpec);
//
//
//        System.out.println("Signed JWT token: " + token);
//        DecodedJWT decodedJWT = jwtVerifier.verify(token);
//        System.out.println("Decrypted JWT token conveyed role: " + decodedJWT.getClaim("role").asString());
//        System.out.println("Recreated public key is the same sa original: " + originalPublicKey.equals(recreatedPublicKey));
//    }
//
//    private static long getOperationTimeSpeed(Function<KeyManager, Long> operation) {
//        long start = System.nanoTime();
//        long end = operation.apply(new KeyManager());
//        return end - start;
//    }
}
