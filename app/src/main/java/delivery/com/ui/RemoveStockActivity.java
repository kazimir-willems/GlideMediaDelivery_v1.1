package delivery.com.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import delivery.com.R;
import delivery.com.adapter.ReasonAdapter;
import delivery.com.adapter.RemoveStockAdapter;
import delivery.com.db.RemoveStockDB;
import delivery.com.model.DespatchItem;
import delivery.com.model.OutletItem;
import delivery.com.model.RemoveStockItem;

public class RemoveStockActivity extends AppCompatActivity {

    @Bind(R.id.remove_stock_list)
    RecyclerView removeStockList;
    @Bind(R.id.tv_outlet)
    TextView tvOutlet;
    @Bind(R.id.tv_outlet_id)
    TextView tvOutletID;
    @Bind(R.id.tv_service)
    TextView tvService;
    @Bind(R.id.tv_address)
    TextView tvAddress;

    private OutletItem outletItem;
    private ArrayList<RemoveStockItem> removeStockItems = new ArrayList<>();
    private RemoveStockAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;
    private DespatchItem despatchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_stock);

        ButterKnife.bind(this);

        outletItem = (OutletItem) getIntent().getSerializableExtra("outlet");
        despatchItem = (DespatchItem) getIntent().getSerializableExtra("despatch");

        tvOutlet.setText(outletItem.getOutlet());
        tvOutletID.setText("[" + outletItem.getOutletId() + "]");
        tvService.setText(outletItem.getServiceType());
        tvAddress.setText(outletItem.getAddress());

        removeStockList.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(RemoveStockActivity.this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        removeStockList.setLayoutManager(mLinearLayoutManager);
        removeStockList.addItemDecoration(new DividerItemDecoration(RemoveStockActivity.this, DividerItemDecoration.VERTICAL_LIST));

        RemoveStockDB removeStockDB = new RemoveStockDB(RemoveStockActivity.this);
        removeStockItems = removeStockDB.fetchRemoveStocks(outletItem.getDespatchId(), outletItem.getOutletId());

        adapter = new RemoveStockAdapter(RemoveStockActivity.this);
        removeStockList.setAdapter(adapter);

        adapter.addItems(removeStockItems);

        adapter.notifyDataSetChanged();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.btn_complete)
    void onClickBtnComplete() {
        Intent intent = new Intent(RemoveStockActivity.this, StockActivity.class);
        intent.putExtra("outlet", outletItem);
        intent.putExtra("despatch", despatchItem);

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
