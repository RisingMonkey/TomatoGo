package monkey.rising.tomatogo.statisticView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;
import monkey.rising.tomatogo.R;

/**
 * Created by Administrator on 2016/7/19.
 */
public class TypeFragment extends Fragment {


    @InjectView(R.id.completeNum)
    TextView completeNum;
    @InjectView(R.id.undoneNum)
    TextView undoneNum;
    @InjectView(R.id.view3)
    PieChartView PieChart;
    private PieChartData pieData;

    public TypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_type, container, false);
        ButterKnife.inject(this, view);
        generatePieData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void generatePieData() {
        int numValues = 6;

        PieChart.cancelDataAnimation();
        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues - 1; ++i) {
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
