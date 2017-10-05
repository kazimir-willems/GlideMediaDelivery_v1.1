package delivery.com.event;

import delivery.com.vo.ClockHistoryResponseVo;
import delivery.com.vo.ClockResponseVo;

public class ClockEvent {
    private ClockResponseVo responseVo;

    public ClockEvent(ClockResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public ClockResponseVo getResponse() {
        return responseVo;
    }
}
