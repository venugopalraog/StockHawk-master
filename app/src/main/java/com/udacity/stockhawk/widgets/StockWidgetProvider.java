package com.udacity.stockhawk.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.ui.MainActivity;

/**
 * Created by gubbave on 12/29/2016.
 */
public class StockWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {

            Intent intent = new Intent(context, StockWidgetService.class);

            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.stock_list_widget_layout);

            remoteViews.setRemoteAdapter(R.id.listViewWidget, intent);
            remoteViews.setEmptyView(R.id.listViewWidget, R.id.empty_view);

            Intent clickIntent = new Intent(context, MainActivity.class);
            PendingIntent clickPI = PendingIntent.getActivity(context, 0,
                                    clickIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);

            remoteViews.setPendingIntentTemplate(R.id.listViewWidget, clickPI);
            remoteViews.setOnClickPendingIntent(R.id.empty_view, clickPI);
            remoteViews.setOnClickPendingIntent(R.id.addStockBtn, clickPI);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
