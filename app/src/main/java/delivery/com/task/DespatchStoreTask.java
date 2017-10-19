package delivery.com.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import delivery.com.consts.StateConsts;
import delivery.com.db.DespatchDB;
import delivery.com.db.OutletDB;
import delivery.com.db.RemoveStockDB;
import delivery.com.db.StockDB;
import delivery.com.db.TierDB;
import delivery.com.event.DespatchStoreEvent;
import delivery.com.event.DownloadDespatchEvent;
import delivery.com.model.DespatchItem;
import delivery.com.model.OutletItem;
import delivery.com.model.RemoveStockItem;
import delivery.com.model.StockItem;
import delivery.com.model.TierItem;
import delivery.com.proxy.DownloadDespatchProxy;
import delivery.com.ui.MainActivity;
import delivery.com.vo.DownloadDespatchResponseVo;

public class DespatchStoreTask extends AsyncTask<String, Void, Integer> {

    private String despatches;
    private Context ctx;

    public DespatchStoreTask(Context context) {
        this.ctx = context;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Integer doInBackground(String... params) {
        despatches = params[0];
        try {
            JSONArray jsonDespatchArray = new JSONArray(despatches);
            DespatchDB despatchDB = new DespatchDB(ctx);
            OutletDB outletDB = new OutletDB(ctx);
            TierDB tierDB = new TierDB(ctx);
            StockDB stockDB = new StockDB(ctx);
            RemoveStockDB removeStockDB = new RemoveStockDB(ctx);

            if(jsonDespatchArray.length() == 0)
                return 2;

            for(int i = 0; i < jsonDespatchArray.length(); i++) {
                JSONObject jsonDespatchItem = jsonDespatchArray.getJSONObject(i);
                DespatchItem despatchItem = new DespatchItem();

                String despatchID = jsonDespatchItem.getString("despatchID");

                despatchItem.setDespatchId(despatchID);
                despatchItem.setRunId(jsonDespatchItem.getString("run"));
                despatchItem.setDriverName(jsonDespatchItem.getString("driver"));
                despatchItem.setCreationDate(jsonDespatchItem.getString("date"));
                despatchItem.setRoute(jsonDespatchItem.getString("route"));
                despatchItem.setReg(jsonDespatchItem.getString("reg"));
                despatchItem.setCompleted(StateConsts.DESPATCH_DEFAULT);

                if(!despatchDB.isExist(despatchItem)) {
                    despatchDB.addDespatch(despatchItem);
                    String despatchOutlet = jsonDespatchItem.getString("outlet");
                    JSONArray jsonOutletArray = new JSONArray(despatchOutlet);
                    for(int j = 0; j < jsonOutletArray.length(); j++) {
                        JSONObject jsonOutletItem = jsonOutletArray.getJSONObject(j);

                        OutletItem outletItem = new OutletItem();
                        String outletID = jsonOutletItem.getString("outletID");
                        outletItem.setDespatchId(despatchID);
                        outletItem.setOutletId(outletID);
                        outletItem.setOrderid(jsonOutletItem.getString("orderID"));
                        outletItem.setOutlet(jsonOutletItem.getString("outlet"));
                        outletItem.setAddress(jsonOutletItem.getString("address"));
                        outletItem.setServiceType(jsonOutletItem.getString("service"));
                        outletItem.setServiceId(jsonOutletItem.getInt("serviceID"));
                        outletItem.setDelivered(jsonOutletItem.getInt("delivered"));
                        outletItem.setDeliveredTime(jsonOutletItem.getString("deliveredtime"));
                        outletItem.setTiers(jsonOutletItem.getInt("tierstotal"));
                        outletItem.setReason(jsonOutletItem.getInt("reason"));
                        outletItem.setOrderType(jsonOutletItem.getInt("orderType"));
                        outletItem.setOrderTypeDisplay(jsonOutletItem.getString("orderTypeDisplay"));

                        Log.v("ServiceID", String.valueOf(jsonOutletItem.getInt("serviceID")));

                        outletDB.addOutlet(outletItem);

                        String removeItems = jsonOutletItem.getString("removeStock");
                        JSONArray jsonRemoveStockArray = new JSONArray(removeItems);

                        for(int u = 0; u < jsonRemoveStockArray.length(); u++) {
                            JSONObject jsonRemoveStockItem = jsonRemoveStockArray.getJSONObject(u);
                            RemoveStockItem removeStockItem = new RemoveStockItem();
                            removeStockItem.setDespatchID(despatchID);
                            removeStockItem.setOutletID(outletID);
                            removeStockItem.setTitleID(jsonRemoveStockItem.getString("titleID"));
                            removeStockItem.setTitle(jsonRemoveStockItem.getString("title"));
                            removeStockItem.setSize(jsonRemoveStockItem.getString("size"));

                            removeStockDB.addRemoveStock(removeStockItem);
                        }

                        String tiers = jsonOutletItem.getString("tiers");
                        JSONArray jsonTierArray = new JSONArray(tiers);

                        for(int u = 0; u < jsonTierArray.length(); u++) {
                            JSONObject jsonTierItem = jsonTierArray.getJSONObject(u);
                            TierItem tierItem = new TierItem();

                            String tierNo = jsonTierItem.getString("tier_no");

                            tierItem.setDespatchID(despatchID);
                            tierItem.setOutletID(outletID);
                            tierItem.setTierNo(tierNo);
                            tierItem.setTierspace(jsonTierItem.getString("tierspace"));
                            tierItem.setSlots(jsonTierItem.getInt("slots"));
                            tierItem.setTierOrder(jsonTierItem.getInt("tierOrder"));

                            tierDB.addTier(tierItem);

                            String stock = jsonTierItem.getString("stock");
                            JSONArray jsonStockArray = new JSONArray(stock);
                            for (int k = 0; k < jsonStockArray.length(); k++) {
                                JSONObject jsonStockItem = jsonStockArray.getJSONObject(k);
                                StockItem stockItem = new StockItem();

                                stockItem.setDespatchID(despatchID);
                                stockItem.setOutletID(outletID);
                                stockItem.setStock(jsonStockItem.getString("stock"));
                                stockItem.setTitleID(jsonStockItem.getString("titleID"));
                                stockItem.setStockId(jsonStockItem.getString("stockID"));
                                stockItem.setSize(jsonStockItem.getString("size"));
                                stockItem.setTier(tierNo);
                                stockItem.setStatus(jsonStockItem.getString("status"));
                                stockItem.setSlotOrder(jsonStockItem.getInt("slotOrder"));
                                String slot = jsonStockItem.getString("slot");
                                if (!slot.isEmpty())
                                    stockItem.setSlot(jsonStockItem.getString("slot"));
                                else
                                    stockItem.setSlot("0");
                                if(stockItem.getStatus().equals("New Stock"))
                                    stockItem.setQty(StateConsts.STOCK_QTY_FULL);
                                else
                                    stockItem.setQty(StateConsts.STOCK_QTY_NONE);
                                stockItem.setRemove(jsonStockItem.getString("remove"));
                                stockItem.setRemoveID(jsonStockItem.getString("removeID"));

                                stockDB.addStock(stockItem);
                            }
                        }
                    }
                }
            }

            return 1;
        } catch (JSONException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    protected void onPostExecute(Integer result) {
        EventBus.getDefault().post(new DespatchStoreEvent(result));
    }
}