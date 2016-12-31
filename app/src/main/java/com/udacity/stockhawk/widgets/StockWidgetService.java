package com.udacity.stockhawk.widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by gubbave on 12/29/2016.
 */

public class StockWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StockWidgetViewsFactory(this.getApplicationContext(), intent);
    }
}
