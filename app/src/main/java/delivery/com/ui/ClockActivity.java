package delivery.com.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import delivery.com.R;
import delivery.com.adapter.ClockAdapter;
import delivery.com.adapter.RemoveStockAdapter;
import delivery.com.application.DeliveryApplication;
import delivery.com.db.ClockDB;
import delivery.com.db.RemoveStockDB;
import delivery.com.model.ClockItem;
import delivery.com.model.RemoveStockItem;
import delivery.com.task.ClockTask;
import delivery.com.util.DateUtil;
import delivery.com.util.SharedPrefManager;

public class ClockActivity extends AppCompatActivity {

    @Bind(R.id.btn_clock)
    Button btnClock;
    @Bind(R.id.btn_lunch)
    Button btnLunch;
    @Bind(R.id.clock_list)
    RecyclerView clockList;

    private ArrayList<ClockItem> clockItems = new ArrayList<>();
    private ClockAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;

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

            ClockTask task = new ClockTask();
            task.execute(curTime, "clockOFF");

            ClockItem item = new ClockItem();
            item.setStaffID(DeliveryApplication.staffID);
            item.setTimeStamp(curTime);
            item.setClockStatus("Clock OFF");

            clockDB.addClock(item);
        }

        refreshItems();
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

        refreshItems();
    }

    private void refreshItems() {
        ClockDB clockDB = new ClockDB(ClockActivity.this);
        clockItems = clockDB.fetchAllClockItem();

        adapter.addItems(clockItems);

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
