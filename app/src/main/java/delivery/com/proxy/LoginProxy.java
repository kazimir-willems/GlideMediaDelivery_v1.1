package delivery.com.proxy;

import com.google.gson.Gson;

import java.io.IOException;

import delivery.com.util.URLManager;
import delivery.com.vo.LoginRequestVo;
import delivery.com.vo.LoginResponseVo;
import delivery.com.vo.UploadDespatchRequestVo;
import delivery.com.vo.UploadDespatchResponseVo;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class LoginProxy extends BaseProxy {

    public LoginResponseVo run(String data) throws IOException {
        LoginRequestVo requestVo = new LoginRequestVo();
        requestVo.data = data;
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("data", requestVo.data);

        RequestBody formBody = formBuilder.build();

        String contentString = postPlain(URLManager.getLoginURL(), formBody);

        LoginResponseVo responseVo = new Gson().fromJson(contentString, LoginResponseVo.class);

        return responseVo;
    }
}
