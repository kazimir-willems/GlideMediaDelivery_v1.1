package delivery.com.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import delivery.com.consts.DBConsts;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME_PREFIX = "GLIDE_DB";
    private static final int DB_VERSION = 2;

    protected static String DESPATCH_TABLE_CREATE_SQL =
            "CREATE TABLE IF NOT EXISTS " + DBConsts.TABLE_NAME_DESPATCH + " (" +
                    DBConsts.FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DBConsts.FIELD_DESPATCH_ID + " TEXT," +
                    DBConsts.FIELD_RUN + " TEXT," +
                    DBConsts.FIELD_DRIVER + " TEXT," +
                    DBConsts.FIELD_ROUTE + " TEXT," +
                    DBConsts.FIELD_REG + " TEXT," +
                    DBConsts.FIELD_DATE + " TEXT," +
                    DBConsts.FIELD_COMPLETED + " INTEGER);";

    protected static String OUTLET_TABLE_CREATE_SQL =
            "CREATE TABLE IF NOT EXISTS " + DBConsts.TABLE_NAME_OUTLET + " (" +
                    DBConsts.FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DBConsts.FIELD_DESPATCH_ID + " TEXT," +
                    DBConsts.FIELD_OUTLET_ID + " TEXT," +
                    DBConsts.FIELD_OUTLET + " TEXT," +
                    DBConsts.FIELD_ADDRESS + " TEXT," +
                    DBConsts.FIELD_SERVICE + " TEXT," +
                    DBConsts.FIELD_DELIVERED + " INTEGER," +
                    DBConsts.FIELD_DELIVER_TIME + " TEXT," +
                    DBConsts.FIELD_TIERS + " INTEGER," +
                    DBConsts.FIELD_REASON + " INTEGER);";

    protected static String TIER_TABLE_CREATE_SQL =
            "CREATE TABLE IF NOT EXISTS " + DBConsts.TABLE_NAME_TIER + " (" +
                    DBConsts.FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DBConsts.FIELD_DESPATCH_ID + " TEXT," +
                    DBConsts.FIELD_OUTLET_ID + " TEXT," +
                    DBConsts.FIELD_TIER_NO + " TEXT," +
                    DBConsts.FIELD_TIER_ORDER + " INTEGER," +
                    DBConsts.FIELD_SLOTS + " INTEGER," +
                    DBConsts.FIELD_TIER_SPACE + " INTEGER);";

    protected static String STOCK_TABLE_CREATE_SQL =
            "CREATE TABLE IF NOT EXISTS " + DBConsts.TABLE_NAME_STOCK + " (" +
                    DBConsts.FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DBConsts.FIELD_DESPATCH_ID + " TEXT," +
                    DBConsts.FIELD_OUTLET_ID + " TEXT," +
                    DBConsts.FIELD_STOCK_ID + " TEXT," +
                    DBConsts.FIELD_STOCK + " TEXT," +
                    DBConsts.FIELD_TIER  + " TEXT," +
                    DBConsts.FIELD_SLOT + " INTEGER," +
                    DBConsts.FIELD_QTY + " INTEGER," +
                    DBConsts.FIELD_STATUS + " TEXT," +
                    DBConsts.FIELD_TITLE_ID + " TEXT," +
                    DBConsts.FIELD_SLOT_ORDER + " INTEGER," +
                    DBConsts.FIELD_SIZE + " TEXT," +
                    DBConsts.FIELD_REMOVE + " TEXT," +
                    DBConsts.FIELD_REMOVE_ID + " TEXT);";

    public DBHelper(Context context) {
        super(context, DB_NAME_PREFIX, null, DB_VERSION);
        this.getWritableDatabase().close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(DESPATCH_TABLE_CREATE_SQL);
            db.execSQL(OUTLET_TABLE_CREATE_SQL);
            db.execSQL(TIER_TABLE_CREATE_SQL);
            db.execSQL(STOCK_TABLE_CREATE_SQL);
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            onCreate(db);
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }
}
