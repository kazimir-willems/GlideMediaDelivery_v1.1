package delivery.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import delivery.com.event.ClockHistoryEvent;
import delivery.com.event.LoginEvent;
import delivery.com.proxy.ClockHistoryProxy;
import delivery.com.proxy.LoginProxy;
import delivery.com.vo.ClockHistoryResponseVo;
import delivery.com.vo.LoginResponseVo;

public class GetClockHistoryTask extends AsyncTask<String, Void, ClockHistoryResponseVo> {


    @Override
    protected void onPreExecute() {

    }

    @Override
    protected ClockHistoryResponseVo doInBackground(String... params) {
        ClockHistoryProxy simpleProxy = new ClockHistoryProxy();
        try {
            final ClockHistoryResponseVo responseVo = simpleProxy.run();

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ClockHistoryResponseVo responseVo) {
        EventBus.getDefault().post(new ClockHistoryEvent(responseVo));
    }
}