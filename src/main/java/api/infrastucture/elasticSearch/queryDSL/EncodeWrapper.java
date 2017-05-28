package api.infrastucture.elasticSearch.queryDSL;

import api.domain.infrastructure.ConfigRepository;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

import static java.util.Base64.getEncoder;

public class EncodeWrapper {

    private static final String ENCODER_MESSAGES_KEY = "encoderMessages";
    private static final int MINIM = 16;
    private static final char CHAR = '-';

    @Inject
    private ConfigRepository configRepository;

    @Inject
    public EncodeWrapper(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    private SecretKey generateKey()
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return new SecretKeySpec(getSecretKeyBytes(), "AES");
    }

    private byte[] getSecretKeyBytes() {
        return this.configRepository.get(ENCODER_MESSAGES_KEY).getBytes();
    }

    private byte[] encryptMsg(String message, SecretKey secret)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        Cipher cipher;
        cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        return cipher.doFinal(message.getBytes("UTF-8"));
    }

    private String packageToSend(byte[] encrypt) {
        java.util.Base64.Encoder encoder = getEncoder();
        return new String(encoder.encode(encrypt));
    }

    public String encode(String text) {
        try {
            return this.packageToSend(
                    this.encryptMsg(
                            getText(text),
                            this.generateKey()
                    )
            );
        } catch (Exception exception) {
            return null;
        }
    }

    private String getText(String text) {
        if (text.length() < MINIM) {
            int number = MINIM - text.length();

            char[] repeat = new char[number];
            Arrays.fill(repeat, CHAR);
            text += new String(repeat);
        }

        return text;
    }

}
