package com.chichi.imoocdaily.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.chichi.imoocdaily.bean.CostBean;
import com.chichi.imoocdaily.R;
import com.chichi.imoocdaily.common.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by chichi on 2017/4/30.
 */

public class ChartsActivity extends Activity {

  private LineChartView mChart;

  private Map<String, Integer> table = new TreeMap<>();

  private LineChartData mData;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chart);
    mChart = (LineChartView) findViewById(R.id.chart);

    List<CostBean> allData = (List<CostBean>) getIntent().getSerializableExtra(Constants.COST_LIST);

    generateValues(allData);

    generateData();
  }

  private void generateValues(List<CostBean> allData) {
    if (allData != null) {
      for (int i = 0; i < allData.size(); i++) {
        CostBean costBean = allData.get(i);
        String costDate = costBean.costDate;
        int costMoney = Integer.parseInt(costBean.costMoney);

        if (!table.containsKey(costDate)) {
          table.put(costDate, costMoney);
        } else {
          int originMoney = table.get(costDate);
          table.put(costDate, originMoney + costMoney);
        }
      }
    }
  }

  private void generateData() {
    List<Line> lines = new ArrayList<>();
    List<PointValue> values = new ArrayList<>();

    int indexX = 0;

    for (Integer value : table.values()) {
      values.add(new PointValue(indexX, value));
      indexX++;
    }

    Line line = new Line(values);
    line.setColor(ChartUtils.COLOR_BLUE);
    line.setShape(ValueShape.CIRCLE);
    line.setPointColor(ChartUtils.COLOR_ORANGE);
    lines.add(line);

    mData = new LineChartData();
    mData.setLines(lines);

    mChart.setLineChartData(mData);
  }
}
