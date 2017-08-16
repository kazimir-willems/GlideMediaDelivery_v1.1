package delivery.com.event;

import delivery.com.vo.DownloadDespatchResponseVo;

public class DespatchStoreEvent {
    private int result;

    public DespatchStoreEvent(int result) {
        this.result = result;
    }

    public int getResponse() {
        return result;
    }
}
