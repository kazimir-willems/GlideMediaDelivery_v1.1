package delivery.com.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import delivery.com.consts.DBConsts;
import delivery.com.model.StockItem;
import delivery.com.model.TierItem;
import delivery.com.util.DBHelper;

public class TierDB extends DBHelper {
    private static final Object[] DB_LOCK 		= new Object[0];

    public TierDB(Context context) {
        super(context);
    }

    public ArrayList<TierItem> fetchAllTiersByOutletID(String outletID, String tierno) {
        ArrayList<TierItem> ret = null;
        try {
            String szWhere = DBConsts.FIELD_OUTLET_ID + " = '" + outletID + "' AND " + DBConsts.FIELD_TIER_NO + " = '" + tierno + "'";
            String szOrderBy = DBConsts.FIELD_TIER_ORDER + " ASC";
            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                Cursor cursor = db.query(DBConsts.TABLE_NAME_TIER, null, szWhere, null, null, null, szOrderBy);
                ret = createOutletBeans(cursor);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return ret;
    }

    public ArrayList<TierItem> fetchTiers(String despatchID, String outletID) {
        ArrayList<TierItem> ret = null;
        try {
            String szWhere = DBConsts.FIELD_DESPATCH_ID + " = '" + despatchID + "' AND " + DBConsts.FIELD_OUTLET_ID + " = '" + outletID + "'";
            String szOrderBy = DBConsts.FIELD_TIER_ORDER + " ASC";
            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                Cursor cursor = db.query(DBConsts.TABLE_NAME_TIER, null, szWhere, null, null, null, szOrderBy);
                ret = createOutletBeans(cursor);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return ret;
    }

    public long addTier(TierItem bean) {
        long ret = -1;
        try {
            ContentValues value = new ContentValues();
            value.put(DBConsts.FIELD_DESPATCH_ID, bean.getDespatchID());
            value.put(DBConsts.FIELD_OUTLET_ID, bean.getOutletID());
            value.put(DBConsts.FIELD_TIER_NO, bean.getTierNo());
            value.put(DBConsts.FIELD_TIER_SPACE, bean.getTierspace());
            value.put(DBConsts.FIELD_SLOTS, bean.getSlots());
            value.put(DBConsts.FIELD_TIER_ORDER, bean.getTierOrder());
            synchronized (DB_LOCK) {
                SQLiteDatabase db = getWritableDatabase();
                ret = db.insert(DBConsts.TABLE_NAME_TIER, null, value);
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
                db.delete(DBConsts.TABLE_NAME_TIER, szWhere, null);
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
                db.delete(DBConsts.TABLE_NAME_TIER, null, null);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    private ArrayList<TierItem> createOutletBeans(Cursor c) {
        ArrayList<TierItem> ret = null;
        try {
            ret = new ArrayList();

            final int COL_ID	            = c.getColumnIndexOrThrow(DBConsts.FIELD_ID),
                    COL_DESPATCH_ID 	    = c.getColumnIndexOrThrow(DBConsts.FIELD_DESPATCH_ID),
                    COL_OUTLET_ID         	= c.getColumnIndexOrThrow(DBConsts.FIELD_OUTLET_ID),
                    COL_TIER_NO   		    = c.getColumnIndexOrThrow(DBConsts.FIELD_TIER_NO),
                    COL_TIER_SPACE          = c.getColumnIndexOrThrow(DBConsts.FIELD_TIER_SPACE),
                    COL_TIER_ORDER          = c.getColumnIndexOrThrow(DBConsts.FIELD_TIER_ORDER),
                    COL_SLOTS    	 	    = c.getColumnIndexOrThrow(DBConsts.FIELD_SLOTS);

            while (c.moveToNext()) {
                TierItem bean = new TierItem();
                bean.setDespatchID(c.getString(COL_DESPATCH_ID));
                bean.setOutletID(c.getString(COL_OUTLET_ID));
                bean.setTierNo(c.getString(COL_TIER_NO));
                bean.setTierspace(c.getString(COL_TIER_SPACE));
                bean.setSlots(c.getInt(COL_SLOTS));
                bean.setTierOrder(c.getInt(COL_TIER_ORDER));
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
