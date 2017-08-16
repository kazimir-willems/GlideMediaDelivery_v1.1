package delivery.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import delivery.com.event.DownloadDespatchEvent;
import delivery.com.proxy.DownloadDespatchProxy;
import delivery.com.vo.DownloadDespatchResponseVo;

public class DownloadDespatchTask extends AsyncTask<String, Void, DownloadDespatchResponseVo> {

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected DownloadDespatchResponseVo doInBackground(String... params) {
        DownloadDespatchProxy simpleProxy = new DownloadDespatchProxy();
        try {
            final DownloadDespatchResponseVo responseVo = simpleProxy.run();

            return responseVo;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(DownloadDespatchResponseVo responseVo) {
        EventBus.getDefault().post(new DownloadDespatchEvent(responseVo));
    }
}