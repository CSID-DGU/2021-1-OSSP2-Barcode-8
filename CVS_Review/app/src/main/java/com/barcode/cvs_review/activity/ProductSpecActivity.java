package com.barcode.cvs_review.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.barcode.cvs_review.R;

public class ProductSpecActivity extends AppCompatActivity {
    TextView barcodeNumberTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productspec);

        Intent intent = getIntent();
        String barcodeNumber = intent.getStringExtra("barcode");
    }
}
