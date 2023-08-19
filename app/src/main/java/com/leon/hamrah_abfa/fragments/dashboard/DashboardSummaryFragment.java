package com.leon.hamrah_abfa.fragments.dashboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentDashboardSummaryBinding;

import java.util.ArrayList;

public class DashboardSummaryFragment extends Fragment {
    private FragmentDashboardSummaryBinding binding;
    private ICallback callback;

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
        designLineChart();
        designBarChart();
        if (!callback.getBillSummary().billSummaryWrapper.isEmpty()) {
            ArrayList<Summary> billSummary = callback.getBillSummary().billSummaryWrapper;
            ArrayList<Entry> entries = new ArrayList<>();
            ArrayList<BarEntry> barEntries = new ArrayList<>();
            for (int i = 0; i < billSummary.size(); i++) {
                entries.add(new Entry(i, billSummary.get(i).getRate()));
                barEntries.add(new BarEntry(i, billSummary.get(i).getRate()));
//            entries.add(new BarEntry(i, i*10));
//            barEntries.add(new BarEntry(i, i*10));
            }
            setLineData(entries);
            setBarData(barEntries);
        } else {
            ViewGroup.LayoutParams params = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
            binding.chartLine.setLayoutParams(params);
            binding.chartBar.setLayoutParams(params);
        }
    }

    private void designLineChart() {
        binding.chartLine.setNoDataText(getString(R.string.no_data_found));
        binding.chartLine.setNoDataTextColor(R.color.dark_gray);
        binding.chartLine.setNoDataTextTypeface(callback.getTypeface());

        binding.chartLine.getDescription().setText(getString(R.string.chart_rate_avg_description));
        binding.chartLine.getDescription().setXOffset(10f);
        binding.chartLine.getDescription().setYOffset(10f);
        binding.chartLine.getDescription().setTextColor(R.color.dark_gray);
        binding.chartLine.getDescription().setTypeface(callback.getTypeface());

        binding.chartLine.setExtraOffsets(5f, 0f, 5f, 10f);

        binding.chartLine.setDragEnabled(false);
        binding.chartLine.setScaleEnabled(false);
        binding.chartLine.setPinchZoom(false);

        XAxis xAxis = binding.chartLine.getXAxis();
        xAxis.setDrawLimitLinesBehindData(true);
        xAxis.setTypeface(callback.getTypeface());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setGranularity(1f);
        xAxis.setTypeface(callback.getTypeface());
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return callback.getBillSummary().billSummaryWrapper.get((int) value).getDay();
            }
        });
        binding.chartLine.getXAxis().setSpaceMax(0.2f);
        binding.chartLine.getXAxis().setSpaceMin(0.2f);

        YAxis yAxisLeft = binding.chartLine.getAxisLeft();
        yAxisLeft.setTypeface(callback.getTypeface());
        yAxisLeft.setDrawLimitLinesBehindData(true);

        YAxis yAxisRight = binding.chartLine.getAxisRight();
        yAxisRight.setTypeface(callback.getTypeface());
        yAxisRight.setDrawLimitLinesBehindData(true);

        LimitLine limitLine = new LimitLine(callback.getBillSummary().maxValue, getString(R.string.chart_rate_avg_max));
        limitLine.setLineColor(R.color.dark_gray);
        limitLine.setLineWidth(2f);
        limitLine.enableDashedLine(10f, 10f, 0f);
        limitLine.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        limitLine.setTextSize(10f);
        limitLine.setTypeface(callback.getTypeface());

        yAxisLeft.addLimitLine(limitLine);

        yAxisRight.setAxisMinimum(0f);
        yAxisLeft.setAxisMinimum(0f);

        Legend legend = binding.chartLine.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setTextSize(10);
        legend.setTypeface(callback.getTypeface());
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setForm(Legend.LegendForm.LINE);
//        binding.chartLine.invalidate();

        binding.chartLine.animateXY(1500, 1500);

