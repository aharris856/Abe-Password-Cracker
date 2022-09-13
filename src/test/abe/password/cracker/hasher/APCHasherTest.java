package test.abe.password.cracker.hasher;

import abe.password.cracker.constants.HashType;
import abe.password.cracker.hasher.APCHasher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class APCHasherTest {

    private APCHasher hasher = new APCHasher();
    private String originalStr = "th!s !$ @ t3st str|ng";
    private String md5Expected = "4cba172f4161769fe642ff68edc9aa93";
    private String sha1Expected = "47b386ee2345a88e75cc2b9d76de6145803a3b30";
    private String sha256Expected = "c237e941c053e365a852b8894500057247ebc1c5463364483508a65e31fe63d0";

    @Test
    void getHashNullString() {
        assertNull(hasher.getHash(null, HashType.MD5));
    }

    @Test
    void getHashNullType() {
        assertNull(hasher.getHash(originalStr, null));
    }

    @Test
    void getHashMD5() {
        assertEquals(hasher.getHash(originalStr, HashType.MD5), md5Expected);
    }

    @Test
    void getHashSHA1() {
        assertEquals(hasher.getHash(originalStr, HashType.SHA1), sha1Expected);
    }

    @Test
    void getHashSHA256() {
        assertEquals(hasher.getHash(originalStr, HashType.SHA256), sha256Expected);
    }
}