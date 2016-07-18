package monkey.rising.tomatogo.statisticView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;
import monkey.rising.tomatogo.R;

public class StatisticActivity extends AppCompatActivity {

    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.textView2)
    TextView textView2;
    @InjectView(R.id.completeNum)
    TextView completeNum;
    @InjectView(R.id.textView4)
    TextView textView4;
    @InjectView(R.id.undoneNum)
    TextView undoneNum;
    @InjectView(R.id.textView7)
    TextView textView7;
    @InjectView(R.id.textView8)
    TextView textView8;
    @InjectView(R.id.view)
    LineChartView LineChart;
    @InjectView(R.id.textView3)
    TextView textView3;
    @InjectView(R.id.view2)
    ColumnChartView ColumnChart;
    @InjectView(R.id.view3)
    PieChartView PieChart;
    @InjectView(R.id.textView5)
    TextView textView5;
    @InjectView(R.id.scrollView)
    ScrollView scrollView;

    private LineChartData lineData;
    private ColumnChartData columnData;
    private PieChartData pieData;

    public final static String[] date = new String[]{"1", "2", "3", "4", "5", "6", "7", "8","9","10",
            "11", "12", "13", "14", "15", "16", "17", "18","19","20",
            "21", "22", "23", "24", "25", "26", "27", "28","29","30",};

    public final static String[] time = new String[]{"0","1", "2", "3", "4", "5", "6", "7","8","9",
            "11", "12", "13", "14", "15", "16", "17", "18","19","20",
            "21", "22", "23", "24",  };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        ButterKnife.inject(this);
        generateInitialLineData();
        generateColumnData(Calendar.getInstance().get(Calendar.MONTH));
        generatePieData();

    }
    private void generateColumnData(int month) {

        int numSubcolumns = 1;
        int numColumns = date.length;

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            int num[] = DataReceiver.monthStatistics(month);
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue(num[i], ChartUtils.pickColor()));
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

        ColumnChart.setZoomType(ZoomType.HORIZONTAL);

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

        Viewport v = new Viewport(0, 5, time.length-1, 0);
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

    private void generatePieData(){
        int numValues = 6;

        PieChart.cancelDataAnimation();
        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues-1; ++i) {
            SliceValue sliceValue = new SliceValue(0, ChartUtils.pickColor());
            values.add(sliceValue);
        }
        SliceValue sliceValue = new SliceValue(1, ChartUtils.pickColor());
        values.add(sliceValue);

        pieData = new PieChartData(values);
        pieData.setHasLabels(false);
        pieData.setHasLabelsOnlyForSelected(true);
        pieData.setHasLabelsOutside(false);
        pieData.setHasCenterCircle(false);

        PieChart.setPieChartData(pieData);
        prepareDataAnimation();
        PieChart.startDataAnimation(5000);
    }

    private void prepareDataAnimation() {
        for (SliceValue value : pieData.getValues()) {
            value.setTarget((float) Math.random() * 30 + 15);
        }
    }
}
