package delivery.com.event;

import delivery.com.vo.DownloadDespatchResponseVo;

public class RemoveAllDataEvent {
    private boolean removeRes;

    public RemoveAllDataEvent(boolean res) {
        this.removeRes = res;
    }

    public boolean getRemoveResult() {
        return removeRes;
    }
}
