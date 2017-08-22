package delivery.com.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import delivery.com.R;
import delivery.com.consts.StateConsts;
import delivery.com.model.OutletItem;
import delivery.com.model.RemoveStockItem;
import delivery.com.ui.OutletActivity;
import delivery.com.ui.ReasonActivity;
import delivery.com.ui.RemoveStockActivity;
import delivery.com.ui.StockActivity;

public class RemoveStockAdapter extends RecyclerView.Adapter<RemoveStockAdapter.RemoveStockViewHolder> {

    private RemoveStockActivity parent;
    private List<RemoveStockItem> items = new ArrayList<>();

    public RemoveStockAdapter(RemoveStockActivity parent) {
        this.parent = parent;
    }

    @Override
    public RemoveStockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_remove_stock, parent, false);
        return new RemoveStockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RemoveStockViewHolder holder, int position) {
        final RemoveStockItem item = items.get(position);

        holder.tvTitleID.setText("[" + item.getTitleID() + "]");
        holder.tvTitle.setText(item.getTitle().replaceAll("&amp;", "&"));
        holder.tvTitle.setSelected(true);
        holder.tvRowNum.setText(String.valueOf(position + 1));
    }

    public RemoveStockItem getItem(int pos) {
        return items.get(pos);
    }

    public void clearItems() {
        items.clear();
    }

    public void addItem(RemoveStockItem item) {
        items.add(item);
    }

    public void addItems(ArrayList<RemoveStockItem> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RemoveStockViewHolder extends RecyclerView.ViewHolder {
        public final View view;

        @Bind(R.id.tv_title_id)
        TextView tvTitleID;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_row_num)
        TextView tvRowNum;

        public RemoveStockViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}
