package delivery.com.proxy;

import com.google.gson.Gson;

import java.io.IOException;

import delivery.com.application.DeliveryApplication;
import delivery.com.util.URLManager;
import delivery.com.vo.ClockResponseVo;
import delivery.com.vo.LoginRequestVo;
import delivery.com.vo.LoginResponseVo;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class ClockProxy extends BaseProxy {

    public ClockResponseVo run(String timestamp, String clockStatus) throws IOException {
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("staffID", DeliveryApplication.staffID);
        formBuilder.add("timedatestamp", timestamp);
        formBuilder.add("status", clockStatus);

        RequestBody formBody = formBuilder.build();

        String contentString = postPlain(URLManager.getClockURL(), formBody);

        ClockResponseVo responseVo = new Gson().fromJson(contentString, ClockResponseVo.class);

        return responseVo;
    }
}
