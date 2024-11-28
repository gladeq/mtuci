package ru.mtuci.rbpomtuci2024.model;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

public class Ticket {

    private final Instant serverTime;
    private final long ticketLifetime; // in seconds
    private final LocalDate licenseActivationDate;
    private final LocalDate licenseExpirationDate;
    private final Long userId;
    private final String deviceId;
    private final boolean isLicenseBlocked;
    private String signature;


    public Ticket(LocalDate licenseActivationDate, LocalDate licenseExpirationDate, Long userId, String deviceId, boolean isLicenseBlocked) {
        this.serverTime = Instant.now();
        this.ticketLifetime = 300; // 5 minutes default
        this.licenseActivationDate = licenseActivationDate;
        this.licenseExpirationDate = licenseExpirationDate;
        this.userId = userId;
        this.deviceId = deviceId;
        this.isLicenseBlocked = isLicenseBlocked;
    }


    public void generateSignature(PrivateKey privateKey) throws SignatureException, InvalidKeyException {
        String data = this.toString(); // Строка для подписи
        Signature rsa = null; // Или другой алгоритм
        try {
            rsa = Signature.getInstance("SHA256withRSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        rsa.initSign(privateKey);
        rsa.update(data.getBytes());
        byte[] signatureBytes = rsa.sign();
        this.signature = Base64.getEncoder().encodeToString(signatureBytes);
    }


    public boolean verifySignature(PublicKey publicKey) throws SignatureException, InvalidKeyException {
        String data = this.toString(); // Строка для проверки
        Signature rsa = null; // Должен совпадать с алгоритмом подписи
        try {
            rsa = Signature.getInstance("SHA256withRSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        rsa.initVerify(publicKey);
        rsa.update(data.getBytes());
        return rsa.verify(Base64.getDecoder().decode(this.signature));
    }


    @Override
    public String toString() {
        return serverTime + "|" + ticketLifetime + "|" + licenseActivationDate + "|" + licenseExpirationDate + "|" + userId + "|" + deviceId + "|" + isLicenseBlocked;
    }


    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048); // Размер ключа
        return keyGen.generateKeyPair();
    }


    public static PrivateKey getPrivateKeyFromString(String privateKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpecPKCS8);

    }
    public static PublicKey getPublicKeyFromString(String publicKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyString));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(keySpecX509);
    }

}

