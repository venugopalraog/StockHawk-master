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

import com.github.mikephil.charting.charts.LineChart;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by venugopalraog on 11/25/16.
 */
public class StockDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int STOCK_DETAIL_LOADER = 1;

    private String mStockSymbol;

    @BindView(R.id.stock_detail_chart)
    LineChart mLineChart;


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
        super.onViewCreated(view, savedInstanceState);
        loadScreenData();
    }

    private void loadScreenData() {
        if (mStockSymbol != null) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.STOCK_SYMBOL, mStockSymbol);
            getLoaderManager().initLoader(STOCK_DETAIL_LOADER, bundle, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        String selection = Contract.Quote.COLUMN_SYMBOL + "=?";
        String[] selectionArgs = { bundle.getString(Constants.STOCK_SYMBOL) };

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
        String history = null;
        while (cursor.moveToNext()) {
            history = cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_HISTORY));
        }
        cursor.close();

        Timber.d("History Data:: %s", history);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Timber.d("onLoaderReset");
    }
}
