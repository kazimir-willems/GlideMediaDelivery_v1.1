package delivery.com.model;

import java.io.Serializable;

/**
 * Created by Caesar on 4/21/2017.
 */

public class DespatchItem implements Serializable {
    private String despatchId;
    private String driverName;
    private String creationDate;
    private String runId;
    private String route;
    private String reg;
    private int completed;
    private int nCntAll;
    private int nCntCompleted;

    public DespatchItem() {
        despatchId = "";
        runId = "";
        driverName = "";
        creationDate = "";
        route = "";
        reg = "";
        completed = 0;
        nCntAll = 0;
        nCntCompleted = 0;
    }

    public void setDespatchId(String value) {
        this.despatchId = value;
    }

    public String getDespatchId() {
        return despatchId;
    }

    public void setRunId(String value) {
        this.runId = value;
    }

    public String getRunId() {
        return runId;
    }

    public void setDriverName(String value) {
        this.driverName = value;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setCreationDate(String value) {
        this.creationDate = value;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setRoute(String value) {
        this.route = value;
    }

    public String getRoute() {
        return route;
    }

    public void setReg(String value) {
        this.reg = value;
    }

    public String getReg() {
        return reg;
    }

    public void setCompleted(int value) {
        this.completed = value;
    }

    public int getCompleted() {
        return completed;
    }

    public void setnCntAll(int value) {
        this.nCntAll = value;
    }

    public int getnCntAll() {
        return nCntAll;
    }

    public void setnCntCompleted(int value) {
        this.nCntCompleted = value;
    }

    public int getnCntCompleted() {
        return nCntCompleted;
    }
}
