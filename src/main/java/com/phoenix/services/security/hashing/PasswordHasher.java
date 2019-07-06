package com.phoenix.services.security.hashing;

public class PasswordHasher extends Hasher {

    public PasswordHasher() {
        super();
    }


    public String hash(String word, String salt) throws IllegalArgumentException {

        //Assert not null
        if (word == null || salt == null) throw new IllegalArgumentException();

        String password_hash = super.hash(word);
        password_hash = super.hash(password_hash + salt);

        return password_hash;
    }
}
