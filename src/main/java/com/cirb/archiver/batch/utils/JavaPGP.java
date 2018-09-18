package com.cirb.archiver.batch.utils;

import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * An implementation of PGP encryption (PGP = Pretty Good Privacy)
 */
public final class JavaPGP {

    /**
     * The message is created like so:
     *    - Generates a random KeyPair
     *    - Encrypt the message with the private key from the generated key pair
     *    - Encrypt the generated public key with given public key
     *
     * @param message The message to encrypt
     * @param key The key to encrypt with
     * @return The encrypted message
     * @throws GeneralSecurityException
     */
    public static byte[] encrypt(byte[] message, PublicKey key) throws GeneralSecurityException {
        KeyPair pair = generateKeyPair();
        PrivateKey privateKey = pair.getPrivate();

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        byte[] encryptedMessage = cipher.doFinal(message);

        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptedPublicKey = cipher.doFinal(pair.getPublic().getEncoded());

        ByteBuffer buffer = ByteBuffer.allocate((encryptedPublicKey.length + encryptedMessage.length) + 4);
        buffer.putInt(encryptedPublicKey.length);
        buffer.put(encryptedPublicKey);
        buffer.put(encryptedMessage);
        return buffer.array();
    }

    /**
     * The message is decrypted like so:
     *    - Read the encrypted public key
     *    - Decrypt the public key with the private key
     *    - Read the encrypted message
     *    - Use the decrypted public key to decrypt the encrypted message
     *    
     * @param message The encrypted message
     * @param key The private key
     * @return The decrypted message
     * @throws GeneralSecurityException
     */
    public static byte[] decrypt(byte[] message, PrivateKey key) throws GeneralSecurityException {
        ByteBuffer buffer = ByteBuffer.wrap(message);
        int keyLength = buffer.getInt();
        byte[] encyptedPublicKey = new byte[keyLength];
        buffer.get(encyptedPublicKey);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] encodedPublicKey = cipher.doFinal(encyptedPublicKey);

        PublicKey publicKey = getPublicKey(encodedPublicKey);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        byte[] encryptedMessage = new byte[buffer.remaining()];
        buffer.get(encryptedMessage);

        return cipher.doFinal(encryptedMessage);
    }


    public static PublicKey getPublicKey(byte[] encodedKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory factory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(encodedKey);
        return factory.generatePublic(encodedKeySpec);
    }

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024, SecureRandom.getInstance("SHA1PRNG"));
        return keyPairGenerator.generateKeyPair();
    }

    private JavaPGP() {
    	
    }
    
}