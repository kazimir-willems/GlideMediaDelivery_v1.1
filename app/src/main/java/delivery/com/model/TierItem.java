package delivery.com.model;

/**
 * Created by Caesar on 5/9/2017.
 */

public class TierItem {
    private String despatchID;
    private String outletID;
    private String tierNo;
    private String tierspace;
    private int tierOrder;
    private int slots;

    public TierItem() {
        despatchID = "";
        outletID = "";
        tierNo= "";
        tierspace = "";
        tierOrder = 0;
    }

    public void setDespatchID(String value) {
        this.despatchID = value;
    }

    public String getDespatchID() {
        return despatchID;
    }

    public void setOutletID(String value) {
        this.outletID = value;
    }

    public String getOutletID() {
        return outletID;
    }

    public void setTierNo(String value) {
        this.tierNo = value;
    }

    public String getTierNo() {
        return tierNo;
    }

    public void setTierspace(String value) {
        this.tierspace = value;
    }

    public String getTierspace() {
        return tierspace;
    }

    public void setSlots(int value) {
        this.slots = value;
    }

    public int getSlots() {
        return slots;
    }

    public void setTierOrder(int value) {
        this.tierOrder = value;
    }

    public int getTierOrder() {
        return tierOrder;
    }
}
