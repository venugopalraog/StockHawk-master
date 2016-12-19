package com.udacity.stockhawk.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.common.Constants;
import com.udacity.stockhawk.common.LineChartUIHelper;
import com.udacity.stockhawk.common.StockDetailsConverter;
import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.model.DetailViewModel;
import com.udacity.stockhawk.model.StockDetailsModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by venugopalraog on 11/25/16.
 */
public class StockDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int STOCK_DETAIL_LOADER = 1;

    private String mStockSymbol;
    private StockDetailsModel mStockDetailModel;
    private StockDetailAdapter mAdapter;


    @BindView(R.id.stock_detail_chart)
    LineChart mLineChart;

    @BindView(R.id.stock_detail_container)
    LinearLayout mStockDetailContainer;


    public static StockDetailFragment newInstance(String stockSymbol) {
        StockDetailFragment fragment = new StockDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Constants.STOCK_SYMBOL, stockSymbol);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.mStockSymbol = getArguments().getString(Constants.STOCK_SYMBOL);
        }
        if (mStockSymbol != null) {
            getLoaderManager().initLoader(STOCK_DETAIL_LOADER, null, this);
            this.mAdapter = new StockDetailAdapter(getActivity());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock_detail, container, false);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mStockDetailModel = savedInstanceState.getParcelable(Constants.STOCK_DETAIL_DATA);
            mStockSymbol = savedInstanceState.getString(Constants.STOCK_SYMBOL);
        }
        loadScreenData();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.STOCK_DETAIL_DATA, mStockDetailModel);
        outState.putString(Constants.STOCK_SYMBOL, mStockSymbol);
        super.onSaveInstanceState(outState);
    }

    private void loadScreenData() {
        if (mStockDetailModel != null && mStockSymbol != null) {
            mStockDetailContainer.setVisibility(View.VISIBLE);
            mLineChart.setVisibility(View.VISIBLE);
            LineChartUIHelper.initLineChart(mStockDetailModel.getStockHistory(), mLineChart);

            LayoutInflater inflater = getActivity().getLayoutInflater();

            for (DetailViewModel viewModel : mStockDetailModel.getDetailsList()) {
                View itemView = inflater.inflate(R.layout.list_item_stock_detail, mStockDetailContainer, false);
                TextView title = (TextView) itemView.findViewById(R.id.title);
                title.setText(viewModel.getTitle());
                TextView value = (TextView) itemView.findViewById(R.id.value);
                value.setText(viewModel.getValue());

                mStockDetailContainer.addView(itemView);
            }

            /*
            mStockDetailContainer.setAdapter(mAdapter);
            mAdapter.setViewModelList(mStockDetailModel.getDetailsList());
            mStockDetailContainer.setLayoutManager(new LinearLayoutManager(getActivity()));*/
        } else {
            mStockDetailContainer.setVisibility(View.GONE);
            mLineChart.setVisibility(View.GONE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection = Contract.Quote.COLUMN_SYMBOL + "=?";
        String[] selectionArgs = { mStockSymbol };

        return new CursorLoader(getActivity(),
                Contract.Quote.uri,
                Contract.Quote.QUOTE_COLUMNS,
                selection,
                selectionArgs,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Timber.d("Cursor Data Length: %d", cursor.getCount());
        mStockDetailModel = StockDetailsConverter.convertStockDetail(cursor);
        loadScreenData();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Timber.d("onLoaderReset");
    }

}
