package com.udacity.stockhawk.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.ui.MainActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by gubbave on 12/29/2016.
 */

public class StockWidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private int appWidgetId;
    private Cursor cursor;

    private DecimalFormat dollarFormatWithPlus;
    private DecimalFormat dollarFormat;
    private DecimalFormat percentageFormat;


    public StockWidgetViewsFactory(Context context, Intent intent) {
        mContext = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                                        AppWidgetManager.INVALID_APPWIDGET_ID);

        dollarFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dollarFormatWithPlus = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dollarFormatWithPlus.setPositivePrefix("+$");
        percentageFormat = (DecimalFormat) NumberFormat.getPercentInstance(Locale.getDefault());
        percentageFormat.setMaximumFractionDigits(2);
        percentageFormat.setMinimumFractionDigits(2);
        percentageFormat.setPositivePrefix("+");
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Widget :: onCreate");
        cursor = mContext.getContentResolver().query(Contract.Quote.uri,
                                                    Contract.Quote.QUOTE_COLUMNS,
                                                    null, null, Contract.Quote.COLUMN_SYMBOL);
    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();

        if (cursor != null) {
            cursor.close();
        }
        Log.d(TAG, "Widget :: onDataSetChanged");
        cursor = mContext.getContentResolver().query(Contract.Quote.uri,
                                            Contract.Quote.QUOTE_COLUMNS,
                                            null, null, Contract.Quote.COLUMN_SYMBOL);

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        Log.d(TAG, "Widget :: getViewAt : " + position);

        RemoteViews row = new RemoteViews(mContext.getPackageName(),
                R.layout.list_item_quote_widget);
        cursor.moveToPosition(position);

        float rawAbsoluteChange = cursor.getFloat(Contract.Quote.POSITION_ABSOLUTE_CHANGE);
        float percentageChange = cursor.getFloat(Contract.Quote.POSITION_PERCENTAGE_CHANGE);

        String change = dollarFormatWithPlus.format(rawAbsoluteChange);
        String percentage = percentageFormat.format(percentageChange / 100);


        row.setTextViewText(R.id.stock_symbol, cursor.getString(Contract.Quote.POSITION_SYMBOL));
        row.setTextViewText(R.id.bid_price, dollarFormat.format(cursor.getFloat(Contract.Quote.POSITION_PRICE)));
        row.setTextViewText(R.id.changePercentage, percentage);
        row.setTextViewText(R.id.change, change);

        if (rawAbsoluteChange > 0) {
            row.setInt(R.id.stock_quote_container, "setBackgroundResource", R.drawable.percent_change_pill_green);
        } else {
            row.setInt(R.id.stock_quote_container, "setBackgroundResource", R.drawable.percent_change_pill_red);
        }

        Intent clickIntent = new Intent(mContext, MainActivity.class);
        row.setOnClickFillInIntent(R.id.widget_row, clickIntent);

        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
