package delivery.com.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import delivery.com.R;
import delivery.com.adapter.ClockAdapter;
import delivery.com.adapter.RemoveStockAdapter;
import delivery.com.application.DeliveryApplication;
import delivery.com.db.ClockDB;
import delivery.com.db.RemoveStockDB;
import delivery.com.event.ClockEvent;
import delivery.com.event.ClockHistoryEvent;
import delivery.com.event.LoginEvent;
import delivery.com.model.ClockItem;
import delivery.com.model.RemoveStockItem;
import delivery.com.task.ClockTask;
import delivery.com.task.GetClockHistoryTask;
import delivery.com.util.DateUtil;
import delivery.com.util.SharedPrefManager;
import delivery.com.vo.ClockHistoryResponseVo;
import delivery.com.vo.LoginResponseVo;

public class ClockActivity extends AppCompatActivity {

    @BindView(R.id.btn_clock)
    Button btnClock;
    @BindView(R.id.btn_lunch)
    Button btnLunch;
    @BindView(R.id.clock_list)
    RecyclerView clockList;

    private ArrayList<ClockItem> clockItems = new ArrayList<>();
    private ClockAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        ButterKnife.bind(this);

        if(!SharedPrefManager.getInstance(this).getClockStatus()) {
            btnClock.setBackground(getResources().getDrawable(R.drawable.button_remove));
            btnClock.setText(getResources().getString(R.string.btn_clock_on));
        } else {
            btnClock.setBackground(getResources().getDrawable(R.drawable.button_complete));
            btnClock.setText(getResources().getString(R.string.btn_clock_off));
        }

        if(!SharedPrefManager.getInstance(this).getLunchStatus()) {
            btnLunch.setBackground(getResources().getDrawable(R.drawable.button_remove));
            btnLunch.setText(getResources().getString(R.string.btn_lunch_on));
        } else {
            btnLunch.setBackground(getResources().getDrawable(R.drawable.button_complete));
            btnLunch.setText(getResources().getString(R.string.btn_lunch_off));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        clockList.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(ClockActivity.this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        clockList.setLayoutManager(mLinearLayoutManager);
        clockList.addItemDecoration(new DividerItemDecoration(ClockActivity.this, DividerItemDecoration.VERTICAL_LIST));

        adapter = new ClockAdapter(ClockActivity.this);
        clockList.setAdapter(adapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.processing));

        refreshItems();
    }

    @Override
    public void onResume() {
        super.onResume();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onClickHistoryEvent(ClockHistoryEvent event) {
//        hideProgressDialog();
        ClockHistoryResponseVo responseVo = event.getResponse();

        if(progressDialog.isShowing())
            progressDialog.dismiss();

        if (responseVo != null) {
            try {
                JSONArray clockHistoryArray = new JSONArray(responseVo.clockHistory);
                JSONObject jsonClock = clockHistoryArray.getJSONObject(0);
                String status = jsonClock.getString("status");
                if(status.equals("CLOCKON")) {
                    SharedPrefManager.getInstance(this).saveClockStatus(true);
                    btnClock.setBackground(getResources().getDrawable(R.drawable.button_complete));
                    btnClock.setText(getResources().getString(R.string.btn_clock_off));
                } else {
                    SharedPrefManager.getInstance(this).saveClockStatus(false);
                    btnClock.setBackground(getResources().getDrawable(R.drawable.button_remove));
                    btnClock.setText(getResources().getString(R.string.btn_clock_on));
                }

                clockItems.clear();
                for(int i = 0; i < clockHistoryArray.length(); i++) {
                    JSONObject jsonClockItem = clockHistoryArray.getJSONObject(i);

                    ClockItem item = new ClockItem();

                    item.setStaffID(jsonClockItem.getString("staffID"));
                    item.setTimeStamp(jsonClockItem.getString("time"));
                    item.setClockStatus(jsonClockItem.getString("status"));

                    clockItems.add(item);
                }

                adapter.addItems(clockItems);
                adapter.notifyDataSetChanged();
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        } else {
            networkError();
        }
    }

    @Subscribe
    public void onClockEvent(ClockEvent event) {
        if(progressDialog.isShowing())
            progressDialog.dismiss();
        refreshItems();
    }

    @OnClick(R.id.btn_clock)
    void onClickBtnClock() {
        ClockDB clockDB = new ClockDB(this);
        String curTime = DateUtil.getCurDateTime();
        if(!SharedPrefManager.getInstance(this).getClockStatus()) {
            btnClock.setBackground(getResources().getDrawable(R.drawable.button_complete));
            btnClock.setText(getResources().getString(R.string.btn_clock_off));
            SharedPrefManager.getInstance(this).saveClockStatus(true);

            progressDialog.show();

            ClockTask task = new ClockTask();
            task.execute(curTime, "clockON");

            ClockItem item = new ClockItem();
            item.setStaffID(DeliveryApplication.staffID);
            item.setTimeStamp(curTime);
            item.setClockStatus("Clock ON");

            clockDB.addClock(item);
        } else {
            btnClock.setBackground(getResources().getDrawable(R.drawable.button_remove));
            btnClock.setText(getResources().getString(R.string.btn_clock_on));
            SharedPrefManager.getInstance(this).saveClockStatus(false);

            progressDialog.show();

            ClockTask task = new ClockTask();
            task.execute(curTime, "clockOFF");

            ClockItem item = new ClockItem();
            item.setStaffID(DeliveryApplication.staffID);
            item.setTimeStamp(curTime);
            item.setClockStatus("Clock OFF");

            clockDB.addClock(item);
        }

    }

    @OnClick(R.id.btn_lunch)
    void onClickBtnLunch() {
        ClockDB clockDB = new ClockDB(this);
        String curTime = DateUtil.getCurDateTime();

        if(!SharedPrefManager.getInstance(this).getLunchStatus()) {
            btnLunch.setBackground(getResources().getDrawable(R.drawable.button_complete));
            btnLunch.setText(getResources().getString(R.string.btn_lunch_off));
            SharedPrefManager.getInstance(this).saveLunchStatus(true);

            ClockTask task = new ClockTask();
            task.execute(curTime, "lunchON");

            ClockItem item = new ClockItem();
            item.setStaffID(DeliveryApplication.staffID);
            item.setTimeStamp(curTime);
            item.setClockStatus("Lunch ON");

            clockDB.addClock(item);
        } else {
            btnLunch.setBackground(getResources().getDrawable(R.drawable.button_remove));
            btnLunch.setText(getResources().getString(R.string.btn_lunch_on));
            SharedPrefManager.getInstance(this).saveLunchStatus(false);

            ClockTask task = new ClockTask();
            task.execute(curTime, "lunchOFF");

            ClockItem item = new ClockItem();
            item.setStaffID(DeliveryApplication.staffID);
            item.setTimeStamp(curTime);
            item.setClockStatus("Lunch OFF");

            clockDB.addClock(item);
        }

//        refreshItems();
    }

    private void refreshItems() {
        /*ClockDB clockDB = new ClockDB(ClockActivity.this);
        clockItems = clockDB.fetchAllClockItem();*/
        progressDialog.show();

        GetClockHistoryTask task = new GetClockHistoryTask();
        task.execute();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void networkError() {
        Toast.makeText(ClockActivity.this, getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
    }
}
