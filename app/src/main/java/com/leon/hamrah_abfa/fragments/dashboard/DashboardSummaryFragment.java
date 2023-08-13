package com.leon.hamrah_abfa.fragments.dashboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentDashboardSummaryBinding;
import com.leon.hamrah_abfa.fragments.ui.dashboard.DashboardSummaryViewModel;

import java.util.ArrayList;
import java.util.List;

public class DashboardSummaryFragment extends Fragment {

    private FragmentDashboardSummaryBinding binding;
    private ICallback callback;
    private String[] billSummaryDays;
    private float[] billSummaryRates;

    public DashboardSummaryFragment() {
    }


    public static DashboardSummaryFragment newInstance() {
        return new DashboardSummaryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardSummaryBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        initializeChart();
    }

    private void initializeChart() {
        billSummaryRates = new float[callback.getBillSummary().size()];
        billSummaryDays = new String[callback.getBillSummary().size()];
        ArrayList<DashboardSummaryViewModel> billSummary = callback.getBillSummary();
        List<Entry> yValues = new ArrayList<>();
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = 0; i < billSummary.size(); i++) {
            billSummaryDays[i] = billSummary.get(i).getDay();
            billSummaryRates[i] = billSummary.get(i).getRate();
            yValues.add(new BarEntry(i, billSummary.get(i).getRate()));
            values.add(new BarEntry(i, billSummary.get(i).getRate()));
        }
        setDataLine(yValues);
        setDataBar(values);
    }

    private void setDataLine(List<Entry> yValues) {
        LineDataSet dataSet = new LineDataSet(yValues, "میانگین مصرف");
        dataSet.setColors(R.color.dark_gray);
        dataSet.setCircleColor(R.color.purple_7001);
        LineData lineChart = new LineData(dataSet);
        lineChart.setValueTextColor(R.color.dark);
        lineChart.setValueTypeface(callback.getTypeface());

        XAxis xAxis = binding.chartLine.getXAxis();
        xAxis.setTypeface(callback.getTypeface());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);

        xAxis.setLabelRotationAngle(-45);
        xAxis.setGranularity(1f);
        xAxis.setTypeface(callback.getTypeface());

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return billSummaryDays[(int) value];
            }
        });


        YAxis yAxisLeft = binding.chartLine.getAxisLeft();
        yAxisLeft.setTypeface(callback.getTypeface());
        YAxis yAxisRight = binding.chartLine.getAxisRight();
        yAxisRight.setTypeface(callback.getTypeface());
        binding.chartLine.setData(lineChart);
        binding.chartLine.setExtraOffsets(5f, 0f, 5f, 10f);
        binding.chartLine.getXAxis().setSpaceMin(0.2f);
        binding.chartLine.getXAxis().setSpaceMax(0.2f);
        binding.chartLine.getDescription().setText("میانگین مصرف دوره\u200cای");
        binding.chartLine.getDescription().setXOffset(10f);
        binding.chartLine.getDescription().setYOffset(10f);
        binding.chartLine.getDescription().setTextColor(R.color.dark_gray);
        binding.chartLine.getDescription().setTypeface(callback.getTypeface());

        Legend l = binding.chartLine.getLegend();
        l.setWordWrapEnabled(true);
        l.setTextSize(10);
        l.setTypeface(callback.getTypeface());
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.NONE);
        binding.chartLine.invalidate();
    }

    private void setDataBar(ArrayList<BarEntry> values) {
        BarDataSet dataSet = new BarDataSet(values, "میانگین مصرف");
        dataSet.setColors(R.color.purple_7001, R.color.dark_gray, R.color.purple_7002, R.color.dark);
        dataSet.setStackLabels(billSummaryDays);

        BarData data = new BarData(dataSet);
        data.setValueTypeface(callback.getTypeface());
        binding.chartBar.setNoDataTextTypeface(callback.getTypeface());
        binding.chartBar.setNoDataTextColor(R.color.dark);
        binding.chartBar.setData(data);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    public interface ICallback {
        ArrayList<DashboardSummaryViewModel> getBillSummary();

        Typeface getTypeface();
    }
}