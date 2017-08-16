package delivery.com.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import delivery.com.R;
import delivery.com.consts.StateConsts;
import delivery.com.fragment.DespatchFragment;
import delivery.com.model.DespatchItem;

public class DespatchAdapter extends RecyclerView.Adapter<DespatchAdapter.DespatchViewHolder> {

    private DespatchFragment parent;
    private List<DespatchItem> items = new ArrayList<>();

    public DespatchAdapter(DespatchFragment parent) {
        this.parent = parent;
    }

    @Override
    public DespatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_despatch, parent, false);
        return new DespatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DespatchViewHolder holder, int position) {
        final DespatchItem item = items.get(position);

        holder.tvDespatchId.setText(item.getDespatchId());
        holder.tvRun.setText(item.getRunId());
        holder.tvDriverName.setText(item.getDriverName());

        holder.setIcon(item.getCompleted());
        holder.setTVColor(item.getCompleted());

        String strOutletCompleted = String.format(parent.getResources().getString(R.string.outlet_complete_format), item.getnCntCompleted(), item.getnCntAll());
        holder.tvOutletCompleted.setText(strOutletCompleted);

        if(item.getCompleted() == StateConsts.DESPATCH_DEFAULT) {
//            holder.ivMark.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    parent.removeDespatch(item);
//                }
//            });
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parent.removeDespatch(item);
                }
            });
        } else if(item.getCompleted() == StateConsts.DESPATCH_COMPLETED) {
            holder.ivMark.setOnClickListener(null);
        }

        holder.despatchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.showOutletActivity(item);
            }
        });

    }

    public DespatchItem getItem(int pos) {
        return items.get(pos);
    }

    public void clearItems() {
        items.clear();
    }

    public void addItem(DespatchItem item) {
        items.add(item);
    }

    public void addItems(ArrayList<DespatchItem> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class DespatchViewHolder extends RecyclerView.ViewHolder {
        public final View view;

        @Bind(R.id.despatch_layout)
        LinearLayout despatchLayout;
        @Bind(R.id.title_despatch)
        TextView tvTitleDespatch;
        @Bind(R.id.tv_despatch_id)
        TextView tvDespatchId;
        @Bind(R.id.title_run)
        TextView tvTitleRun;
        @Bind(R.id.tv_run)
        TextView tvRun;
        @Bind(R.id.title_driver)
        TextView tvTitleDriver;
        @Bind(R.id.tv_driver_name)
        TextView tvDriverName;
        @Bind(R.id.tv_outlet_completed)
        TextView tvOutletCompleted;
        @Bind(R.id.btn_delete)
        Button btnDelete;
        @Bind(R.id.iv_mark)
        ImageView ivMark;

        public DespatchViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }

        public void setTVColor(int completed) {
            if(completed == StateConsts.DESPATCH_COMPLETED) {
                tvDespatchId.setTextColor(parent.getResources().getColor(R.color.colorWhite));
                tvRun.setTextColor(parent.getResources().getColor(R.color.colorWhite));
                tvDriverName.setTextColor(parent.getResources().getColor(R.color.colorWhite));
                tvTitleDespatch.setTextColor(parent.getResources().getColor(R.color.colorWhite));
                tvTitleRun.setTextColor(parent.getResources().getColor(R.color.colorWhite));
                tvTitleDriver.setTextColor(parent.getResources().getColor(R.color.colorWhite));
                tvOutletCompleted.setTextColor(parent.getResources().getColor(R.color.colorWhite));
            } else {
                tvDespatchId.setTextColor(parent.getResources().getColor(R.color.colorBlack));
                tvRun.setTextColor(parent.getResources().getColor(R.color.colorBlack));
                tvDriverName.setTextColor(parent.getResources().getColor(R.color.colorBlack));
                tvTitleDespatch.setTextColor(parent.getResources().getColor(R.color.colorBlack));
                tvTitleRun.setTextColor(parent.getResources().getColor(R.color.colorBlack));
                tvTitleDriver.setTextColor(parent.getResources().getColor(R.color.colorBlack));
                tvOutletCompleted.setTextColor(parent.getResources().getColor(R.color.colorBlack));
            }
        }

        public void setIcon(int completed) {
            if(completed == StateConsts.DESPATCH_COMPLETED) {
                despatchLayout.setBackgroundDrawable(parent.getResources().getDrawable(R.drawable.despatch_bg_complete));
                ivMark.setBackground(parent.getResources().getDrawable(R.drawable.ic_complete));
                ivMark.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.GONE);
            } else {
                despatchLayout.setBackgroundDrawable(parent.getResources().getDrawable(R.drawable.despatch_bg_default));
                ivMark.setBackground(parent.getResources().getDrawable(R.drawable.ic_delete));
                ivMark.setVisibility(View.GONE);
                btnDelete.setVisibility(View.VISIBLE);
            }
        }
    }
}
