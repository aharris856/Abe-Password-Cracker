package abe.password.cracker.hasher;

import abe.password.cracker.apclogger.APCLogger;
import abe.password.cracker.constants.HashType;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class APCHasher {

    private final APCLogger logger = new APCLogger(this.getClass().getSimpleName());

    public String getHash(String str, HashType hashType) {

        if(str == null || hashType == null) {
            return null;
        }

        switch (hashType) {
            case MD5:
                return getHash(str, "MD5");
            case SHA1:
                return getHash(str, "SHA-1");
            case SHA256:
                return getHash(str, "SHA-256");
            default:
                return null;
        }
    }

    private String getHash(String str, String hashAlgorithm) {

        try {

            MessageDigest messageDigest = MessageDigest.getInstance(hashAlgorithm);
            byte[] hashedByteArr = messageDigest.digest(str.getBytes("UTF-8"));
            return bytesToString(hashedByteArr);

        } catch (NoSuchAlgorithmException e) {
            logger.error("Error. Failed to determine hashing algorithm. : \"" + hashAlgorithm + "\" returning null.");
            return null;
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
            return null;
        }
    }

    private String bytesToString(byte[] bytes) {

        StringBuilder stringBuilder = new StringBuilder();

        for(byte by : bytes) {
            stringBuilder.append(
                    Integer.toString( (by&0xff) + 0x100, 16 )
                            .substring(1)
            );
        }

        return stringBuilder.toString();
    }
}
