package delivery.com.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import delivery.com.consts.DBConsts;
import delivery.com.model.RemoveStockItem;
import delivery.com.model.TierItem;
import delivery.com.util.DBHelper;

public class RemoveStockDB extends DBHelper {
    private static final Object[] DB_LOCK 		= new Object[0];

    public RemoveStockDB(Context context) {
        super(context);
    }

    public ArrayList<RemoveStockItem> fetchAllRemoveStocksByOutletID(String outletID) {
        ArrayList<RemoveStockItem> ret = null;
        try {
            String szWhere = DBConsts.FIELD_OUTLET_ID + " = '" + outletID;
            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                Cursor cursor = db.query(DBConsts.TABLE_NAME_REMOVE_STOCK, null, szWhere, null, null, null, null);
                ret = createOutletBeans(cursor);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return ret;
    }

    public ArrayList<RemoveStockItem> fetchRemoveStocks(String despatchID, String outletID) {
        ArrayList<RemoveStockItem> ret = null;
        try {
            String szWhere = DBConsts.FIELD_DESPATCH_ID + " = '" + despatchID + "' AND " + DBConsts.FIELD_OUTLET_ID + " = '" + outletID + "'";
            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                Cursor cursor = db.query(DBConsts.TABLE_NAME_REMOVE_STOCK, null, szWhere, null, null, null, "size DESC, title_id DESC");
                ret = createOutletBeans(cursor);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return ret;
    }

    public long addRemoveStock(RemoveStockItem bean) {
        long ret = -1;
        try {
            ContentValues value = new ContentValues();
            value.put(DBConsts.FIELD_DESPATCH_ID, bean.getDespatchID());
            value.put(DBConsts.FIELD_OUTLET_ID, bean.getOutletID());
            value.put(DBConsts.FIELD_SIZE, bean.getSize());
            value.put(DBConsts.FIELD_TITLE_ID, bean.getTitleID());
            value.put(DBConsts.FIELD_TITLE, bean.getTitle());
            synchronized (DB_LOCK) {
                SQLiteDatabase db = getWritableDatabase();
                ret = db.insert(DBConsts.TABLE_NAME_REMOVE_STOCK, null, value);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return ret;
    }

    public void removeDatasByDespatchID(String despatchID) {
        try {
            String szWhere = DBConsts.FIELD_DESPATCH_ID + " = '" + despatchID + "'";

            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                db.delete(DBConsts.TABLE_NAME_REMOVE_STOCK, szWhere, null);
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
                db.delete(DBConsts.TABLE_NAME_REMOVE_STOCK, null, null);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    private ArrayList<RemoveStockItem> createOutletBeans(Cursor c) {
        ArrayList<RemoveStockItem> ret = null;
        try {
            ret = new ArrayList();

            final int COL_ID	            = c.getColumnIndexOrThrow(DBConsts.FIELD_ID),
                    COL_DESPATCH_ID 	    = c.getColumnIndexOrThrow(DBConsts.FIELD_DESPATCH_ID),
                    COL_OUTLET_ID         	= c.getColumnIndexOrThrow(DBConsts.FIELD_OUTLET_ID),
                    COL_SIZE                = c.getColumnIndexOrThrow(DBConsts.FIELD_SIZE),
                    COL_TITLE_ID   		    = c.getColumnIndexOrThrow(DBConsts.FIELD_TITLE_ID),
                    COL_TITLE               = c.getColumnIndexOrThrow(DBConsts.FIELD_TITLE);

            while (c.moveToNext()) {
                RemoveStockItem bean = new RemoveStockItem();
                bean.setDespatchID(c.getString(COL_DESPATCH_ID));
                bean.setOutletID(c.getString(COL_OUTLET_ID));
                bean.setSize(c.getString(COL_SIZE));
                bean.setTitleID(c.getString(COL_TITLE_ID));
                bean.setTitle(c.getString(COL_TITLE));
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
