package delivery.com.model;

/**
 * Created by rgi on 8/22/17.
 */

public class ClockItem {
    private String timeStamp;
    private String clockStatus;

    public ClockItem() {
        timeStamp = "";
        clockStatus = "";
    }

    public void setTimeStamp(String value) {
        this.timeStamp = value;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setClockStatus(String value) {
        this.clockStatus = value;
    }

    public String getClockStatus() {
        return clockStatus;
    }
}
