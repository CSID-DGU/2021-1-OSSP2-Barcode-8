package com.barcode.cvs_review;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity{
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_actions, menu) ;

        return true ;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent home_intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivityForResult(home_intent, 101);
                return true;
            case R.id.action_settings:
                Intent setting_intent = new Intent(getApplicationContext(),SettingActivity.class);
                startActivityForResult(setting_intent, 101);
                return true;
            default :
                return super.onOptionsItemSelected(item) ;
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new FABClickListener());
    }
    class FABClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent barcode_intent = new Intent(getApplicationContext(),BarcodeActivity.class);
            startActivityForResult(barcode_intent, 101);
        }
    }
}