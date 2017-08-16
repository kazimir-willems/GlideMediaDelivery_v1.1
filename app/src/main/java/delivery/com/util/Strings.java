package delivery.com.util;

/**
 * Created by newfund on 2017/02/08.
 */

public class Strings {

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static String stringify(byte[] bytes) {
        return stringify(new String(bytes));
    }

    private static String stringify(String str) {
        String aux = "";
        for (int i = 0; i < str.length(); i++) {
            aux += str.charAt(i);
        }
        return aux;
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

}
