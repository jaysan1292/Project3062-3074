package com.jaysan1292.project.common.util;

import org.apache.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

/** @author Jason Recillo */
public class EncryptionUtils {
    private static final Logger log = Logger.getLogger(EncryptionUtils.class);
    private static final ConfigurablePasswordEncryptor ENCRYPTOR;
    private static final SimpleStringPBEConfig STRING_ENCRYPTOR_CONFIG;
    private static StandardPBEStringEncryptor stringEncryptor;

    static {
        ENCRYPTOR = new ConfigurablePasswordEncryptor();
        ENCRYPTOR.setStringOutputType("hexadecimal");
        ENCRYPTOR.setAlgorithm("SHA-256");
        ENCRYPTOR.setPlainDigest(true);

        STRING_ENCRYPTOR_CONFIG = new SimpleStringPBEConfig();
        STRING_ENCRYPTOR_CONFIG.setStringOutputType("base64");
    }

    private static void initializeStringEncryptor(char[] password) {
        stringEncryptor = new StandardPBEStringEncryptor();
        stringEncryptor.setConfig(STRING_ENCRYPTOR_CONFIG);
        stringEncryptor.setPasswordCharArray(password);
    }

    public static String encryptPassword(String password) {
        return ENCRYPTOR.encryptPassword(password);
    }

    public static ConfigurablePasswordEncryptor getPasswordEncryptor() {
        return ENCRYPTOR;
    }
}
