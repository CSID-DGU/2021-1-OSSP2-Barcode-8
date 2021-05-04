package com.barcode.cvs_review;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProductSpecActivity extends AppCompatActivity {
    TextView barcodeNumberTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productspec);

        Intent intent = getIntent();
        String barcodeNumber = intent.getStringExtra("barcode");
        barcodeNumberTextView = findViewById(R.id.barcode);
        barcodeNumberTextView.setText(barcodeNumber);
    }
}
