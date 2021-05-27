package com.barcode.cvs_review.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.barcode.cvs_review.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

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
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));
        Glide.with(getApplicationContext()).load(PRODUCT_IMAGE).apply(requestOptions).into(product_image);

    }
}
