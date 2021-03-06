package delivery.com.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import delivery.com.R;
import delivery.com.application.DeliveryApplication;
import delivery.com.consts.StateConsts;
import delivery.com.db.OutletDB;
import delivery.com.db.StockDB;
import delivery.com.db.TierDB;
import delivery.com.fragment.DespatchFragment;
import delivery.com.fragment.HomeFragment;
import delivery.com.fragment.StockFragment;
import delivery.com.model.DespatchItem;
import delivery.com.model.OutletItem;
import delivery.com.model.RemoveStockItem;
import delivery.com.model.StockItem;
import delivery.com.model.TierItem;
import delivery.com.util.DateUtil;

public class StockActivity extends AppCompatActivity
{
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tv_outlet)
    TextView tvOutlet;
    @BindView(R.id.tv_outlet_id)
    TextView tvOutletID;
    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.btn_complete)
    Button btnComplete;

    private OutletItem outletItem;
    private int tiers = 0;
    private StockFragment[] fragments;

    private DespatchItem despatchItem;

    private ArrayList<StockItem> tempOperationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        ButterKnife.bind(this);

        outletItem = (OutletItem) getIntent().getSerializableExtra("outlet");
        despatchItem = (DespatchItem) getIntent().getSerializableExtra("despatch");
        tiers = outletItem.getTiers();
        tvOutlet.setText(outletItem.getOutlet());
        tvOutletID.setText("[" + outletItem.getOutletId() + "]");
        tvService.setText(outletItem.getServiceType());
        tvAddress.setText(outletItem.getAddress());

        /*if(outletItem.getDelivered() != StateConsts.OUTLET_NOT_DELIVERED) {
            btnComplete.setBackground(getResources().getDrawable(R.drawable.button_complete));
            btnComplete.setText(getResources().getText(R.string.btn_reset));
        } else {
            btnComplete.setBackground(getResources().getDrawable(R.drawable.button_remove));
            btnComplete.setText(getResources().getText(R.string.btn_complete));
        }*/

        TierDB tierDB = new TierDB(this);
        ArrayList<TierItem> tierItems = tierDB.fetchTiers(outletItem.getDespatchId(), outletItem.getOutletId(), outletItem.getOrderId());

        fragments = new StockFragment[tiers];
        for(int i = 0; i < tierItems.size(); i++) {
            fragments[i] = new StockFragment().newInstance(outletItem, tierItems.get(i).getTierNo());
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(tiers);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            return fragments[pos];
        }

        @Override
        public int getCount() {
            return tiers;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /*@OnClick(R.id.btn_save)
    void onClickBtnSave() {
        StockDB stockDB = new StockDB(StockActivity.this);
        OutletDB outletDB = new OutletDB(StockActivity.this);
        for (int i = 0; i < tempOperationList.size(); i++) {
            stockDB.updateStock(tempOperationList.get(i));
        }

        outletItem.setDelivered(StateConsts.OUTLET_NOT_DELIVERED);
        outletItem.setDeliveredTime(DateUtil.getCurDateTime());
        outletDB.updateOutlet(outletItem);

        finish();
    }*/

    @OnClick(R.id.btn_full)
    void onClickBtnFull() {
        for(int i = 0; i < fragments.length; i++) {
            fragments[i].performFull();
        }
    }

    @OnClick(R.id.btn_complete)
    void onClickBtnComplete() {
//        if(outletItem.getDelivered() == StateConsts.OUTLET_NOT_DELIVERED) {

        Intent intent = new Intent(StockActivity.this, AddStockActivity.class);
        intent.putExtra("outlet", outletItem);
        intent.putExtra("despatch", despatchItem);

        DeliveryApplication.operationLists = tempOperationList;

        startActivity(intent);

            /*StockDB stockDB = new StockDB(StockActivity.this);
            OutletDB outletDB = new OutletDB(StockActivity.this);
            for (int i = 0; i < tempOperationList.size(); i++) {
                stockDB.updateStock(tempOperationList.get(i));
            }

            outletItem.setDelivered(StateConsts.OUTLET_DELIVERED);
            outletItem.setDeliveredTime(DateUtil.getCurDateTime());
            outletDB.updateOutlet(outletItem);*/
//        } else {
//            OutletDB outletDB = new OutletDB(StockActivity.this);
//            outletItem.setDelivered(StateConsts.OUTLET_NOT_DELIVERED);
//            outletDB.updateOutlet(outletItem);
//        }
    }

    public void addOperation(StockItem item) {
        int pos = isExistingOperation(item);
        if(pos != -1) {
            tempOperationList.remove(pos);
            if(item.getQty() != StateConsts.STOCK_QTY_NONE)
                tempOperationList.add(item);
        } else {
            if(item.getQty() != StateConsts.STOCK_QTY_NONE)
                tempOperationList.add(item);
        }
    }

    private int isExistingOperation(StockItem item) {
        for(int i = 0; i < tempOperationList.size(); i++) {
            if(tempOperationList.get(i).getStockId().equals(item.getStockId())) {
                return i;
            }
        }
        return -1;
    }

}