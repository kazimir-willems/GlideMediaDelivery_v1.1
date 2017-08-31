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
import delivery.com.consts.StateConsts;
import delivery.com.model.RemoveStockItem;
import delivery.com.model.StockItem;
import delivery.com.ui.AddStockActivity;
import delivery.com.ui.RemoveStockActivity;

public class AddStockAdapter extends RecyclerView.Adapter<AddStockAdapter.AddStockViewHolder> {

    private AddStockActivity parent;
    private List<StockItem> items = new ArrayList<>();

    public AddStockAdapter(AddStockActivity parent) {
        this.parent = parent;
    }

    @Override
    public AddStockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_add_stock, parent, false);
        return new AddStockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AddStockViewHolder holder, int position) {
        final StockItem item = items.get(position);

        holder.tvTitleID.setText("[" + item.getTitleID() + "]");
        holder.tvStock.setText(item.getStock().replaceAll("&amp;", "&"));
        holder.tvStock.setSelected(true);
        holder.tvRowNum.setText("ROW " + String.valueOf(Integer.valueOf(item.getTier()) + 1));
        holder.tvSlotNum.setText("SLOT " + String.valueOf(item.getSlotOrder()));
        holder.tvSlot.setText(String.valueOf(position + 1));

        if(item.getStatus().equals("New Stock")) {
            holder.tvStatus.setVisibility(View.VISIBLE);
        } else {
            holder.tvStatus.setVisibility(View.GONE);
        }
        switch(item.getQty()) {
            case StateConsts.STOCK_QTY_FULL:
                holder.tvStockStatus.setText("FULL");
                holder.tvStockStatus.setTextColor(parent.getResources().getColor(R.color.colorLightBlue));
                break;
            case StateConsts.STOCK_QTY_RESTOCK:
                holder.tvStockStatus.setText("RESTOCK");
                holder.tvStockStatus.setTextColor(parent.getResources().getColor(R.color.colorGreen));
                break;
            case StateConsts.STOCK_QTY_MISSING:
                holder.tvStockStatus.setTextColor(parent.getResources().getColor(R.color.colorRemove));
                holder.tvStockStatus.setText("MISSING");
                break;
        }
        holder.tvSize.setText("[" + item.getSize() + "]");
    }

    public StockItem getItem(int pos) {
        return items.get(pos);
    }

    public void clearItems() {
        items.clear();
    }

    public void addItem(StockItem item) {
        items.add(item);
    }

    public void addItems(ArrayList<StockItem> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class AddStockViewHolder extends RecyclerView.ViewHolder {
        public final View view;

        @Bind(R.id.tv_title_id)
        TextView tvTitleID;
        @Bind(R.id.tv_status)
        TextView tvStatus;
        @Bind(R.id.tv_stock)
        TextView tvStock;
        @Bind(R.id.tv_size)
        TextView tvSize;
        @Bind(R.id.tv_stock_status)
        TextView tvStockStatus;
        @Bind(R.id.tv_row_num)
        TextView tvRowNum;
        @Bind(R.id.tv_slot_num)
        TextView tvSlotNum;
        @Bind(R.id.tv_slot)
        TextView tvSlot;

        public AddStockViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}
