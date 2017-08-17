package delivery.com.util;

/**
 * Created by Caesar on 4/21/2017.
 */

public class URLManager {
    public static String getDownloadDespatchURL() {
        return "http://glideadmin.co.uk/json/delivery-download.php";
    }

    public static String getUploadDespatchURL() {
//        return "http://glideadmin.co.uk/json/deliverySend.php";
        return "http://glideadmin.co.uk/json/deliveryRetrieve.php";
    }

    public static String getLoginURL() {
        return "http://www.glideadmin.co.uk/json/delivery-login.php";
    }
}