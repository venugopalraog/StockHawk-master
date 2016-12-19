package com.udacity.stockhawk.common;

import android.graphics.Color;
import android.text.TextUtils;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import timber.log.Timber;

/**
 * Created by gubbave on 12/15/2016.
 */
public class LineChartUIHelper {

    private void LineChartUIHelper() {  }

    private static String TAG = LineChartUIHelper.class.getSimpleName();

    public static void initLineChart(String history, LineChart lineChart) {
        if (history == null) {
            Timber.d(TAG, "initLineChart history is null -- return");
            return;
        }

        updateLineChartData(history, lineChart);
        updateChartAxis(lineChart);
    }

    private static void updateChartAxis(LineChart lineChart) {
        // get the legend (only possible after setting data)
        Legend l = lineChart.getLegend();
        l.setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.rgb(255, 192, 56));
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f); // one hour
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(24f);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setYOffset(-9f);
        leftAxis.setTextColor(Color.rgb(255, 192, 56));

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private  static void updateLineChartData(String history, LineChart lineChart) {
        String[] historyArray =  TextUtils.split(history, "\n");
        int numberOfPoints = historyArray.length;
        ArrayList<Entry> values = new ArrayList<>();

        for (int j = numberOfPoints - 1; j > -1; j--) {
            String historyData = historyArray[j];

            if (TextUtils.isEmpty(historyData)) continue;

            String[] entryData = TextUtils.split(historyData, ", ");
            values.add(new Entry((numberOfPoints - 1) - j, Float.valueOf(entryData[1])));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(values, "DataSet 1");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setValueTextColor(ColorTemplate.getHoloBlue());
        set1.setLineWidth(1.5f);
        set1.setDrawCircles(false);
        set1.setDrawValues(false);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(true);

        // create a data object with the datasets
        LineData data = new LineData(set1);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        // no description text
        lineChart.getDescription().setEnabled(false);
        // enable touch gestures
        lineChart.setTouchEnabled(true);
        lineChart.setDragDecelerationFrictionCoef(0.9f);

        // set data
        lineChart.setData(data);
        lineChart.setAutoScaleMinMaxEnabled(true);
        lineChart.invalidate();
    }

}
