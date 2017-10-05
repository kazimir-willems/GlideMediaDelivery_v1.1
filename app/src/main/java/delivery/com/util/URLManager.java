package delivery.com.util;

/**
 * Created by Caesar on 4/21/2017.
 */

public class URLManager {
    public static String getDownloadDespatchURL() {
        //return "http://glideadmin.co.uk/json/delivery-download.php";
        return "http://glideadmin.co.uk/json/delivery-download-dev.php";
    }

    public static String getUploadDespatchURL() {
//        return "http://glideadmin.co.uk/json/deliverySend.php";
        return "http://glideadmin.co.uk/json/delivery-upload.php";
    }

    public static String getLoginURL() {
        return "http://www.glideadmin.co.uk/json/delivery-login.php";
    }

    public static String getClockURL() {
        return "http://www.glideadmin.co.uk/json/delivery-clock.php";
    }

    public static String getClockHistoryURL() {
        return "http://www.glideadmin.co.uk/json/delivery-clock-history.php";
    }
}