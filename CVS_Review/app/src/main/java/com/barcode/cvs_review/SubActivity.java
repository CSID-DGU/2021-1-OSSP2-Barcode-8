package com.barcode.cvs_review;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class SubActivity extends AppCompatActivity {
    private String strNick, strProfileImg, strEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        Intent intent = getIntent();
        strNick = intent.getStringExtra("kakaoname");
        strProfileImg = intent.getStringExtra("profileImg");
        strEmail = intent.getStringExtra("email");

        TextView tv_nick = findViewById(R.id.tv_nickName);
        TextView tv_email = findViewById(R.id.tv_email);
        ImageView iv_profile = findViewById(R.id.iv_profile);

        //닉네임 set
        tv_nick.setText(strNick);
        //이메일 set
        tv_email.setText(strEmail);
        //프로필 이미지 사진 set
        Glide.with(this).load(strProfileImg).into(iv_profile);

    }
}