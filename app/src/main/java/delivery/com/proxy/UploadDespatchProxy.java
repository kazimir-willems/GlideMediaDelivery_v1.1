package delivery.com.proxy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import delivery.com.util.URLManager;
import delivery.com.vo.DownloadDespatchResponseVo;
import delivery.com.vo.UploadDespatchRequestVo;
import delivery.com.vo.UploadDespatchResponseVo;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class UploadDespatchProxy extends BaseProxy {

    public UploadDespatchResponseVo run(String despatches) throws IOException {
        UploadDespatchRequestVo requestVo = new UploadDespatchRequestVo();
        requestVo.data = despatches;
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("data", requestVo.data);

        RequestBody formBody = formBuilder.build();

        String contentString = postPlain(URLManager.getUploadDespatchURL(), formBody);

        UploadDespatchResponseVo responseVo = new UploadDespatchResponseVo();

        responseVo.success = 1;

        return responseVo;
    }
}
