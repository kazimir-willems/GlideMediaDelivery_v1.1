package delivery.com.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import delivery.com.R;
import delivery.com.adapter.AddStockAdapter;
import delivery.com.adapter.RemoveStockAdapter;
import delivery.com.application.DeliveryApplication;
import delivery.com.consts.StateConsts;
import delivery.com.db.OutletDB;
import delivery.com.db.RemoveStockDB;
import delivery.com.db.StockDB;
import delivery.com.model.DespatchItem;
import delivery.com.model.OutletItem;
import delivery.com.model.RemoveStockItem;
import delivery.com.model.StockItem;
import delivery.com.util.DateUtil;

public class AddStockActivity extends AppCompatActivity {

    @BindView(R.id.remove_stock_list)
    RecyclerView removeStockList;
    @BindView(R.id.tv_outlet)
    TextView tvOutlet;
    @BindView(R.id.tv_outlet_id)
    TextView tvOutletID;
    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    private OutletItem outletItem;
    private ArrayList<StockItem> addStockItems = new ArrayList<>();
    private AddStockAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;

    private DespatchItem despatchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);

        ButterKnife.bind(this);

        outletItem = (OutletItem) getIntent().getSerializableExtra("outlet");
        despatchItem = (DespatchItem) getIntent().getSerializableExtra("despatch");

        tvOutlet.setText(outletItem.getOutlet());
        tvOutletID.setText("[" + outletItem.getOutletId() + "]");
        tvService.setText(outletItem.getServiceType());
        tvAddress.setText(outletItem.getAddress());

        removeStockList.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(AddStockActivity.this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        removeStockList.setLayoutManager(mLinearLayoutManager);
        removeStockList.addItemDecoration(new DividerItemDecoration(AddStockActivity.this, DividerItemDecoration.VERTICAL_LIST));

        adapter = new AddStockAdapter(AddStockActivity.this);
        removeStockList.setAdapter(adapter);

        adapter.addItems(DeliveryApplication.operationLists);

        adapter.notifyDataSetChanged();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.btn_complete)
    void onBtnClickComplete() {
        StockDB stockDB = new StockDB(AddStockActivity.this);
        OutletDB outletDB = new OutletDB(AddStockActivity.this);
        for (int i = 0; i < DeliveryApplication.operationLists.size(); i++) {
            stockDB.updateStock(DeliveryApplication.operationLists.get(i));
        }

        outletItem.setDelivered(StateConsts.OUTLET_DELIVERED);
        outletItem.setDeliveredTime(DateUtil.getCurDateTime());
        outletDB.updateOutlet(outletItem);

        Intent intent = new Intent(AddStockActivity.this, OutletActivity.class);

        intent.putExtra("despatch", despatchItem);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
