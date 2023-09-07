package com.app.leon.moshtarak.fragments.dashboard;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
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
import com.app.leon.moshtarak.databinding.FragmentDashboardCounterBinding;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class DashboardCounterFragment extends Fragment {
    private FragmentDashboardCounterBinding binding;
    private ICallback callback;

    public DashboardCounterFragment() {
    }

    public static DashboardCounterFragment newInstance() {
        return new DashboardCounterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardCounterBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        initializeChart();
    }

    private void initializeChart() {
        designPieChart();
        if (callback.getCounterStat().counterStatWrapper != null &&
                !callback.getCounterStat().counterStatWrapper.isEmpty()) {
            ArrayList<PieEntry> entries = new ArrayList<>();
            ArrayList<Counter> counters = callback.getCounterStat().counterStatWrapper;
            for (int i = 0; i < counters.size(); i++) {
                entries.add(new PieEntry(counters.get(i).count, counters.get(i).title));
            }
            setPieChartData(entries);
        } else {
            ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            binding.chartPie.setLayoutParams(params);
        }
    }

    private void designPieChart() {
        binding.chartPie.setNoDataText(getString(R.string.no_data_found));
        binding.chartPie.setNoDataTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray));
        binding.chartPie.setNoDataTextTypeface(callback.getTypeface());

        binding.chartPie.setUsePercentValues(true);

        binding.chartPie.getDescription().setEnabled(false);
//        binding.chartPie.getDescription().setText(getString(R.string.counter_state_description));
//        binding.chartPie.getDescription().setXOffset(10f);
//        binding.chartPie.getDescription().setYOffset(10f);
//        binding.chartPie.getDescription().setTextSize(15f);
//        binding.chartPie.getDescription().setTypeface(callback.getTypeface());
//        binding.chartPie.getDescription().setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray));

//        binding.chartPie.setExtraOffsets(10f, 0f, 10f, 0f);

        binding.chartPie.setDragDecelerationFrictionCoef(0.95f);

        binding.chartPie.setCenterTextTypeface(callback.getTypeface());


        binding.chartPie.setTransparentCircleColor(Color.WHITE);
        binding.chartPie.setTransparentCircleAlpha(110);

        binding.chartPie.setHoleRadius(45f);
        binding.chartPie.setTransparentCircleRadius(55f);

        binding.chartPie.setDrawCenterText(true);

        binding.chartPie.setDrawHoleEnabled(true);
        binding.chartPie.setHoleColor(Color.TRANSPARENT);

        binding.chartPie.setRotationAngle(0);
        binding.chartPie.setRotationEnabled(true);
        binding.chartPie.setHighlightPerTapEnabled(true);

        binding.chartPie.setDrawEntryLabels(false);
//        binding.chartPie.setEntryLabelTextSize(12f);
//        binding.chartPie.setEntryLabelColor(R.color.dark_gray);
//        binding.chartPie.setEntryLabelTypeface(callback.getTypeface());

        binding.chartPie.animateY(1400, Easing.EaseInOutQuad);

        Legend l = binding.chartPie.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setEnabled(true);
        l.setWordWrapEnabled(true);
        l.setTypeface(callback.getTypeface());
    }

    private void setPieChartData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, getString(R.string.counter_state_description));
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        int[] colors = new int[entries.size()];
        TypedArray drawable = getResources().obtainTypedArray(R.array.pie_colors);
        for (int i = 0; i < entries.size(); i++) {
            colors[i] = drawable.getColor(i % getResources().getIntArray(R.array.pie_colors).length,
                    ContextCompat.getColor(requireContext(), R.color.purple_7001));
        }
        dataSet.setColors(colors, 75);

        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);

        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);

        data.setValueTextSize(11f);
        data.setValueTextColor(R.color.dark_gray);
        data.setValueTypeface(callback.getTypeface());

        binding.chartPie.setData(data);
        binding.chartPie.highlightValues(null);
        binding.chartPie.invalidate();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    public interface ICallback {
        CounterStats getCounterStat();

        Typeface getTypeface();
    }
}