package delivery.com.consts;

/**
 * Created by Caesar on 4/21/2017.
 */

public class StateConsts {
    public static final int USER_ADMIN = 0;        //admin
    public static final int USER_DRIVER = 1;        //driver
    public static final int DESPATCH_DEFAULT = 0;
    public static final int DESPATCH_COMPLETED = 1;

    public static final int OUTLET_NOT_DELIVERED = 0;
    public static final int OUTLET_DELIVERED = 1;
    public static final int OUTLET_CANNOT_DELIVER = 2;

    public static final int STOCK_QTY_FULL = 1;
    public static final int STOCK_QTY_RESTOCK = 2;
    public static final int STOCK_QTY_NONE = 3;
    public static final int STOCK_QTY_MISSING = 4;

}
