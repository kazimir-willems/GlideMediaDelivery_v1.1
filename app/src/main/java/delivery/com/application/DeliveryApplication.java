package delivery.com.application;

import android.app.Application;

import java.util.ArrayList;

import delivery.com.model.StockItem;

/**
 * Created by Caesar on 5/4/2017.
 */

public class DeliveryApplication extends Application {
    private static DeliveryApplication instance;

    private static boolean bLoginStatus = false;
    public static int nAccess = 0;
    public static String staffID = "";
    public static String passcode = "";

    public static ArrayList<StockItem> operationLists = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static synchronized DeliveryApplication getInstance() {
        return instance;
    }
}
