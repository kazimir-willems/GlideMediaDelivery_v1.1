package delivery.com.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import delivery.com.R;
import delivery.com.model.ClockItem;
import delivery.com.model.RemoveStockItem;
import delivery.com.ui.ClockActivity;
import delivery.com.ui.RemoveStockActivity;

public class ClockAdapter extends RecyclerView.Adapter<ClockAdapter.ClockViewHolder> {

    private ClockActivity parent;
    private List<ClockItem> items = new ArrayList<>();

    public ClockAdapter(ClockActivity parent) {
        this.parent = parent;
    }

    @Override
    public ClockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_clock, parent, false);
        return new ClockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ClockViewHolder holder, int position) {
        final ClockItem item = items.get(position);

        holder.tvDateTime.setText(item.getTimeStamp());
        holder.tvStatus.setText(item.getClockStatus());
        if(item.getClockStatus().endsWith("ON")) {
            holder.tvStatus.setTextColor(parent.getResources().getColor(R.color.colorGreen));
        } else {
            holder.tvStatus.setTextColor(parent.getResources().getColor(R.color.colorRemove));
        }
        holder.tvRowNum.setText(String.valueOf(position + 1));
    }

    public ClockItem getItem(int pos) {
        return items.get(pos);
    }

    public void clearItems() {
        items.clear();
    }

    public void addItem(ClockItem item) {
        items.add(item);
    }

    public void addItems(ArrayList<ClockItem> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ClockViewHolder extends RecyclerView.ViewHolder {
        public final View view;

        @Bind(R.id.tv_status)
        TextView tvStatus;
        @Bind(R.id.tv_datetime)
        TextView tvDateTime;
        @Bind(R.id.tv_row_num)
        TextView tvRowNum;

        public ClockViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}
