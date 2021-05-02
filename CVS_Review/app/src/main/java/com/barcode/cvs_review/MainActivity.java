package com.barcode.cvs_review;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity{

    CardView profileInfoCardView;
    ImageView profileImageView;
    TextView nickname;
    

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileInfoCardView = findViewById(R.id.accountCardView);
        // 카드뷰 프로필 사진 지정
        profileImageView = findViewById(R.id.imageView);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));
        Glide.with(MainActivity.this)
                .load(Uri.parse(CustomPreferenceManager.getString(getApplicationContext(), "photoUrl")))
                .apply(requestOptions)
                .placeholder(R.drawable.egg)
                .error(R.drawable.egg)
                .into(profileImageView);
        // 카드뷰 프로필 닉네임 지정
        nickname = findViewById(R.id.textView);
        nickname.setText(CustomPreferenceManager.getString(getApplicationContext(), "nickname"));
        
        // 카드뷰 클릭시 행동
        profileInfoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });
        
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new FABClickListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_actions, menu) ;

        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                /*
                Intent home_intent = new Intent(getApplicationContext(),MainActivity.class);
                home_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(home_intent, 101);
                */
                reload();   // refresh screen 
                return true;
            case R.id.action_settings:
                Intent setting_intent = new Intent(getApplicationContext(),SettingActivity.class);
                setting_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(setting_intent, 101);
                return true;
            default :
                return super.onOptionsItemSelected(item) ;
        }
    }

    class FABClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent barcode_intent = new Intent(getApplicationContext(), BarcodeScanActivity.class);
            startActivityForResult(barcode_intent, 101);
        }
    }

    /* Dialog For Account Logout */
    public void showLogoutDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout").setMessage("Do you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Logout 다이얼로그 Yes 누른 경우
                CustomPreferenceManager.clear(getApplicationContext()); // preference manager 값 다 정리
                AuthUI.getInstance().signOut(MainActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "로그아웃 성공", Toast.LENGTH_SHORT).show();  // toast로 알림
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);   // 다시 login activity로 보내고
                        startActivity(intent); 
                        finish();   // Mainactivity 종료
                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            // Logout 다이얼로그 No 누른 경우
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();   // 창 없애기
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /* refresh screen */
    public void reload(){
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }


}

