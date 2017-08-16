package delivery.com.task;

import android.content.Context;
import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import delivery.com.db.DespatchDB;
import delivery.com.db.OutletDB;
import delivery.com.db.StockDB;
import delivery.com.db.TierDB;
import delivery.com.event.DownloadDespatchEvent;
import delivery.com.event.RemoveAllDataEvent;
import delivery.com.proxy.DownloadDespatchProxy;
import delivery.com.vo.DownloadDespatchResponseVo;

public class RemoveAllDataTask extends AsyncTask<String, Void, Boolean> {

    private Context context;

    public RemoveAllDataTask(Context ctx) {
        this.context = ctx;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            StockDB stockDB = new StockDB(context);
            stockDB.removeAllDatas();
            TierDB tierDB = new TierDB(context);
            tierDB.removeAllDatas();
            OutletDB outletDB = new OutletDB(context);
            outletDB.removeAllDatas();
            DespatchDB despatchDB = new DespatchDB(context);
            despatchDB.removeAllDatas();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        EventBus.getDefault().post(new RemoveAllDataEvent(result));
    }
}