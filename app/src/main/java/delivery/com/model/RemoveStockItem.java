package delivery.com.model;

/**
 * Created by rgi on 8/21/17.
 */

public class RemoveStockItem {
    private String despatchID;
    private String outletID;
    private String orderID;
    private String size;
    private String titleID;
    private String title;

    public RemoveStockItem() {
        despatchID = "";
        outletID = "";
        orderID = "";
        size = "";
        title = "";
        titleID = "";
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

    public void setOrderID(String value) {
        this.orderID = value;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setSize(String value) {
        this.size = value;
    }

    public String getSize() {
        return size;
    }

    public void setTitleID(String value) {
        this.titleID = value;
    }

    public String getTitleID() {
        return titleID;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public String getTitle() {
        return title;
    }
}
