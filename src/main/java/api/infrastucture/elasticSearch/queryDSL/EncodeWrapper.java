package api.infrastucture.elasticSearch.queryDSL;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

import static java.util.Base64.getEncoder;

public class EncodeWrapper {

    private static final int MINIM = 16;
    private static final String SECRET_KEY = "1263472456324561";
    private String text;

    private EncodeWrapper(String text) {
        this.text = text;
    }

    public static String Encoder(String text) {
        return new EncodeWrapper(text).encode();
    }

    private SecretKey generateKey()
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
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

    private String encode() {
        try {
            return this.packageToSend(
                    this.encryptMsg(
                            getText(),
                            this.generateKey()
                    )
            );
        } catch (Exception exception) {
            return null;
        }
    }

    private String getText() {
        if (this.text.length() < MINIM) {
            char add = '-';
            int number = MINIM - this.text.length();

            char[] repeat = new char[number];
            Arrays.fill(repeat, add);
            this.text += new String(repeat);
        }

        return this.text;
    }

}
