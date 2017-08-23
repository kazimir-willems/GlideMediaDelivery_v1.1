package delivery.com.proxy;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

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

        JSONObject json = new JSONObject();
        try {
            json.put("staffID", DeliveryApplication.staffID);
            json.put("timedatestamp", timestamp);
            json.put("status", clockStatus);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("data", json.toString());

        RequestBody formBody = formBuilder.build();

        String contentString = postPlain(URLManager.getClockURL(), formBody);

        ClockResponseVo responseVo = new Gson().fromJson(contentString, ClockResponseVo.class);

        return responseVo;
    }
}
