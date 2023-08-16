package com.leon.hamrah_abfa.fragments.dashboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentDashboardPaymentBinding;

import java.util.ArrayList;

public class DashboardPaymentFragment extends Fragment {
    private FragmentDashboardPaymentBinding binding;
    private ICallback callback;

    public DashboardPaymentFragment() {
    }

    public static DashboardPaymentFragment newInstance() {
        return new DashboardPaymentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardPaymentBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        initializeChart();
    }

    private void initializeChart() {
        designPieChart();

        setData(12, 24);

    }

    private void designPieChart() {
        binding.chartHorizontalBar.setNoDataText(getString(R.string.no_data_found));
        binding.chartHorizontalBar.setNoDataTextColor(R.color.dark_gray);
        binding.chartHorizontalBar.setNoDataTextTypeface(callback.getTypeface());

        binding.chartHorizontalBar.getDescription().setText(getString(R.string.chart_payment_description));
        binding.chartHorizontalBar.getDescription().setXOffset(10f);
        binding.chartHorizontalBar.getDescription().setYOffset(10f);
        binding.chartHorizontalBar.getDescription().setTextColor(R.color.dark_gray);
        binding.chartHorizontalBar.getDescription().setTypeface(callback.getTypeface());

        binding.chartHorizontalBar.setExtraOffsets(5f, 0f, 5f, 10f);

        binding.chartHorizontalBar.setDrawBarShadow(false);
        binding.chartHorizontalBar.setDrawValueAboveBar(true);

        binding.chartHorizontalBar.setDragEnabled(false);
        binding.chartHorizontalBar.setScaleEnabled(false);
        binding.chartHorizontalBar.setPinchZoom(false);

        binding.chartHorizontalBar.setDrawGridBackground(false);

        XAxis xAxis = binding.chartHorizontalBar.getXAxis();
        xAxis.setTypeface(callback.getTypeface());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLimitLinesBehindData(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
//                Log.e("value", String.valueOf(value));
                return String.valueOf((int) value);
            }
        });

        YAxis yAxisLeft = binding.chartHorizontalBar.getAxisLeft();
        yAxisLeft.setTypeface(callback.getTypeface());
//        yAxisLeft.setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value) {
//                return super.getFormattedValue(value);
//            }
//        });
        yAxisLeft.setDrawAxisLine(true);
        yAxisLeft.setDrawGridLines(true);

        YAxis yAxisRight = binding.chartHorizontalBar.getAxisRight();
        yAxisRight.setTypeface(callback.getTypeface());
        yAxisRight.setDrawAxisLine(true);
        yAxisRight.setDrawGridLines(false);

        binding.chartHorizontalBar.setFitBars(true);

        binding.chartHorizontalBar.animateXY(1500, 1500);

        Legend legend = binding.chartHorizontalBar.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setTextSize(10);
        legend.setTypeface(callback.getTypeface());
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setFormSize(8f);
        legend.setXEntrySpace(4f);
    }


    private void setData(int count, float range) {

        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range - range / 2);
            values.add(new BarEntry(i, val));
        }

        BarDataSet dataSet;

        if (binding.chartHorizontalBar.getData() != null &&
                binding.chartHorizontalBar.getData().getDataSetCount() > 0) {
            dataSet = (BarDataSet) binding.chartHorizontalBar.getData().getDataSetByIndex(0);
            dataSet.setValues(values);
            binding.chartHorizontalBar.getData().notifyDataChanged();
            binding.chartHorizontalBar.notifyDataSetChanged();
        } else {
            dataSet = new BarDataSet(values, getString(R.string.chart_payment));
            dataSet.setColors(R.color.purple_7001, R.color.dark_gray, R.color.purple_7002, R.color.dark);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataSet);

            BarData data = new BarData(dataSets);
            data.setValueTypeface(callback.getTypeface());
//            data.setValueTextSize(10f);
//            data.setBarWidth(barWidth);
            data.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return String.format("%s روز %s از مهلت پرداخت", (int) value, value > 0 ? "پس" : "پیش");
//                    return super.getFormattedValue((int) value);
                }
            });
            binding.chartHorizontalBar.setData(data);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    public interface ICallback {
        PaymentStats getPaymentStats();

        Typeface getTypeface();
    }
}