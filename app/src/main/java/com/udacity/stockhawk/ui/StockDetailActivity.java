package com.udacity.stockhawk.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.udacity.stockhawk.R;

/**
 * Created by venugopalraog on 11/25/16.
 */

public class StockDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            String stockName = getIntent().getParcelableExtra(Constants.STOCK_SYMBOL);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.stock_detail_container, StockDetailFragment.newInstance(stockName), StockDetailFragment.class.getSimpleName())
                    .commit();
        }
    }
}
