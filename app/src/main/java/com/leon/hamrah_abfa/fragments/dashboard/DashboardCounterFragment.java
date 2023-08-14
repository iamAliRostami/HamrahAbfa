package com.leon.hamrah_abfa.fragments.dashboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentDashboardCounterBinding;
import com.leon.hamrah_abfa.fragments.ui.dashboard.CounterStat;
import com.leon.hamrah_abfa.fragments.ui.dashboard.CounterStats;

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
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<CounterStat> counterStats = callback.getCounterStat().counterStatWrapper;
        for (int i = 0; i < counterStats.size(); i++) {
            entries.add(new PieEntry(i, counterStats.get(i)));
        }
        designPieChart();
        setPieChartData(entries);
    }

    private void designPieChart() {
        binding.chartPie.setNoDataText(getString(R.string.no_data_found));
        binding.chartPie.setNoDataTextColor(R.color.dark_gray);
        binding.chartPie.setNoDataTextTypeface(callback.getTypeface());

        binding.chartPie.setUsePercentValues(true);

        binding.chartPie.getDescription().setText(getString(R.string.counter_state));
        binding.chartPie.getDescription().setXOffset(10f);
        binding.chartPie.getDescription().setYOffset(10f);
        binding.chartPie.getDescription().setTextColor(R.color.dark_gray);
        binding.chartPie.getDescription().setTextSize(10f);
        binding.chartPie.getDescription().setTypeface(callback.getTypeface());

        binding.chartPie.setExtraOffsets(5, 10, 5, 5);

        binding.chartPie.setDragDecelerationFrictionCoef(0.95f);


        binding.chartPie.setCenterTextTypeface(callback.getTypeface());
//        binding.chartPie.setCenterText(generateCenterSpannableText());

        binding.chartPie.setExtraOffsets(10.f, 0.f, 10.f, 0.f);

        binding.chartPie.setDrawHoleEnabled(true);
        binding.chartPie.setHoleColor(Color.WHITE);

        binding.chartPie.setTransparentCircleColor(Color.WHITE);
        binding.chartPie.setTransparentCircleAlpha(110);

        binding.chartPie.setHoleRadius(58f);
        binding.chartPie.setTransparentCircleRadius(61f);

        binding.chartPie.setDrawCenterText(true);

        binding.chartPie.setRotationAngle(0);
        // enable rotation of the chart by touch
        binding.chartPie.setRotationEnabled(true);
        binding.chartPie.setHighlightPerTapEnabled(true);

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
//        binding.chartPie.setOnChartValueSelectedListener(this);

        binding.chartPie.animateY(1400, Easing.EaseInOutQuad);
        binding.chartPie.spin(2000, 0, 360, new Easing.EasingFunction() {
            @Override
            public float getInterpolation(float input) {
                return 0;
            }
        });

        Legend l = binding.chartPie.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);
    }

    private void setPieChartData(ArrayList<PieEntry> entries) {

        PieDataSet dataSet = new PieDataSet(entries, getString(R.string.counter_state));
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);


        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);

        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(callback.getTypeface());
        binding.chartPie.setData(data);

        // undo all highlights
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