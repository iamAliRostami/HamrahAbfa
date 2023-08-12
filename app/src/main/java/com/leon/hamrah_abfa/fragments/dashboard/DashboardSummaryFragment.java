package com.leon.hamrah_abfa.fragments.dashboard;

import static com.leon.hamrah_abfa.helpers.Constants.FONT_NAME;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
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
        for (int i = 0; i < billSummary.size(); i++) {
            billSummaryDays[i] = billSummary.get(i).getDay();
            billSummaryRates[i] = billSummary.get(i).getRate();
        }
//        ChartEntity chartEntities = new ChartEntity(Color.YELLOW, billSummaryRates);
//        ChartEntity chartEntities1 = new ChartEntity(Color.YELLOW, billSummaryRates);
//
//        ArrayList<ChartEntity> chartEntities2 = new ArrayList<>();
//        chartEntities2.add(chartEntities1);
//        chartEntities2.add(chartEntities);
//
////        binding.lineChart.setLegend(billSummaryDays);
////        binding.lineChart.setList(chartEntities2);
//        ArrayList<DataPoint> points = new ArrayList<>();
//        points.add(new DataPoint(0f, 1f));
//        points.add(new DataPoint(1f, 2f));
//        points.add(new DataPoint(2f, 3f));
//        points.add(new DataPoint(3f, 4f));
//        Dataset dataset = new Dataset(points);
//        binding.liveChart.setDataset(dataset);

        setData(12, 230);
    }

    private void setData(int count, float range) {

//        float start = 1f;

        ArrayList<BarEntry> values = new ArrayList<>();

        List<Entry> yVals = new ArrayList<>();


        for (int i = 0; i < billSummaryRates.length; i++) {
//            float val = (float) (Math.random() * (range + 1));

//            if (Math.random() * 100 < 25) {
//                values.add(new BarEntry(i, billSummaryRates[i], ContextCompat.getDrawable(requireContext(),R.drawable.ic_bill_id)));
//            } else {
//                values.add(new BarEntry(i, billSummaryRates[i]));
//            }
            values.add(new BarEntry(i, billSummaryRates[i]));
            yVals.add(new BarEntry(i, billSummaryRates[i]));
        }

        BarDataSet set1;

//        if (binding.chart1.getData() != null &&
//                binding.chart1.getData().getDataSetCount() > 0) {
//            set1 = (BarDataSet) binding.chart1.getData().getDataSetByIndex(0);
//            set1.setValues(values);
//            binding.chart1.getData().notifyDataChanged();
//            binding.chart1.notifyDataSetChanged();
//
//        } else {
//            set1 = new BarDataSet(values, "The year 2017");
//
//            set1.setDrawIcons(false);
//
//            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
//            dataSets.add(set1);
//
//            BarData data = new BarData(dataSets);
//            data.setValueTextSize(10f);
//            data.setBarWidth(10.9f);
//
//            binding.chart1.setData(data);
//        }


        ArrayList<BarEntry> group1 = new ArrayList<>();
        group1.add(new BarEntry(1f, 0));
//        group1.add(new BarEntry(2f, 1));
//        group1.add(new BarEntry(3f, 2));
//        group1.add(new BarEntry(4f, 30));
//        group1.add(new BarEntry(5f, 4));
//        group1.add(new BarEntry(6f, 5));

        // create BarEntry for group 2
        ArrayList<BarEntry> group2 = new ArrayList<>();
        group2.add(new BarEntry(6f, 0));
        group2.add(new BarEntry(7f, 1));
        group2.add(new BarEntry(8f, 2));
        group2.add(new BarEntry(12f, 3));
        group2.add(new BarEntry(15f, 4));
        group2.add(new BarEntry(10f, 5));

        // creating dataset for group1
        BarDataSet barDataSet1 = new BarDataSet(group1, "Brand 1");
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

        // creating dataset for group2
        BarDataSet barDataSet2 = new BarDataSet(group2, "Brand 2");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);


        BarDataSet barDataSet3 = new BarDataSet(values, "Brand 1");
//        barDataSet3.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet3.setColors(R.color.purple_7001, R.color.purple_7002, R.color.dark, R.color.dark_gray);
        barDataSet3.setStackLabels(billSummaryDays);

        // combined all dataset into an arraylist
        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);


        ArrayList<String> labels = new ArrayList<>();
        labels.add("JAN");
        labels.add("FEB");
        labels.add("MAR");
        labels.add("APR");
        labels.add("MAY");
        labels.add("JUN");
        BarData data = new BarData(barDataSet1, barDataSet2);// initialize the Bardata with argument labels and dataSet
        data = new BarData(barDataSet3);
        data.setValueTypeface(Typeface.createFromAsset(requireContext().getAssets(), FONT_NAME));
        binding.chart1.setNoDataTextTypeface(Typeface.createFromAsset(requireContext().getAssets(), FONT_NAME));
        binding.chart1.setData(data);

        LineDataSet lineDataSet = new LineDataSet(yVals, "test");
        LineData lineChart = new LineData(lineDataSet);
        binding.chart2.setData(lineChart);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    public interface ICallback {
        ArrayList<DashboardSummaryViewModel> getBillSummary();
    }
}