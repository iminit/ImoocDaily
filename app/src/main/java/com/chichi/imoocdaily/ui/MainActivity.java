package com.chichi.imoocdaily.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import com.chichi.imoocdaily.bean.CostBean;
import com.chichi.imoocdaily.adapter.CostListAdapter;
import com.chichi.imoocdaily.common.Constants;
import com.chichi.imoocdaily.helper.DatabaseHelper;
import com.chichi.imoocdaily.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private List<CostBean> mCostBeanList = new ArrayList<>();

  private DatabaseHelper mDatabaseHelper;

  private CostListAdapter mAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    mDatabaseHelper = new DatabaseHelper(this);

    ListView costList = (ListView) findViewById(R.id.lv_main);
    initCostData();
    mAdapter = new CostListAdapter(this, mCostBeanList);
    costList.setAdapter(mAdapter);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View viewDialog = inflater.inflate(R.layout.dialog_cost, null);
        final EditText title = (EditText) viewDialog.findViewById(R.id.et_cost_title);
        final EditText money = (EditText) viewDialog.findViewById(R.id.et_cost_money);
        final DatePicker date = (DatePicker) viewDialog.findViewById(R.id.dp_cost_date);

        builder.setView(viewDialog);
        builder.setTitle(Constants.TITLE_NEW_COST);
        builder.setPositiveButton(Constants.STATUS_OK, new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            CostBean costBean = new CostBean();
            costBean.costTitle = title.getText().toString();
            costBean.costMoney = money.getText().toString();
            costBean.costDate =
                date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDayOfMonth();

            mDatabaseHelper.insertCost(costBean);

            mCostBeanList.add(costBean);
            mAdapter.notifyDataSetChanged();
          }
        });
        builder.setNegativeButton(Constants.STATUS_CANCEL, null);
        builder.create().show();
      }
    });
  }

  private void initCostData() {
    //mDatabaseHelper.deleteAllData();
    //for (int i = 0; i < 6; i++) {
    //  CostBean costBean = new CostBean();
    //  costBean.costTitle = i + "mock";
    //  costBean.costDate = "11-11";
    //  costBean.costMoney = "20";
    //  mDatabaseHelper.insertCost(costBean);
    //}
    Cursor cursor = mDatabaseHelper.getAllCostData();
    if (cursor == null) {
      return;
    }
    while (cursor.moveToNext()) {
      CostBean costBean = new CostBean();
      costBean.costTitle = cursor.getString(cursor.getColumnIndex("cost_title"));
      costBean.costDate = cursor.getString(cursor.getColumnIndex("cost_date"));
      costBean.costMoney = cursor.getString(cursor.getColumnIndex("cost_money"));
      mCostBeanList.add(costBean);
    }
    cursor.close();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.action_chart) {
      Intent intent = new Intent(MainActivity.this, ChartsActivity.class);
      intent.putExtra(Constants.COST_LIST, (Serializable) mCostBeanList);
      startActivity(intent);
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
