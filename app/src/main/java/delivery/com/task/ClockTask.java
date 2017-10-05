package delivery.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import delivery.com.event.ClockEvent;
import delivery.com.event.ClockHistoryEvent;
import delivery.com.event.LoginEvent;
import delivery.com.proxy.ClockProxy;
import delivery.com.proxy.LoginProxy;
import delivery.com.vo.ClockResponseVo;
import delivery.com.vo.LoginResponseVo;

public class ClockTask extends AsyncTask<String, Void, ClockResponseVo> {

    private String timestamp;
    private String status;

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected ClockResponseVo doInBackground(String... params) {
        ClockProxy simpleProxy = new ClockProxy();
        timestamp = params[0];
        status = params[1];
        try {
            final ClockResponseVo responseVo = simpleProxy.run(timestamp, status);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ClockResponseVo responseVo) {
        EventBus.getDefault().post(new ClockEvent(responseVo));
    }
}