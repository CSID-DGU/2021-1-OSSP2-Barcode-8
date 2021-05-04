package com.barcode.cvs_review;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.IOException;
import java.util.List;

public class BarcodeScanActivity extends AppCompatActivity {
    CameraSource cameraSource;
    SurfaceView surfaceView;

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
                home_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(home_intent, 101);
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        surfaceView = (SurfaceView)findViewById(R.id.cameraSurface);
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.EAN_13)
                .build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(29.8f)
                .setRequestedPreviewSize(500,500)
                .setAutoFocusEnabled(true)
                .build();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                try {   // try-catch 문은 Camera 권한획득을 위한 권장사항
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());  // Mobile Vision API 시작
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                cameraSource.stop();
            }

            @Override
            public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodeSparseArray = detections.getDetectedItems();
                if(barcodeSparseArray.size() != 0){
                    String barcodeNumber = barcodeSparseArray.valueAt(0).displayValue;
                    Handler toastHandler = new Handler(Looper.getMainLooper());
                    toastHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent barcodeInfoIntent = new Intent(getApplicationContext(), ProductSpecActivity.class);
                            barcodeInfoIntent.putExtra("barcode", barcodeNumber);
                            startActivity(barcodeInfoIntent);
                            barcodeDetector.release();
                            finish();
                        }
                    },0);
                }
            }
        });

    }

}