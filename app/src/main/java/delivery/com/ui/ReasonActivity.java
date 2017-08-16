package delivery.com.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import delivery.com.R;
import delivery.com.adapter.OutletAdapter;
import delivery.com.adapter.ReasonAdapter;
import delivery.com.consts.StateConsts;
import delivery.com.db.OutletDB;
import delivery.com.fragment.StockFragment;
import delivery.com.model.OutletItem;

public class ReasonActivity extends AppCompatActivity
{
    @Bind(R.id.reason_list)
    RecyclerView reasonList;
    @Bind(R.id.tv_outlet)
    TextView tvOutlet;
    @Bind(R.id.tv_outlet_id)
    TextView tvOutletID;
    @Bind(R.id.tv_service)
    TextView tvService;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.btn_complete)
    Button btnComplete;

    private OutletItem outletItem;
    private ReasonAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reason);

        ButterKnife.bind(this);

        outletItem = (OutletItem) getIntent().getSerializableExtra("outlet");

        tvOutlet.setText(outletItem.getOutlet());
        tvOutletID.setText("[" + outletItem.getOutletId() + "]");
        tvService.setText(outletItem.getServiceType());
        tvAddress.setText(outletItem.getAddress());
        tvAddress.setSelected(true);

        if(outletItem.getDelivered() != StateConsts.OUTLET_NOT_DELIVERED) {
            btnComplete.setBackground(getResources().getDrawable(R.drawable.button_complete));
            btnComplete.setText(getResources().getText(R.string.btn_reset));
        } else {
            btnComplete.setBackground(getResources().getDrawable(R.drawable.button_remove));
            btnComplete.setText(getResources().getText(R.string.btn_complete));
        }

        reasonList.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(ReasonActivity.this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        reasonList.setLayoutManager(mLinearLayoutManager);
//        reasonList.addItemDecoration(new DividerItemDecoration(ReasonActivity.this, DividerItemDecoration.VERTICAL_LIST));

        String[] reasons = getResources().getStringArray(R.array.outlet_reason);

        adapter = new ReasonAdapter(ReasonActivity.this, reasons, outletItem.getReason());
        reasonList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_save)
    void onClickBtnSave() {
        int position = adapter.getSelectedPos();
        outletItem.setReason(position + 1);

        OutletDB outletDB = new OutletDB(ReasonActivity.this);
        outletDB.updateOutlet(outletItem);

        finish();
    }

    @OnClick(R.id.btn_complete)
    void onClickBtnComplete() {
        if(outletItem.getDelivered() == StateConsts.OUTLET_NOT_DELIVERED) {
            int position = adapter.getSelectedPos();
            outletItem.setReason(position + 1);
            outletItem.setDelivered(StateConsts.OUTLET_CANNOT_DELIVER);

            OutletDB outletDB = new OutletDB(ReasonActivity.this);
            outletDB.updateOutlet(outletItem);
            outletDB.updateOutlet(outletItem);
        } else {
            OutletDB outletDB = new OutletDB(ReasonActivity.this);
            outletItem.setDelivered(StateConsts.OUTLET_NOT_DELIVERED);
            outletDB.updateOutlet(outletItem);
        }

        finish();
    }

}