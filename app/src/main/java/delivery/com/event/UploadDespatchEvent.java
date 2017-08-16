package delivery.com.event;

import delivery.com.vo.DownloadDespatchResponseVo;
import delivery.com.vo.UploadDespatchResponseVo;

public class UploadDespatchEvent {
    private UploadDespatchResponseVo responseVo;

    public UploadDespatchEvent(UploadDespatchResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public UploadDespatchResponseVo getResponse() {
        return responseVo;
    }
}
