package delivery.com.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import delivery.com.application.DeliveryApplication;
import delivery.com.consts.DBConsts;
import delivery.com.model.ClockItem;
import delivery.com.model.RemoveStockItem;
import delivery.com.util.DBHelper;

public class ClockDB extends DBHelper {
    private static final Object[] DB_LOCK 		= new Object[0];

    public ClockDB(Context context) {
        super(context);
    }

    public ArrayList<ClockItem> fetchAllClockItem() {
        ArrayList<ClockItem> ret = null;
        try {

            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                Cursor cursor = db.query(DBConsts.TABLE_NAME_CLOCK, null, null, null, null, null, "clock_time DESC", "10");
                ret = createClockBeans(cursor);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return ret;
    }

    public ArrayList<ClockItem> fetchAll() {
        ArrayList<ClockItem> ret = null;
        try {

            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                Cursor cursor = db.query(DBConsts.TABLE_NAME_CLOCK, null, null, null, null, null, "clock_time DESC");
                ret = createClockBeans(cursor);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return ret;
    }

    public void removeAllDatas() {
        try {
            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                db.delete(DBConsts.TABLE_NAME_CLOCK, null, null);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    public long addClock(ClockItem bean) {
        long ret = -1;
        try {
            ContentValues value = new ContentValues();
            value.put(DBConsts.FIELD_STAFF_ID, bean.getStaffID());
            value.put(DBConsts.FIELD_CLOCK_TIME, bean.getTimeStamp());
            value.put(DBConsts.FIELD_CLOCK_STATUS, bean.getClockStatus());
            synchronized (DB_LOCK) {
                SQLiteDatabase db = getWritableDatabase();
                ret = db.insert(DBConsts.TABLE_NAME_CLOCK, null, value);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return ret;
    }

    private ArrayList<ClockItem> createClockBeans(Cursor c) {
        ArrayList<ClockItem> ret = null;
        try {
            ret = new ArrayList();

            final int COL_ID	            = c.getColumnIndexOrThrow(DBConsts.FIELD_ID),
                    COL_STAFF_ID            = c.getColumnIndexOrThrow(DBConsts.FIELD_STAFF_ID),
                    COL_CLOCK_TIME   	    = c.getColumnIndexOrThrow(DBConsts.FIELD_CLOCK_TIME),
                    COL_CLOCK_STATUS       	= c.getColumnIndexOrThrow(DBConsts.FIELD_CLOCK_STATUS);

            while (c.moveToNext()) {
                ClockItem bean = new ClockItem();
                bean.setStaffID(c.getString(COL_STAFF_ID));
                bean.setTimeStamp(c.getString(COL_CLOCK_TIME));
                bean.setClockStatus(c.getString(COL_CLOCK_STATUS));
                ret.add(bean);
            }

            c.close();
            getReadableDatabase().close();
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return ret;
    }
}
