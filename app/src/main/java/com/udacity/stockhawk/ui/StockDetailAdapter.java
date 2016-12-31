package com.udacity.stockhawk.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.model.DetailViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by venugopalraog on 12/16/16.
 */
public class StockDetailAdapter extends BaseAdapter {

    private Context context;
    private List<DetailViewModel> viewModelList;

    public StockDetailAdapter(Context context) {
        this.context = context;
    }

    public void setViewModelList(List<DetailViewModel> viewModelList) {
        this.viewModelList = viewModelList;
    }

    @Override
    public int getCount() {
        return viewModelList != null ? viewModelList.size() : 0 ;
    }

    @Override
    public DetailViewModel getItem(int pos) {
        return viewModelList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_stock_detail, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.bindData(getItem(pos));

        return view;
    }

    public class ViewHolder {

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.value)
        TextView value;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

        public void bindData(DetailViewModel viewModel) {
            title.setText(viewModel.getTitle());
            title.setContentDescription(viewModel.getTitle());

            value.setText(viewModel.getValue());
            value.setContentDescription(viewModel.getValue());
        }
    }
}
