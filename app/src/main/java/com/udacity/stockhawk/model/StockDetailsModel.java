package com.udacity.stockhawk.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by gubbave on 12/16/2016.
 */

public class StockDetailsModel implements Parcelable{

    private String symbol;
    private float price;
    private float percentChange;
    private float ask;
    private float bid;
    private long bidSize;
    private long lastTradeSize;
    private String lastTradeDate;
    private String lastTradeTime;
    private String stockHistory;
    private List<DetailViewModel> detailsList;


    protected StockDetailsModel(Parcel in) {
        symbol = in.readString();
        price = in.readFloat();
        percentChange = in.readFloat();
        ask = in.readFloat();
        bid = in.readFloat();
        bidSize = in.readLong();
        lastTradeSize = in.readLong();
        lastTradeDate = in.readString();
        lastTradeTime = in.readString();
        stockHistory = in.readString();
    }

    private StockDetailsModel(Builder builder) {
        symbol = builder.symbol;
        price = builder.price;
        percentChange = builder.percentChange;
        ask = builder.ask;
        bid = builder.bid;
        bidSize = builder.bidSize;
        lastTradeSize = builder.lastTradeSize;
        lastTradeDate = builder.lastTradeDate;
        lastTradeTime = builder.lastTradeTime;
        stockHistory = builder.stockHistory;
    }

    public String getSymbol() {
        return symbol;
    }

    public float getPrice() {
        return price;
    }

    public float getPercentChange() {
        return percentChange;
    }

    public float getAsk() {
        return ask;
    }

    public float getBid() {
        return bid;
    }

    public long getBidSize() {
        return bidSize;
    }

    public long getLastTradeSize() {
        return lastTradeSize;
    }

    public String getLastTradeDate() {
        return lastTradeDate;
    }

    public String getLastTradeTime() {
        return lastTradeTime;
    }

    public String getStockHistory() {
        return stockHistory;
    }

    public List<DetailViewModel> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(List<DetailViewModel> detailsList) {
        this.detailsList = detailsList;
    }


    public static final Creator<StockDetailsModel> CREATOR = new Creator<StockDetailsModel>() {
        @Override
        public StockDetailsModel createFromParcel(Parcel in) {
            return new StockDetailsModel(in);
        }

        @Override
        public StockDetailsModel[] newArray(int size) {
            return new StockDetailsModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(symbol);
        parcel.writeFloat(price);
        parcel.writeFloat(percentChange);
        parcel.writeFloat(ask);
        parcel.writeFloat(bid);
        parcel.writeLong(bidSize);
        parcel.writeLong(lastTradeSize);
        parcel.writeString(lastTradeDate);
        parcel.writeString(lastTradeTime);
        parcel.writeString(stockHistory);
    }


    public static final class Builder {

        private String symbol;
        private float price;
        private float percentChange;
        private float ask;
        private float bid;
        private long bidSize;
        private long lastTradeSize;
        private String lastTradeDate;
        private String lastTradeTime;
        private String stockHistory;
        private List<DetailViewModel> detailsList;


        public Builder() {  }

        public Builder symbol(String val) {
            symbol = val;
            return this;
        }

        public Builder price(float val) {
            price = val;
            return this;
        }

        public Builder percentChange(float val) {
            percentChange = val;
            return this;
        }

        public Builder ask(float val) {
            ask = val;
            return this;
        }

        public Builder bid(float val) {
            bid = val;
            return this;
        }

        public Builder bidSize(long val) {
            bidSize = val;
            return this;
        }

        public Builder lastTradeSize(long val) {
            lastTradeSize = val;
            return this;
        }

        public Builder lastTradeDate(String val) {
            lastTradeDate = val;
            return this;
        }

        public Builder lastTradeTime(String val) {
            lastTradeTime = val;
            return this;
        }

        public Builder stockHistory(String val) {
            stockHistory = val;
            return this;
        }

        public Builder detailViewModelList(List<DetailViewModel> val) {
            detailsList = val;
            return this;
        }

        public StockDetailsModel build() {
            return new StockDetailsModel(this);
        }
    }
}
