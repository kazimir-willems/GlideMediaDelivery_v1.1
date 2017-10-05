package delivery.com.proxy;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import delivery.com.application.DeliveryApplication;
import delivery.com.util.SharedPrefManager;
import delivery.com.util.URLManager;
import delivery.com.vo.ClockHistoryRequestVo;
import delivery.com.vo.ClockHistoryResponseVo;
import delivery.com.vo.LoginRequestVo;
import delivery.com.vo.LoginResponseVo;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class ClockHistoryProxy extends BaseProxy {

    public ClockHistoryResponseVo run() throws IOException {

        JSONObject json = new JSONObject();
        try {
            json.put("staffID", DeliveryApplication.staffID);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("data", json.toString());

        RequestBody formBody = formBuilder.build();

        String contentString = postPlain(URLManager.getClockHistoryURL(), formBody);
        Log.v("contentString", contentString);

        ClockHistoryResponseVo responseVo = new ClockHistoryResponseVo();

        try {
            JSONObject jsonResponse = new JSONObject(contentString);
            responseVo.clockHistory = jsonResponse.getString("history");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return responseVo;
    }
}
