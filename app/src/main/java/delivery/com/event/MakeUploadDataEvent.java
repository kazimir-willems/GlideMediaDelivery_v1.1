package delivery.com.event;

public class MakeUploadDataEvent {
    private String despatches;

    public MakeUploadDataEvent(String result) {
        this.despatches = result;
    }

    public String getResponse() {
        return despatches;
    }
}
