package com.phoenix.services.security.hashing;

public enum HashAlgorithms {

    /**
     * 16 bytes (128 bits) hash length
     */
    MD5,

    /**
     * 20 bytes (160 bits) hash length
     */
    SHA_1,

    /**
     * 32 bytes (256 bits) hash length
     */
    SHA_256,

    /**
     * 48 bytes (384 bits) hash length
     */
    SHA_384,

    /**
     * 64 bytes (512 bits) hash length
     */
    SHA_512
}
