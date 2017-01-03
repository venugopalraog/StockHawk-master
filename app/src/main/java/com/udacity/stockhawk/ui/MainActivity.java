package com.udacity.stockhawk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.common.Constants;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements StockListFragment.onListItemClicked {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        ButterKnife.bind(this);

        if (findViewById(R.id.stock_detail_container) != null) {
            mTwoPane = true;

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.stock_detail_container, new StockDetailFragment(), StockDetailFragment.class.getSimpleName())
                        .commit();
            }
        } else {
            mTwoPane = false;
        }
    }

    public void button(View view) {
        new AddStockDialog().show(getFragmentManager(), "StockDialogFragment");
    }

    void addStock(String symbol) {
        StockListFragment fragment = (StockListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_stock_list);
        fragment.addStock(symbol);
    }

    @Override
    public void onItemClicked(String symbol) {
        if (mTwoPane) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.stock_detail_container, StockDetailFragment.newInstance(symbol), StockDetailFragment.class.getSimpleName())
                    .commit();
        } else {
            Intent detailsIntent = new Intent(this, StockDetailActivity.class);
            detailsIntent.putExtra(Constants.STOCK_SYMBOL, symbol);
            startActivity(detailsIntent);
        }
    }

    @Override
    public void onRefresDetailView(String symbol) {
        if (mTwoPane) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.stock_detail_container, StockDetailFragment.newInstance(symbol), StockDetailFragment.class.getSimpleName())
                    .commit();
        }
    }
}
