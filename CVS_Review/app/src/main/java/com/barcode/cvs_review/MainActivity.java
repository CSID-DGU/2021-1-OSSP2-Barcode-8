package com.barcode.cvs_review;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{
    MainFragment mainFragment;
    SevenFragment sevenFragment;
    CuFragment cuFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.mainFragment);
        sevenFragment = new SevenFragment();
        cuFragment = new CuFragment();
    }

    public void onFragmentChanged(int index) {
        if (index == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment).commit();
        } else if (index == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, sevenFragment).commit();
        } else if (index == 2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, cuFragment).commit();
        }
    }

}