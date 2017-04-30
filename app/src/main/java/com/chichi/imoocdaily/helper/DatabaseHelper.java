package com.chichi.imoocdaily.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.chichi.imoocdaily.bean.CostBean;
import com.chichi.imoocdaily.common.Constants;

/**
 * Created by chichi on 2017/4/30.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

  public DatabaseHelper(Context context) {
    super(context, Constants.DATABASE_COST, null, 1);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    db.execSQL("create table if not exists "
        + Constants.TABLE_COST
        + "(id integer primary key,"
        + Constants.COST_TITLE
        + " varchar,"
        + Constants.COST_DATE
        + " varchar,"
        + Constants.COST_MONEY
        + " varchar)");
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }

  public void insertCost(CostBean costBean) {
    SQLiteDatabase database = getWritableDatabase();
    ContentValues cv = new ContentValues();
    cv.put(Constants.COST_TITLE, costBean.costTitle);
    cv.put(Constants.COST_DATE, costBean.costDate);
    cv.put(Constants.COST_MONEY, costBean.costMoney);
    database.insert(Constants.TABLE_COST, null, cv);
  }

  public Cursor getAllCostData() {
    SQLiteDatabase database = getWritableDatabase();
    return database.query(Constants.TABLE_COST, null, null, null, null, null, Constants.COST_DATE + " ASC");
  }

  public void deleteAllData() {
    SQLiteDatabase database = getWritableDatabase();
    database.delete(Constants.TABLE_COST, null, null);
  }
}
