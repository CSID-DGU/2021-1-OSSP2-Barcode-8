package com.barcode.cvs_review.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.barcode.cvs_review.CustomPreferenceManager;
import com.barcode.cvs_review.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity{

    CardView sevenElevenListCardView;
    CardView cuListCardView;
    CardView gs25ListCardView;
    CardView etcListCardView;
    

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sevenElevenListCardView = findViewById(R.id.sevenElevenCardView);
        cuListCardView = findViewById(R.id.cuCardView);
        gs25ListCardView = findViewById(R.id.gs25CardView);
        etcListCardView = findViewById(R.id.etcCardView);

        sevenElevenListCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
                intent.putExtra("selected", 0);
                startActivity(intent);
            }
        });

        cuListCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
                intent.putExtra("selected", 1);
                startActivity(intent);
            }
        });

        gs25ListCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
                intent.putExtra("selected", 2);
                startActivity(intent);
            }
        });

        etcListCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
                intent.putExtra("selected", 3);
                startActivity(intent);
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
                Intent setting_intent = new Intent(getApplicationContext(), SettingActivity.class);
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
            // 어플리케이션 카메라 권한 부여
            PermissionListener permissionListener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    Toast.makeText(getApplicationContext(), "카메라 권한 승인", Toast.LENGTH_SHORT).show();
                    Intent barcode_intent = new Intent(getApplicationContext(), BarcodeScanActivity.class);
                    startActivityForResult(barcode_intent, 101);
                }

                @Override
                public void onPermissionDenied(List<String> deniedPermissions) {
                    Toast.makeText(getApplicationContext(), "카메라 권한 거부", Toast.LENGTH_SHORT).show();
                }
            };
            TedPermission.with(getApplicationContext())
                    .setPermissionListener(permissionListener)
                    .setRationaleMessage("바코드를 스캔하기 위해 카메라 접근 권한이 필요합니다")
                    .setDeniedMessage("카메라 접근 권한이 거부되었습니다\n[설정] > [권한]에서 권한을 다시 허용하실 수 있습니다")
                    .setPermissions(Manifest.permission.CAMERA)
                    .check();
        }
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

