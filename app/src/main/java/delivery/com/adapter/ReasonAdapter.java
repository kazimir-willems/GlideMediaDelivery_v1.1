package delivery.com.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import delivery.com.R;
import delivery.com.consts.StateConsts;
import delivery.com.model.OutletItem;
import delivery.com.ui.OutletActivity;
import delivery.com.ui.ReasonActivity;
import delivery.com.ui.StockActivity;

public class ReasonAdapter extends RecyclerView.Adapter<ReasonAdapter.ReasonViewHolder> {

    private ReasonActivity parent;
    private String[] items;
    private int lastCheckedPosition = 11;

    public ReasonAdapter(ReasonActivity parent, String[] reasons, int reason) {
        this.parent = parent;
        this.items = reasons;
        lastCheckedPosition = reason - 1;
    }

    @Override
    public ReasonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_reason, parent, false);
        return new ReasonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReasonViewHolder holder, int position) {
        final String item = items[position];

        holder.tvReason.setText(item);
        holder.btnRadio.setChecked(position == lastCheckedPosition);
        holder.btnRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastCheckedPosition = holder.getAdapterPosition();
                notifyItemRangeChanged(0, items.length);
            }
        });
        holder.reasonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastCheckedPosition = holder.getAdapterPosition();
                notifyItemRangeChanged(0, items.length);
            }
        });

    }

    public int getSelectedPos() {
        return lastCheckedPosition;
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public class ReasonViewHolder extends RecyclerView.ViewHolder {
        public final View view;

        @Bind(R.id.reason_layout)
        LinearLayout reasonLayout;
        @Bind(R.id.btn_radio)
        RadioButton btnRadio;
        @Bind(R.id.tv_reason)
        TextView tvReason;

        public ReasonViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}
