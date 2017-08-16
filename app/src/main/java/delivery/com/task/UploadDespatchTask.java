package delivery.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import delivery.com.event.DownloadDespatchEvent;
import delivery.com.event.UploadDespatchEvent;
import delivery.com.proxy.DownloadDespatchProxy;
import delivery.com.proxy.UploadDespatchProxy;
import delivery.com.vo.DownloadDespatchResponseVo;
import delivery.com.vo.UploadDespatchResponseVo;

public class UploadDespatchTask extends AsyncTask<String, Void, UploadDespatchResponseVo> {

    private String datas;

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected UploadDespatchResponseVo doInBackground(String... params) {
        UploadDespatchProxy simpleProxy = new UploadDespatchProxy();
        datas = params[0];
        try {
            final UploadDespatchResponseVo responseVo = simpleProxy.run(datas);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(UploadDespatchResponseVo responseVo) {
        EventBus.getDefault().post(new UploadDespatchEvent(responseVo));
    }
}