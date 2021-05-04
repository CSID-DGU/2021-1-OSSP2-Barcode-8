package com.barcode.cvs_review;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

public class BarcodeScanActivity extends AppCompatActivity {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        // 어플리케이션 카메라 권한 부여
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(getApplicationContext(), "카메라 권한 승인", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getApplicationContext(), "카메라 권한 거부", Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("바코드를 스캔하기 위해 카메라 접근 권한이 필요합니다")
                .setDeniedMessage("카메라 접근 권한이 거부되었습니다\n[설정] > [권한]에서 권한을 다시 허용하실 수 있습니다")
                .setPermissions(Manifest.permission.CAMERA)
                .check();
    }

}