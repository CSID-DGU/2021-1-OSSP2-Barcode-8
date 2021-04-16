package com.barcode.cvs_review;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ResultActivity extends AppCompatActivity {
    private TextView tv_result;// 닉네임 text
    private ImageView iv_profile;//이미지 뷰


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String nickName =  intent.getStringExtra("nickName"); //MainActivity로 부터 닉네임 전달 받음
        String photoUrl =  intent.getStringExtra("photoUrl"); //MainActivity로 부터 프로필 사진url 전달 받음

        tv_result = findViewById(R.id.tv_result);
        tv_result.setText(nickName);

        iv_profile= findViewById(R.id.iv_profile);
        Glide.with(this).load(photoUrl).into(iv_profile);
    }
}