//        LineDataSet dataSet = new LineDataSet(values, "میانگین مصرف");
//        dataSet.setColors(R.color.dark_gray);
//        dataSet.setCircleColor(R.color.purple_7001);
//        LineData lineData = new LineData(dataSet);
//        lineData.setValueTextColor(R.color.dark);
//        lineData.setValueTypeface(callback.getTypeface());
//        binding.chartLine.setData(lineData);
    }

    private void setLineData(ArrayList<Entry> values) {

        LineDataSet dataSet;

        if (binding.chartLine.getData() != null &&
                binding.chartLine.getData().getDataSetCount() > 0) {
            dataSet = (LineDataSet) binding.chartLine.getData().getDataSetByIndex(0);
            dataSet.setValues(values);
            dataSet.notifyDataSetChanged();
            binding.chartLine.getData().notifyDataChanged();
            binding.chartLine.notifyDataSetChanged();
        } else {
            dataSet = new LineDataSet(values, getString(R.string.chart_rate_avg));
            dataSet.enableDashedLine(10f, 5f, 0f);

            dataSet.setColors(R.color.dark_gray);
            dataSet.setCircleColor(R.color.purple_7001);

            dataSet.setLineWidth(1f);
            dataSet.setCircleRadius(3f);

            dataSet.setDrawCircleHole(true);

            dataSet.setFormLineWidth(1f);
            dataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            dataSet.setFormSize(15.f);

            dataSet.setValueTextSize(9f);

            dataSet.enableDashedHighlightLine(10f, 5f, 0f);

            dataSet.setDrawFilled(true);
            dataSet.setFillFormatter((dataSet1, dataProvider) -> binding.chartLine.getAxisLeft().getAxisMinimum());


            dataSet.setFillColor(R.color.light_gray);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataSet);

            LineData data = new LineData(dataSets);
            data.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
//                    return super.getFormattedValue((int) value);
                    return String.valueOf((int) value);
                }
            });
            data.setValueTextColor(R.color.dark);
            data.setValueTypeface(callback.getTypeface());

            binding.chartLine.setData(data);
        }
    }

    private void designBarChart() {
        binding.chartBar.setNoDataText(getString(R.string.no_data_found));
        binding.chartBar.setNoDataTextColor(R.color.dark_gray);
        binding.chartBar.setNoDataTextTypeface(callback.getTypeface());

        binding.chartBar.getDescription().setEnabled(false);
        binding.chartBar.setDragEnabled(false);
        binding.chartBar.setScaleEnabled(false);
        binding.chartBar.setPinchZoom(false);

        binding.chartBar.setExtraOffsets(5f, 0f, 5f, 10f);

        XAxis xAxis = binding.chartBar.getXAxis();
        xAxis.setTypeface(callback.getTypeface());
        xAxis.setDrawLimitLinesBehindData(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return callback.getBillSummary().billSummaryWrapper.get((int) value).getDay();
            }
        });

        YAxis yAxisLeft = binding.chartBar.getAxisLeft();
        yAxisLeft.setTypeface(callback.getTypeface());
        yAxisLeft.setDrawLimitLinesBehindData(true);

        YAxis yAxisRight = binding.chartBar.getAxisRight();
        yAxisRight.setTypeface(callback.getTypeface());
        yAxisRight.setDrawLimitLinesBehindData(true);

        binding.chartBar.animateXY(1500, 1500);

        Legend legend = binding.chartBar.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setTextSize(10);
        legend.setTypeface(callback.getTypeface());
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setForm(Legend.LegendForm.SQUARE);


//        BarData data = new BarData(dataSet);
//        data.setValueTypeface(callback.getTypeface());
//        binding.chartBar.setNoDataTextTypeface(callback.getTypeface());
//        binding.chartBar.setNoDataTextColor(R.color.dark);
//        binding.chartBar.setData(data);
    }

    private void setBarData(ArrayList<BarEntry> values) {
        BarDataSet dataSet;
        if (binding.chartBar.getData() != null &&
                binding.chartBar.getData().getDataSetCount() > 0) {
            dataSet = (BarDataSet) binding.chartBar.getData().getDataSetByIndex(0);
            dataSet.setValues(values);
            binding.chartBar.getData().notifyDataChanged();
            binding.chartBar.notifyDataSetChanged();
        } else {
            dataSet = new BarDataSet(values, getString(R.string.chart_rate_avg));
            dataSet.setColors(R.color.purple_7001, R.color.dark_gray, R.color.purple_7002, R.color.dark);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataSet);

            BarData data = new BarData(dataSets);
            data.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return String.valueOf((int) value);
//                    return super.getFormattedValue((int) value);
                }
            });
            data.setValueTypeface(callback.getTypeface());
            binding.chartBar.setData(data);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    public interface ICallback {
        SummaryStats getBillSummary();

        Typeface getTypeface();
    }
}