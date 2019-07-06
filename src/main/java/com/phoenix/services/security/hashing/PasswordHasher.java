package com.phoenix.services.security.hashing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

@Deprecated
public class PasswordHasher extends Hasher implements InitializingBean {

    //LOGGER
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordHasher.class);

    public PasswordHasher() {
        super();
    }


    public String hash(String word, String salt) throws IllegalArgumentException {

        //Assert not null
        if (word == null) throw new IllegalArgumentException();
        if (salt == null) salt = super.generateSalt();

        //Generate hash for password
        String password_hash = super.hash(word);
        password_hash = super.hash(password_hash + salt); //Generate hash from password and salt

        //Return
        return password_hash;
    }

    @Override
    public String hash(String word) {
        return this.hash(word, super.generateSalt());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (super.getHashAlgorithm() == null) {
            LOGGER.warn("Hash algorithm for " +getClass().getName() +" is not set. Application will use default " +HashAlgorithms.SHA_1 +" algorithm");
            super.setHashAlgorithm(HashAlgorithms.SHA_1);
        }
    }

}
