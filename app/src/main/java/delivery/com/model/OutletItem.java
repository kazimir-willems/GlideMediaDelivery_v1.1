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
    private int serviceId;
    private int delivered;
    private String deliveredTime;
    private int tiers;
    private int reason;
    private int completed;
    private int orderType;
    private String orderTypeDisplay;

    public OutletItem() {
        this.despatchId = "";
        this.outletId = "";
        this.outlet = "";
        this.address = "";
        this.serviceType = "";
        this.serviceId = 0;
        this.delivered = 0;
        this.deliveredTime = "";
        this.tiers = 0;
        this.reason = 0;
        this.orderType = 1;
        this.orderTypeDisplay = "";
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

    public void setServiceId(int value) {
        this.serviceId = value;
    }

    public int getServiceId() {
        return serviceId;
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

    public void setOrderType(int value) {
        this.orderType = value;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderTypeDisplay(String value) {
        this.orderTypeDisplay = value;
    }

    public String getOrderTypeDisplay() {
        return orderTypeDisplay;
    }

}
