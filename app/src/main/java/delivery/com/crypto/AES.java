package delivery.com.crypto;

import java.security.MessageDigest;
import java.security.Security;

public class AES {

    static {
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
    }

    public static byte[] generateSHA(byte[] bytes) {
        byte[] hash;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256", "SC");
            digest.update(bytes);

            hash = digest.digest();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return hash;
    }
}
