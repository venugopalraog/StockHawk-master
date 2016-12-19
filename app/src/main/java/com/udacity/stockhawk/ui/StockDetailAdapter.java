package com.udacity.stockhawk.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.model.DetailViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by venugopalraog on 12/16/16.
 */
public class StockDetailAdapter extends RecyclerView.Adapter<StockDetailAdapter.DetailViewHolder> {

    private Context context;
    private List<DetailViewModel> viewModelList;

    public StockDetailAdapter(Context context) {
        this.context = context;
    }

    public void setViewModelList(List<DetailViewModel> viewModelList) {
        this.viewModelList = viewModelList;
    }

    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.list_item_stock_detail, parent, false);
        return new DetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DetailViewHolder holder, int position) {
        DetailViewModel viewModel = getItem(position);

        holder.title.setText(viewModel.getTitle());
        holder.value.setText(viewModel.getValue());
    }

    @Override
    public int getItemCount() {
        return viewModelList != null ? viewModelList.size() : 0;
    }

    public DetailViewModel getItem(int pos) {
        return viewModelList.get(pos);
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.value)
        TextView value;

        public DetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
