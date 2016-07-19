package monkey.rising.tomatogo.statisticView;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;
import monkey.rising.tomatogo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyFragment extends Fragment {


    @InjectView(R.id.textView8)
    TextView textView8;
    @InjectView(R.id.view)
    LineChartView LineChart;
    @InjectView(R.id.textView3)
    TextView textView3;
    @InjectView(R.id.view2)
    ColumnChartView ColumnChart;

    private LineChartData lineData;
    private ColumnChartData columnData;
    public final static String[] date = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",};

    public final static String[] time = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24",};

    public DailyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily, container, false);
        ButterKnife.inject(this, view);

        generateInitialLineData();
        generateColumnData(Calendar.getInstance().get(Calendar.MONTH));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void generateColumnData(int month) {

        int numSubcolumns = 1;
        int numColumns = date.length;

        ColumnChart.cancelDataAnimation();
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue(0, ChartUtils.pickColor()));
            }

            axisValues.add(new AxisValue(i).setLabel(date[i]));

            columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
        }

        columnData = new ColumnChartData(columns);

        columnData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        columnData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(2));

        ColumnChart.setColumnChartData(columnData);

        // Set value touch listener that will trigger changes for chartTop.
        ColumnChart.setOnValueTouchListener(new ValueTouchListener());

        // Set selection mode to keep selected month column highlighted.
        ColumnChart.setValueSelectionEnabled(true);

        ColumnChart.setViewportCalculationEnabled(false);

        Viewport v = new Viewport(0, 5, time.length - 1, 0);
        Viewport cv = new Viewport(0, 5, 6, 0);
        ColumnChart.setMaximumViewport(v);
        ColumnChart.setCurrentViewport(cv);

        LineChart.setZoomType(ZoomType.HORIZONTAL);
        ColumnChart.setZoomType(ZoomType.HORIZONTAL);

        int num[] = DataReceiver.monthStatistics(month);
        int counter = 0;
        for (Column column : columnData.getColumns()) {
            for (SubcolumnValue value : column.getValues()) {
                value.setTarget(num[counter]);
                counter++;
            }
        }
        ColumnChart.startDataAnimation();

    }

    private void generateInitialLineData() {
        int numValues = time.length;

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<PointValue> values = new ArrayList<PointValue>();
        for (int i = 0; i < numValues; ++i) {
            values.add(new PointValue(i, 0));
            axisValues.add(new AxisValue(i).setLabel(time[i]));
        }

        Line line = new Line(values);
        line.setColor(ChartUtils.COLOR_GREEN).setCubic(true);

        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        lineData = new LineChartData(lines);
        lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3));

        LineChart.setLineChartData(lineData);

        LineChart.setViewportCalculationEnabled(false);

        Viewport v = new Viewport(0, 5, time.length - 1, 0);
        Viewport cv = new Viewport(0, 5, 6, 0);
        LineChart.setMaximumViewport(v);
        LineChart.setCurrentViewport(cv);

        LineChart.setZoomType(ZoomType.HORIZONTAL);

    }

    private void generateLineData(int color, int day) {
        LineChart.cancelDataAnimation();

        Line line = lineData.getLines().get(0);
        line.setColor(color);
        int counter = 0;
        int num[] = DataReceiver.dayStatistics(day);
        for (PointValue value : line.getValues()) {
            // Change target only for Y value.
            value.setTarget(value.getX(), num[counter]);
            counter++;
        }

        // Start new data animation with 300ms duration;
        LineChart.startDataAnimation(300);
    }

    private class ValueTouchListener implements ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
            generateLineData(value.getColor(), Integer.parseInt(date[columnIndex]));
        }

        @Override
        public void onValueDeselected() {

            generateLineData(ChartUtils.COLOR_GREEN, 0);

        }
    }

}