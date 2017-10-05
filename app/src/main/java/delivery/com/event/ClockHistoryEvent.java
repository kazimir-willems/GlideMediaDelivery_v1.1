package delivery.com.event;

import delivery.com.vo.ClockHistoryResponseVo;

public class ClockHistoryEvent {
    private ClockHistoryResponseVo responseVo;

    public ClockHistoryEvent(ClockHistoryResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public ClockHistoryResponseVo getResponse() {
        return responseVo;
    }
}
