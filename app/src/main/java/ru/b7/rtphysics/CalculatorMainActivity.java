package ru.b7.rtphysics;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;

/**
 * Calculator Activity. Navigation drawer enabled, back button goes to HandbookMenuActivity.
 */
public class CalculatorMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_main);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected int getNavDrawerItem() {
        return NAV_DRAWER_ITEM_CALCULATOR;
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }
}
