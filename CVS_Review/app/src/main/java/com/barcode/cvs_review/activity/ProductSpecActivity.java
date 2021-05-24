package com.barcode.cvs_review.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.barcode.cvs_review.R;
import com.bumptech.glide.Glide;

public class ProductSpecActivity extends AppCompatActivity {
    TextView product_name;
    ImageView product_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productspec);

        product_image = findViewById(R.id.imageView);
        product_name = findViewById(R.id.textView);

        Intent intent = getIntent();
        String PRODUCT_NAME = intent.getStringExtra("PRODUCT_NAME");
        String PRODUCT_IMAGE = intent.getStringExtra("PRODUCT_IMAGE");

        product_name.setText(PRODUCT_NAME);
        Glide.with(getApplicationContext()).load(PRODUCT_IMAGE).into(product_image);

    }
}
