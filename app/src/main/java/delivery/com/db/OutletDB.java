package delivery.com.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import delivery.com.consts.DBConsts;
import delivery.com.model.OutletItem;
import delivery.com.util.DBHelper;

public class OutletDB extends DBHelper {
    private static final Object[] DB_LOCK 		= new Object[0];

    public OutletDB(Context context) {
        super(context);
    }

    public ArrayList<OutletItem> fetchOutletsByDespatchID(String despatchID) {
        ArrayList<OutletItem> ret = null;
        try {
            String szWhere = DBConsts.FIELD_DESPATCH_ID + " = '" + despatchID + "'";

            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                Cursor cursor = db.query(DBConsts.TABLE_NAME_OUTLET, null, szWhere, null, null, null, null);
                ret = createOutletBeans(cursor);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return ret;
    }

    public int getAllCount(String despatchID) {
        int count = 0;
        try {
            String szWhere = DBConsts.FIELD_DESPATCH_ID + " = '" + despatchID + "'";

            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                Cursor cursor = db.query(DBConsts.TABLE_NAME_OUTLET, null, szWhere, null, null, null, null);
                count = cursor.getCount();
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return count;
    }

    public int getCompletedCount(String despatchID) {
        int count = 0;
        try {
            String szWhere = DBConsts.FIELD_DESPATCH_ID + " = '" + despatchID + "' AND " + DBConsts.FIELD_DELIVERED + " <> 0";

            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                Cursor cursor = db.query(DBConsts.TABLE_NAME_OUTLET, null, szWhere, null, null, null, null);
                count = cursor.getCount();
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return count;
    }

    public long addOutlet(OutletItem bean) {
        long ret = -1;
        try {
            ContentValues value = new ContentValues();
            value.put(DBConsts.FIELD_DESPATCH_ID, bean.getDespatchId());
            value.put(DBConsts.FIELD_OUTLET_ID, bean.getOutletId());
            value.put(DBConsts.FIELD_ORDER_ID, bean.getOrderId());
            value.put(DBConsts.FIELD_OUTLET, bean.getOutlet());
            value.put(DBConsts.FIELD_ADDRESS, bean.getAddress());
            value.put(DBConsts.FIELD_SERVICE, bean.getServiceType());
            value.put(DBConsts.FIELD_SERVICE_ID, bean.getServiceId());
            value.put(DBConsts.FIELD_DELIVERED, bean.getDelivered());
            value.put(DBConsts.FIELD_DELIVER_TIME, bean.getDeliveredTime());
            value.put(DBConsts.FIELD_TIERS, bean.getTiers());
            value.put(DBConsts.FIELD_ORDER_TYPE, bean.getOrderType());
            value.put(DBConsts.FIELD_ORDER_TYPE_DISPLAY, bean.getOrderTypeDisplay());
            value.put(DBConsts.FIELD_REASON, bean.getReason());
            synchronized (DB_LOCK) {
                SQLiteDatabase db = getWritableDatabase();
                ret = db.insert(DBConsts.TABLE_NAME_OUTLET, null, value);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return ret;
    }

    public void updateOutlet(OutletItem item) {
        try {
            String szWhere = DBConsts.FIELD_ORDER_ID + " = '" + item.getOrderId() + "'";
            ContentValues value = new ContentValues();
            value.put(DBConsts.FIELD_REASON, item.getReason());
            value.put(DBConsts.FIELD_DELIVERED, item.getDelivered());
            value.put(DBConsts.FIELD_DELIVER_TIME, item.getDeliveredTime());

            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                db.update(DBConsts.TABLE_NAME_OUTLET, value, szWhere, null);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    public void removeDatasByDespatchID(String despatchID) {
        try {
            String szWhere = DBConsts.FIELD_DESPATCH_ID + " = '" + despatchID + "'";

            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                db.delete(DBConsts.TABLE_NAME_OUTLET, szWhere, null);
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
                db.delete(DBConsts.TABLE_NAME_OUTLET, null, null);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    private ArrayList<OutletItem> createOutletBeans(Cursor c) {
        ArrayList<OutletItem> ret = null;
        try {
            ret = new ArrayList();

            final int COL_ID	            = c.getColumnIndexOrThrow(DBConsts.FIELD_ID),
                    COL_DESPATCH_ID 	    = c.getColumnIndexOrThrow(DBConsts.FIELD_DESPATCH_ID),
                    COL_OUTLET_ID         	= c.getColumnIndexOrThrow(DBConsts.FIELD_OUTLET_ID),
                    COL_ORDER_ID            = c.getColumnIndexOrThrow(DBConsts.FIELD_ORDER_ID),
                    COL_OUTLET   		    = c.getColumnIndexOrThrow(DBConsts.FIELD_OUTLET),
                    COL_ADDRESS             = c.getColumnIndexOrThrow(DBConsts.FIELD_ADDRESS),
                    COL_SERVICE    	 	    = c.getColumnIndexOrThrow(DBConsts.FIELD_SERVICE),
                    COL_SERVICE_ID          = c.getColumnIndexOrThrow(DBConsts.FIELD_SERVICE_ID),
                    COL_DELIVERED 		    = c.getColumnIndexOrThrow(DBConsts.FIELD_DELIVERED),
                    COL_DELIVER_TIME        = c.getColumnIndexOrThrow(DBConsts.FIELD_DELIVER_TIME),
                    COL_TIERS   		    = c.getColumnIndexOrThrow(DBConsts.FIELD_TIERS),
                    COL_ORDER_TYPE          = c.getColumnIndexOrThrow(DBConsts.FIELD_ORDER_TYPE),
                    COL_ORDER_TYPE_DISPLAY  = c.getColumnIndexOrThrow(DBConsts.FIELD_ORDER_TYPE_DISPLAY),
                    COL_REASON    	 	    = c.getColumnIndexOrThrow(DBConsts.FIELD_REASON);

            while (c.moveToNext()) {
                OutletItem bean = new OutletItem();
                bean.setDespatchId(c.getString(COL_DESPATCH_ID));
                bean.setOutletId(c.getString(COL_OUTLET_ID));
                bean.setOrderid(c.getString(COL_ORDER_ID));
                bean.setOutlet(c.getString(COL_OUTLET));
                bean.setAddress(c.getString(COL_ADDRESS));
                bean.setServiceType(c.getString(COL_SERVICE));
                bean.setServiceId(c.getInt(COL_SERVICE_ID));
                bean.setDelivered(c.getInt(COL_DELIVERED));
                bean.setDeliveredTime(c.getString(COL_DELIVER_TIME));
                bean.setTiers(c.getInt(COL_TIERS));
                bean.setOrderType(c.getInt(COL_ORDER_TYPE));
                bean.setOrderTypeDisplay(c.getString(COL_ORDER_TYPE_DISPLAY));
                bean.setReason(c.getInt(COL_REASON));
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
