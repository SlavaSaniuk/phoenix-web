package com.phoenix.unittests.servicestest;

import com.phoenix.services.utility.ConversionUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;

import org.junit.jupiter.params.provider.ValueSource;

class ConversionUtilityTestCase {

    @RepeatedTest(3)
    @ParameterizedTest
    @ValueSource(strings = {"Hello", "password", "test"})
    void bytesToHex_byteArray_shouldConvertToHexString(String test) {
        Assertions.assertNotNull(ConversionUtility.bytesToHex(test.getBytes()));
    }
}
