package delivery.com.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import delivery.com.consts.DBConsts;
import delivery.com.consts.StateConsts;
import delivery.com.model.DespatchItem;
import delivery.com.util.DBHelper;

public class DespatchDB extends DBHelper {
    private static final Object[] DB_LOCK 		= new Object[0];

    public DespatchDB(Context context) {
        super(context);
    }

    public ArrayList<DespatchItem> fetchAllDespatches() {
        ArrayList<DespatchItem> ret = null;
        try {
            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                Cursor cursor = db.query(DBConsts.TABLE_NAME_DESPATCH, null, null, null, null, null, null);
                ret = createDespatchBeans(cursor);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return ret;
    }

    public ArrayList<DespatchItem> fetchCompletedDespatches() {
        ArrayList<DespatchItem> ret = null;
        try {
            String szWhere = DBConsts.FIELD_COMPLETED + " = " + StateConsts.DESPATCH_COMPLETED;

            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                Cursor cursor = db.query(DBConsts.TABLE_NAME_DESPATCH, null, szWhere, null, null, null, null);
                ret = createDespatchBeans(cursor);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return ret;
    }

    public boolean isExist(DespatchItem item) {
        boolean bExist = false;
        try {
            String szWhere = DBConsts.FIELD_DESPATCH_ID + " = '" + item.getDespatchId() + "'";

            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                Cursor cursor = db.query(DBConsts.TABLE_NAME_DESPATCH, null, szWhere, null, null, null, null);
                if(cursor.moveToNext())
                    bExist = true;
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return bExist;
    }

    public long addDespatch(DespatchItem bean) {
        long ret = -1;
        try {
            ContentValues value = new ContentValues();
            value.put(DBConsts.FIELD_DESPATCH_ID, bean.getDespatchId());
            value.put(DBConsts.FIELD_RUN, bean.getRunId());
            value.put(DBConsts.FIELD_DRIVER, bean.getDriverName());
            value.put(DBConsts.FIELD_DATE, bean.getCreationDate());
            value.put(DBConsts.FIELD_ROUTE, bean.getRoute());
            value.put(DBConsts.FIELD_REG, bean.getReg());
            value.put(DBConsts.FIELD_COMPLETED, bean.getCompleted());
            synchronized (DB_LOCK) {
                SQLiteDatabase db = getWritableDatabase();
                ret = db.insert(DBConsts.TABLE_NAME_DESPATCH, null, value);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return ret;
    }

    public void updateDespatch(DespatchItem item) {
        try {
            String szWhere = DBConsts.FIELD_DESPATCH_ID + " = '" + item.getDespatchId() + "'";
            ContentValues value = new ContentValues();
            value.put(DBConsts.FIELD_COMPLETED, item.getCompleted());

            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                db.update(DBConsts.TABLE_NAME_DESPATCH, value, szWhere, null);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    public void removeDespatch(DespatchItem item) {
        try {
            String szWhere = DBConsts.FIELD_DESPATCH_ID + " = '" + item.getDespatchId() + "'";

            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                db.delete(DBConsts.TABLE_NAME_DESPATCH, szWhere, null);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    public void removeAllDatas() {
        try {
            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                db.delete(DBConsts.TABLE_NAME_DESPATCH, null, null);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    private ArrayList<DespatchItem> createDespatchBeans(Cursor c) {
        ArrayList<DespatchItem> ret = null;
        try {
            ret = new ArrayList();

            final int COL_ID	            = c.getColumnIndexOrThrow(DBConsts.FIELD_ID),
                    COL_DESPATCH_ID 	    = c.getColumnIndexOrThrow(DBConsts.FIELD_DESPATCH_ID),
                    COL_RUN         		= c.getColumnIndexOrThrow(DBConsts.FIELD_RUN),
                    COL_DRIVER   		    = c.getColumnIndexOrThrow(DBConsts.FIELD_DRIVER),
                    COL_DATE    	 	    = c.getColumnIndexOrThrow(DBConsts.FIELD_DATE),
                    COL_ROUTE               = c.getColumnIndexOrThrow(DBConsts.FIELD_ROUTE),
                    COL_REG                 = c.getColumnIndexOrThrow(DBConsts.FIELD_REG),
                    COL_COMPLETED 		    = c.getColumnIndexOrThrow(DBConsts.FIELD_COMPLETED);

            while (c.moveToNext()) {
                DespatchItem bean = new DespatchItem();
                bean.setDespatchId(c.getString(COL_DESPATCH_ID));
                bean.setRunId(c.getString(COL_RUN));
                bean.setDriverName(c.getString(COL_DRIVER));
                bean.setCreationDate(c.getString(COL_DATE));
                bean.setRoute(c.getString(COL_ROUTE));
                bean.setReg(c.getString(COL_REG));
                bean.setCompleted(c.getInt(COL_COMPLETED));
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
