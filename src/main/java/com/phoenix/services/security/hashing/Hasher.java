package com.phoenix.services.security.hashing;

import com.phoenix.services.utility.ConversionUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class Hasher extends AbstractHashingServiceImpl {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(Hasher.class);

    private String hash_algorithm; //Hash function

    public Hasher() {
        super();
    }

    @Override
    public String hash(String word) {

        try {
             MessageDigest md = MessageDigest.getInstance(this.hash_algorithm);
             byte[] byte_hash = md.digest(word.getBytes());
             return ConversionUtility.bytesToHex(byte_hash);
        } catch (NoSuchAlgorithmException exc) {
            LOGGER.error("Hash algorithm " +this.hash_algorithm +" is not supported. Hash function didn't worked.");
            return word;
        }

    }

    public String generateSalt() {         return super.generateSalt();       }
    public int getSaltLength() {        return super.hash_length;    }

    public String getHashAlgorithm() {        return this.hash_algorithm;        }
    public void setHashAlgorithm(HashAlgorithms algorithm) {
        switch (algorithm) {
            case MD5:
                this.hash_algorithm = "MD5";
                super.hash_length = 16;
                break;
            case SHA_1:
                this.hash_algorithm = "SHA-1";
                super.hash_length = 20;
                break;
            case SHA_256:
                this.hash_algorithm = "SHA-256";
                super.hash_length = 32;
                break;
            case SHA_384:
                this.hash_algorithm = "SHA-384";
                super.hash_length = 48;
                break;
            case SHA_512:
                this.hash_algorithm = "SHA-512";
                super.hash_length = 64;
                break;
        }


    }



}
