package com.app.leon.moshtarak.fragments.dashboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.databinding.FragmentDashboardPaymentBinding;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

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
        designHorizontalBarChart();
        if (callback.getPaymentStats().payDeadlineKeys != null &&
                !callback.getPaymentStats().payDeadlineKeys.isEmpty() &&
                callback.getPaymentStats().payDeadlineValues != null &&
                !callback.getPaymentStats().payDeadlineValues.isEmpty()) {
            ArrayList<BarEntry> barEntries = new ArrayList<>();
            for (int i = 0; i < callback.getPaymentStats().payDeadlineValues.size(); i++) {
                barEntries.add(new BarEntry(i, callback.getPaymentStats().payDeadlineValues.get(i)));
            }
            setData(barEntries);
            initializeDescription();
        } else {
            ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            binding.chartHorizontalBar.setLayoutParams(params);
        }
    }

    private void initializeDescription() {
        @SuppressLint("DefaultLocale")
        String description = String.format(getString(R.string.payment_description),
                callback.getPaymentStats().totalBills, callback.getPaymentStats().totalPayments,
                callback.getPaymentStats().unpayedBills, callback.getPaymentStats().firstBillDate,
                callback.getPaymentStats().payAverageTime
        );
        binding.textViewPaymentDescription.setText(description);
    }

    private void designHorizontalBarChart() {
        binding.chartHorizontalBar.setNoDataText(getString(R.string.no_data_found));
        binding.chartHorizontalBar.setNoDataTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray));
        binding.chartHorizontalBar.setNoDataTextTypeface(callback.getTypeface());

        binding.chartHorizontalBar.getDescription().setEnabled(false);
//        binding.chartHorizontalBar.getDescription().setXOffset(10f);
//        binding.chartHorizontalBar.getDescription().setYOffset(10f);
//        binding.chartHorizontalBar.getDescription().setTextSize(12f);
//        binding.chartHorizontalBar.getDescription().setTypeface(callback.getTypeface());
//        binding.chartHorizontalBar.getDescription().setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray));

        binding.chartHorizontalBar.setExtraOffsets(5f, 0f, 5f, 10f);

        binding.chartHorizontalBar.setDrawBarShadow(true);
        binding.chartHorizontalBar.setDrawValueAboveBar(true);

//        binding.chartHorizontalBar.setDrawGridBackground(false);

        XAxis xAxis = binding.chartHorizontalBar.getXAxis();
        xAxis.setTypeface(callback.getTypeface());
        xAxis.setDrawLimitLinesBehindData(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                try {
                    return callback.getPaymentStats().payDeadlineKeys.get((int) value);
                } catch (Exception e) {
                    return "";
                }
            }
        });
        xAxis.setAxisMinValue(1.5f);
//        xAxis.mAxisMinimum = 1.5f;

        YAxis yAxisLeft = binding.chartHorizontalBar.getAxisLeft();
        yAxisLeft.setTypeface(callback.getTypeface());
        yAxisLeft.setDrawAxisLine(true);
        yAxisLeft.setDrawGridLines(true);

        YAxis yAxisRight = binding.chartHorizontalBar.getAxisRight();
        yAxisRight.setTypeface(callback.getTypeface());
        yAxisRight.setDrawAxisLine(true);
        yAxisRight.setDrawGridLines(false);


        binding.chartHorizontalBar.getXAxis().setSpaceMin(2f);
        binding.chartHorizontalBar.setFitBars(true);

        binding.chartHorizontalBar.animateXY(1500, 1500);

        Legend legend = binding.chartHorizontalBar.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setTextSize(10f);
        legend.setTypeface(callback.getTypeface());
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setFormSize(8f);
        legend.setXEntrySpace(4f);
    }

    private void setData(ArrayList<BarEntry> values) {
        BarDataSet dataSet;
        if (binding.chartHorizontalBar.getData() != null &&
                binding.chartHorizontalBar.getData().getDataSetCount() > 0) {
            dataSet = (BarDataSet) binding.chartHorizontalBar.getData().getDataSetByIndex(0);
            dataSet.setValues(values);
            binding.chartHorizontalBar.getData().notifyDataChanged();
            binding.chartHorizontalBar.notifyDataSetChanged();
        } else {
            dataSet = new BarDataSet(values, getString(R.string.chart_payment_description));
            dataSet.setColors(R.color.purple_7001, R.color.dark_gray, R.color.purple_7002, R.color.dark);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataSet);

            BarData data = new BarData(dataSets);


            data.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
//                    return String.format("%s روز %s از مهلت پرداخت", (int) value, value > 0 ? "پس" : "پیش");
                    return String.valueOf(value > 0 ? (int) value : (int) -value);
                }
            });
            data.setValueTypeface(callback.getTypeface());
            binding.chartHorizontalBar.setData(data);
            binding.chartHorizontalBar.invalidate();
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