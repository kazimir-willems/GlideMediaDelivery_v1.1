package delivery.com.model;


import java.io.Serializable;

import delivery.com.consts.StateConsts;

/**
 * Created by Caesar on 4/21/2017.
 */

public class OutletItem implements Serializable {
    private String despatchId;
    private String outletId;
    private String outlet;
    private String address;
    private String serviceType;
    private int delivered;
    private String deliveredTime;
    private int tiers;
    private int reason;
    private int completed;

    public OutletItem() {
        this.despatchId = "";
        this.outletId = "";
        this.outlet = "";
        this.address = "";
        this.serviceType = "";
        this.delivered = 0;
        this.deliveredTime = "";
        this.tiers = 0;
        this.reason = 0;
        this.completed = StateConsts.OUTLET_NOT_DELIVERED;
    }

    public void setDespatchId(String value) {
        this.despatchId = value;
    }

    public String getDespatchId() {
        return despatchId;
    }

    public void setOutletId(String value) {
        this.outletId = value;
    }

    public String getOutletId() {
        return outletId;
    }

    public void setOutlet(String value) {
        this.outlet = value;
    }

    public String getOutlet() {
        return outlet;
    }

    public void setAddress(String value) {
        this.address = value;
    }

    public String getAddress() {
        return address;
    }

    public void setServiceType(String value) {
        this.serviceType = value;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setDelivered(int value) {
        this.delivered = value;
    }

    public int getDelivered() {
        return delivered;
    }

    public void setDeliveredTime(String value) {
        this.deliveredTime = value;
    }

    public String getDeliveredTime() {
        return deliveredTime;
    }

    public void setTiers(int value) {
        this.tiers = value;
    }

    public int getTiers() {
        return tiers;
    }

    public void setReason(int value) {
        this.reason = value;
    }

    public int getReason() {
        return this.reason;
    }

    public void setCompleted(int value) {
        this.completed = value;
    }

    public int getCompleted() {
        return completed;
    }

}
