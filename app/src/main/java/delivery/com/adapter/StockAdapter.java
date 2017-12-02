package delivery.com.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import delivery.com.R;
import delivery.com.consts.StateConsts;
import delivery.com.fragment.StockFragment;
import delivery.com.model.OutletItem;
import delivery.com.model.StockItem;
import delivery.com.ui.OutletActivity;
import delivery.com.ui.StockActivity;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder> {

    private StockFragment parent;
    private List<StockItem> items = new ArrayList<>();

    public StockAdapter(StockFragment parent) {
        this.parent = parent;
    }

    @Override
    public StockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_stock, parent, false);
        return new StockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StockViewHolder holder, int position) {
        if(position % 2 == 0) {
            holder.totalLayout.setBackgroundColor(parent.getResources().getColor(R.color.colorLightGray));
        } else {
            holder.totalLayout.setBackgroundColor(parent.getResources().getColor(R.color.colorWhite));
        }
        final StockItem item = items.get(position);

        holder.tvSlot.setText(String.valueOf(item.getSlotOrder()));
        holder.tvEmptySlot.setText(String.valueOf(item.getSlotOrder()));
        holder.tvStock.setText(item.getStock().replaceAll("&amp;", "&"));
        holder.tvTitleID.setText("[" + item.getTitleID() + "]");
        holder.tvSize.setText("[" + item.getSize() + "]");
        holder.tvStock.setSelected(true);
        holder.tvStockStatus.setText(item.getStatus());

        holder.stockLayout.setVisibility(View.VISIBLE);
        holder.removeLayout.setVisibility(View.GONE);
        holder.emptyLayout.setVisibility(View.GONE);

        if(item.getStatus().equals("In Stock")) {
            holder.tvStockStatus.setTextColor(parent.getResources().getColor(R.color.colorSimpleGray));
        } else if(item.getStatus().equals("Out of Stock")){
            holder.tvStockStatus.setTextColor(parent.getResources().getColor(R.color.colorRemove));
        } else if(item.getStatus().equals("New Stock")) {
            holder.tvStockStatus.setTextColor(parent.getResources().getColor(R.color.colorGreen));
        }else {
            holder.stockLayout.setVisibility(View.GONE);
            holder.removeLayout.setVisibility(View.GONE);
            holder.emptyLayout.setVisibility(View.VISIBLE);
        }

        if(!item.getRemove().isEmpty()) {
            holder.removeLayout.setVisibility(View.VISIBLE);
            holder.tvRemove.setText(item.getRemove());
            holder.tvRemoveID.setText("[" + item.getRemoveID() + "]");
            holder.tvRemove.setSelected(true);
        } else {
            holder.removeLayout.setVisibility(View.GONE);
        }

        holder.setBtnsDefault();

        switch(item.getQty()) {
            case 1:
                holder.btnFull.setBackground(parent.getResources().getDrawable(R.drawable.qty_bg_full));
                holder.btnFull.setTextColor(parent.getResources().getColor(R.color.colorWhite));
                break;
            case 2:
                holder.btnRestock.setBackground(parent.getResources().getDrawable(R.drawable.qty_bg_restock));
                holder.btnRestock.setTextColor(parent.getResources().getColor(R.color.colorWhite));
                break;
            case 3:
                holder.btnNone.setBackground(parent.getResources().getDrawable(R.drawable.qty_bg_none));
                holder.btnNone.setTextColor(parent.getResources().getColor(R.color.colorWhite));
                break;
            case 4:
                holder.btnMissing.setBackground(parent.getResources().getDrawable(R.drawable.qty_bg_missing));
                holder.btnMissing.setTextColor(parent.getResources().getColor(R.color.colorWhite));
                break;
        }

        holder.btnFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.setBtnsDefault();
                holder.btnFull.setBackground(parent.getResources().getDrawable(R.drawable.qty_bg_full));
                holder.btnFull.setTextColor(parent.getResources().getColor(R.color.colorWhite));
                item.setQty(StateConsts.STOCK_QTY_FULL);
                parent.updateStockQty(item);
            }
        });

        holder.btnRestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.setBtnsDefault();
                holder.btnRestock.setBackground(parent.getResources().getDrawable(R.drawable.qty_bg_restock));
                holder.btnRestock.setTextColor(parent.getResources().getColor(R.color.colorWhite));
                item.setQty(StateConsts.STOCK_QTY_RESTOCK);
                parent.updateStockQty(item);
            }
        });

        holder.btnNone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.setBtnsDefault();
                holder.btnNone.setBackground(parent.getResources().getDrawable(R.drawable.qty_bg_none));
                holder.btnNone.setTextColor(parent.getResources().getColor(R.color.colorWhite));
                item.setQty(StateConsts.STOCK_QTY_NONE);
                parent.updateStockQty(item);
            }
        });

        holder.btnMissing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.setBtnsDefault();
                holder.btnMissing.setBackground(parent.getResources().getDrawable(R.drawable.qty_bg_missing));
                holder.btnMissing.setTextColor(parent.getResources().getColor(R.color.colorWhite));
                item.setQty(StateConsts.STOCK_QTY_MISSING);
                parent.updateStockQty(item);
            }
        });

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

    public class StockViewHolder extends RecyclerView.ViewHolder {
        public final View view;

        @BindView(R.id.total_layout)
        LinearLayout totalLayout;
        @BindView(R.id.stock_layout)
        RelativeLayout stockLayout;
        @BindView(R.id.remove_layout)
        LinearLayout removeLayout;
        @BindView(R.id.tv_slot)
        TextView tvSlot;
        @BindView(R.id.tv_stock)
        TextView tvStock;
        @BindView(R.id.tv_title_id)
        TextView tvTitleID;
        @BindView(R.id.tv_size)
        TextView tvSize;
        @BindView(R.id.tv_stock_status)
        TextView tvStockStatus;
        @BindView(R.id.tv_remove)
        TextView tvRemove;
        @BindView(R.id.tv_remove_id)
        TextView tvRemoveID;
        @BindView(R.id.btn_full)
        Button btnFull;
        @BindView(R.id.btn_restock)
        Button btnRestock;
        @BindView(R.id.btn_none)
        Button btnNone;
        @BindView(R.id.btn_missing)
        Button btnMissing;
        @BindView(R.id.tv_empty_slot)
        TextView tvEmptySlot;
        @BindView(R.id.empty_layout)
        LinearLayout emptyLayout;

        public StockViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }

        public void setBtnsDefault() {
            btnFull.setBackground(parent.getResources().getDrawable(R.drawable.qty_bg_default));
            btnRestock.setBackground(parent.getResources().getDrawable(R.drawable.qty_bg_default));
            btnNone.setBackground(parent.getResources().getDrawable(R.drawable.qty_bg_default));
            btnMissing.setBackground(parent.getResources().getDrawable(R.drawable.qty_bg_default));
            btnFull.setTextColor(parent.getResources().getColor(R.color.colorBlack));
            btnRestock.setTextColor(parent.getResources().getColor(R.color.colorBlack));
            btnNone.setTextColor(parent.getResources().getColor(R.color.colorBlack));
            btnMissing.setTextColor(parent.getResources().getColor(R.color.colorBlack));
        }
    }
}
