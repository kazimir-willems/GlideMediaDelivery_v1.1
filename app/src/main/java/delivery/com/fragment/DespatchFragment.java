package delivery.com.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import delivery.com.R;
import delivery.com.adapter.DespatchAdapter;
import delivery.com.consts.StateConsts;
import delivery.com.db.DespatchDB;
import delivery.com.db.OutletDB;
import delivery.com.db.StockDB;
import delivery.com.db.TierDB;
import delivery.com.model.DespatchItem;
import delivery.com.ui.DividerItemDecoration;
import delivery.com.ui.MainActivity;
import delivery.com.ui.OutletActivity;

public class DespatchFragment extends Fragment {

    @Bind(R.id.despatch_list)
    RecyclerView despatchList;

    private LinearLayoutManager mLinearLayoutManager;
    private DespatchAdapter adapter;

    public static DespatchFragment newInstance() {
        DespatchFragment fragment = new DespatchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_despatch, container, false);
        ButterKnife.bind(this, view);

        despatchList.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        despatchList.setLayoutManager(mLinearLayoutManager);
//        despatchList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        adapter = new DespatchAdapter(DespatchFragment.this);
        despatchList.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getDespatches();

        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(1).setChecked(true);

    }

    private void getDespatches() {
        DespatchDB db = new DespatchDB(getActivity());
        ArrayList<DespatchItem> despatches = db.fetchAllDespatches();

        OutletDB outletDB = new OutletDB(getActivity());

        for(int i = 0; i < despatches.size(); i++) {
            int allCnt = outletDB.getAllCount(despatches.get(i).getDespatchId());
            int completedCnt = outletDB.getCompletedCount(despatches.get(i).getDespatchId());

            Log.v("AllCnt", String.valueOf(allCnt));
            Log.v("CompCnt", String.valueOf(completedCnt));

            if(allCnt == completedCnt) {
                DespatchItem despatchItem = despatches.get(i);
                despatchItem.setCompleted(StateConsts.DESPATCH_COMPLETED);

                db.updateDespatch(despatchItem);
            } else {
                DespatchItem despatchItem = despatches.get(i);
                despatchItem.setCompleted(StateConsts.DESPATCH_DEFAULT);

                db.updateDespatch(despatchItem);
            }
        }

        despatches = db.fetchAllDespatches();
        for(int i = 0; i < despatches.size(); i++) {
            int allCnt = outletDB.getAllCount(despatches.get(i).getDespatchId());
            int completedCnt = outletDB.getCompletedCount(despatches.get(i).getDespatchId());

            despatches.get(i).setnCntCompleted(completedCnt);
            despatches.get(i).setnCntAll(allCnt);
        }

        adapter.clearItems();
        adapter.addItems(despatches);
        adapter.notifyDataSetChanged();
    }

    public void removeDespatch(final DespatchItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(getString(R.string.msg_ask_remove_despatch));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StockDB stockDB = new StockDB(getActivity());
                stockDB.removeDatasByDespatchID(item.getDespatchId());

                TierDB tierDB = new TierDB(getActivity());
                tierDB.removeDatasByDespatchID(item.getDespatchId());

                OutletDB outletDB = new OutletDB(getActivity());
                outletDB.removeDatasByDespatchID(item.getDespatchId());

                DespatchDB despatchDB = new DespatchDB(getActivity());
                despatchDB.removeDespatch(item);

                getDespatches();
            }
        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    public void showOutletActivity(DespatchItem item) {
        Intent intent = new Intent(getActivity(), OutletActivity.class);
        intent.putExtra("despatch", item);
        startActivity(intent);
    }

}
