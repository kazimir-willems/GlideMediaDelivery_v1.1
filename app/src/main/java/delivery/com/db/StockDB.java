package delivery.com.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import delivery.com.consts.DBConsts;
import delivery.com.model.StockItem;
import delivery.com.util.DBHelper;

public class StockDB extends DBHelper {
    private static final Object[] DB_LOCK 		= new Object[0];

    public StockDB(Context context) {
        super(context);
    }

    public ArrayList<StockItem> fetchStocksByOutletID(String outletID, String tier) {
        ArrayList<StockItem> ret = null;
        try {
            String szWhere = DBConsts.FIELD_OUTLET_ID + " = '" + outletID + "' AND " + DBConsts.FIELD_TIER + " = '" + tier + "'";
            String szOrderBy = DBConsts.FIELD_SLOT_ORDER + " ASC";
            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                Cursor cursor = db.query(DBConsts.TABLE_NAME_STOCK, null, szWhere, null, null, null, szOrderBy);
                ret = createOutletBeans(cursor);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return ret;
    }

    public long addStock(StockItem bean) {
        long ret = -1;
        try {
            ContentValues value = new ContentValues();
            value.put(DBConsts.FIELD_DESPATCH_ID, bean.getDespatchID());
            value.put(DBConsts.FIELD_OUTLET_ID, bean.getOutletID());
            value.put(DBConsts.FIELD_STOCK_ID, bean.getStockId());
            value.put(DBConsts.FIELD_STOCK, bean.getStock());
            value.put(DBConsts.FIELD_TIER, bean.getTier());
            value.put(DBConsts.FIELD_SLOT, bean.getSlot());
            value.put(DBConsts.FIELD_QTY, bean.getQty());
            value.put(DBConsts.FIELD_STATUS, bean.getStatus());
            value.put(DBConsts.FIELD_REMOVE, bean.getRemove());
            value.put(DBConsts.FIELD_REMOVE_ID, bean.getRemoveID());
            value.put(DBConsts.FIELD_TITLE_ID, bean.getTitleID());
            value.put(DBConsts.FIELD_SIZE, bean.getSize());
            value.put(DBConsts.FIELD_SLOT_ORDER, bean.getSlotOrder());
            synchronized (DB_LOCK) {
                SQLiteDatabase db = getWritableDatabase();
                ret = db.insert(DBConsts.TABLE_NAME_STOCK, null, value);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return ret;
    }

    public void updateStock(StockItem item) {
        try {
            String szWhere = DBConsts.FIELD_OUTLET_ID + " = '" + item.getOutletID() + "' AND " + DBConsts.FIELD_STOCK_ID + " = '" + item.getStockId() + "'";
            ContentValues value = new ContentValues();
            value.put(DBConsts.FIELD_QTY, item.getQty());

            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                db.update(DBConsts.TABLE_NAME_STOCK, value, szWhere, null);
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
                db.delete(DBConsts.TABLE_NAME_STOCK, szWhere, null);
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
                db.delete(DBConsts.TABLE_NAME_STOCK, null, null);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    private ArrayList<StockItem> createOutletBeans(Cursor c) {
        ArrayList<StockItem> ret = null;
        try {
            ret = new ArrayList();

            final int COL_ID	            = c.getColumnIndexOrThrow(DBConsts.FIELD_ID),
                    COL_DESPATCH_ID 	    = c.getColumnIndexOrThrow(DBConsts.FIELD_DESPATCH_ID),
                    COL_OUTLET_ID         	= c.getColumnIndexOrThrow(DBConsts.FIELD_OUTLET_ID),
                    COL_STOCK_ID   		    = c.getColumnIndexOrThrow(DBConsts.FIELD_STOCK_ID),
                    COL_STOCK               = c.getColumnIndexOrThrow(DBConsts.FIELD_STOCK),
                    COL_TIER    	 	    = c.getColumnIndexOrThrow(DBConsts.FIELD_TIER),
                    COL_SLOT     		    = c.getColumnIndexOrThrow(DBConsts.FIELD_SLOT),
                    COL_QTY     	 	    = c.getColumnIndexOrThrow(DBConsts.FIELD_QTY),
                    COL_STATUS    	 	    = c.getColumnIndexOrThrow(DBConsts.FIELD_STATUS),
                    COL_TITLE_ID   	 	    = c.getColumnIndexOrThrow(DBConsts.FIELD_TITLE_ID),
                    COL_SIZE    	 	    = c.getColumnIndexOrThrow(DBConsts.FIELD_SIZE),
                    COL_REMOVE     		    = c.getColumnIndexOrThrow(DBConsts.FIELD_REMOVE),
                    COL_SLOT_ORDER  		= c.getColumnIndexOrThrow(DBConsts.FIELD_SLOT_ORDER),
                    COL_REMOVE_ID     	 	= c.getColumnIndexOrThrow(DBConsts.FIELD_REMOVE_ID);

            while (c.moveToNext()) {
                StockItem bean = new StockItem();
                bean.setDespatchID(c.getString(COL_DESPATCH_ID));
                bean.setOutletID(c.getString(COL_OUTLET_ID));
                bean.setStockId(c.getString(COL_STOCK_ID));
                bean.setStock(c.getString(COL_STOCK));
                bean.setTier(c.getString(COL_TIER));
                bean.setSlot(c.getString(COL_SLOT));
                bean.setQty(c.getInt(COL_QTY));
                bean.setStatus(c.getString(COL_STATUS));
                bean.setTitleID(c.getString(COL_TITLE_ID));
                bean.setSize(c.getString(COL_SIZE));
                bean.setSlotOrder(c.getInt(COL_SLOT_ORDER));
                bean.setRemove(c.getString(COL_REMOVE));
                bean.setRemoveID(c.getString(COL_REMOVE_ID));
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
