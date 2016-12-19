package com.udacity.stockhawk.common;

import android.database.Cursor;

import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.model.DetailViewModel;
import com.udacity.stockhawk.model.StockDetailsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gubbave on 12/16/2016.
 */

public class StockDetailsConverter {

    private void StockDetailsConverter() {  }

    public static StockDetailsModel convertStockDetail(Cursor cursor) {

        StockDetailsModel stockDetailsModel = null;
        StockDetailsModel.Builder builder = new StockDetailsModel.Builder();

        while (cursor.moveToNext()) {
            stockDetailsModel = builder.ask(cursor.getFloat(cursor.getColumnIndex(Contract.Quote.COLUMN_ASK)))
                    .bid(cursor.getFloat(cursor.getColumnIndex(Contract.Quote.COLUMN_BID)))
                    .bidSize(cursor.getLong(cursor.getColumnIndex(Contract.Quote.COLUMN_BID_SIZE)))
                    .lastTradeDate(cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_LAST_TRADE_DATE_STR)))
                    .lastTradeSize(cursor.getLong(cursor.getColumnIndex(Contract.Quote.COLUMN_LAST_TRADE_SIZE)))
                    .lastTradeTime(cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_LAST_TRADE_TIME_STR)))
                    .percentChange(cursor.getFloat(cursor.getColumnIndex(Contract.Quote.COLUMN_PERCENTAGE_CHANGE)))
                    .price(cursor.getFloat(cursor.getColumnIndex(Contract.Quote.COLUMN_PRICE)))
                    .stockHistory(cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_HISTORY)))
                    .symbol(cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_SYMBOL)))
                    .build();
        }
        cursor.close();

        stockDetailsModel.setDetailsList(createDetailViewModelList(stockDetailsModel));

        return stockDetailsModel;
    }

    private static List<DetailViewModel> createDetailViewModelList(StockDetailsModel stockDetailsModel) {
        List<DetailViewModel> detailList = new ArrayList<>();

        detailList.add(new DetailViewModel("Symbol", stockDetailsModel.getSymbol()));
        detailList.add(new DetailViewModel("Price", String.valueOf(stockDetailsModel.getPrice())));
        detailList.add(new DetailViewModel("Ask", String.valueOf(stockDetailsModel.getAsk())));
        detailList.add(new DetailViewModel("Bid", String.valueOf(stockDetailsModel.getBid())));
        detailList.add(new DetailViewModel("Bid Size", String.valueOf(stockDetailsModel.getBidSize())));
        detailList.add(new DetailViewModel("Percentage Change", String.valueOf(stockDetailsModel.getPercentChange())));
        detailList.add(new DetailViewModel("Last Trade Date", stockDetailsModel.getLastTradeDate()));
        detailList.add(new DetailViewModel("Last Trade Size", String.valueOf(stockDetailsModel.getLastTradeSize())));
        detailList.add(new DetailViewModel("Last Trade Time", stockDetailsModel.getLastTradeTime()));

        return detailList;
    }
}
