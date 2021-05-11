package com.barcode.cvs_review;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

public class ProductListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlist);
        Intent intent = getIntent();
        int selected = intent.getIntExtra("selected",3);
        
        if(selected == 0){
            // 세븐일레븐 상품 목록
            getSupportActionBar().setTitle("세븐일레븐");
        }
        else if(selected == 1){
            // CU 상품 목록
            getSupportActionBar().setTitle("CU");
        }
        else if (selected == 2){
            // GS25 상품 목록
            getSupportActionBar().setTitle("GS25");
        }
        else{
            // 공통 상품 목록
            getSupportActionBar().setTitle("공통 상품");
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new FABClickListener());
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
}
