package delivery.com.event;

import delivery.com.vo.DownloadDespatchResponseVo;

public class DownloadDespatchEvent {
    private DownloadDespatchResponseVo responseVo;

    public DownloadDespatchEvent(DownloadDespatchResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public DownloadDespatchResponseVo getResponse() {
        return responseVo;
    }
}
