package com.phoenix.services.security.hashing;

import com.phoenix.services.accounting.SigningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class HashingServiceFactoryBean implements FactoryBean<HashingService>, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(HashingServiceFactoryBean.class);
    private final Hasher hasher = new Hasher();

    public void setHashAlgorithm(HashAlgorithms algorithm) {
        LOGGER.debug("Set HashingService hash algorithm to " +algorithm.name());
        this.hasher.setHashAlgorithm(algorithm);
    }


    @Override
    public HashingService getObject() throws Exception {
        return this.hasher;
    }

    @Override
    public Class<?> getObjectType() {
        return SigningService.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.hasher.getHashAlgorithm() == null) {
            LOGGER.warn("HashingService bean will use default " +HashAlgorithms.SHA_512.name() +" algorithm.");
            this.hasher.setHashAlgorithm(HashAlgorithms.SHA_512);
        }
    }
}
