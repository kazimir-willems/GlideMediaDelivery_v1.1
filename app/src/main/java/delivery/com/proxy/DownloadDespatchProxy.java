package delivery.com.proxy;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import delivery.com.util.URLManager;
import delivery.com.vo.DownloadDespatchRequestVo;
import delivery.com.vo.DownloadDespatchResponseVo;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class DownloadDespatchProxy extends BaseProxy {

    public DownloadDespatchResponseVo run() throws IOException {
        FormBody.Builder formBuilder = new FormBody.Builder();

        RequestBody formBody = formBuilder.build();

        String contentString = postPlain(URLManager.getDownloadDespatchURL(), formBody);

        DownloadDespatchResponseVo responseVo = new DownloadDespatchResponseVo();

        try {
            JSONObject json = new JSONObject(contentString);
            responseVo.despatch = json.getString("despatch");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return responseVo;
    }
}
