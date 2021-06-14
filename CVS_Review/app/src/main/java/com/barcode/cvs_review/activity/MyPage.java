package com.barcode.cvs_review.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.barcode.cvs_review.CommentAdapter;
import com.barcode.cvs_review.MyCommentAdapter;
import com.barcode.cvs_review.CustomPreferenceManager;
import com.barcode.cvs_review.Database;
import com.barcode.cvs_review.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyPage extends AppCompatActivity {

    private static String IP_ADDRESS = "118.67.128.31";
    private static String TAG = "phptest";
    String PRODUCT_NAME;
    String USER_INFO_ID;
    String AVE_GRADE;
    String COMMENTS;
    ArrayList<Database> mArrayList;
    MyCommentAdapter mAdapter;
    RecyclerView mRecyclerView;
    private String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        Intent intent = getIntent();
        mRecyclerView = findViewById(R.id.mycomment_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mArrayList = new ArrayList<>();
        mAdapter = new MyCommentAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

        PRODUCT_NAME = intent.getStringExtra("PRODUCT_NAME");
        USER_INFO_ID = CustomPreferenceManager.getString(getApplicationContext(),"userEmail");
        AVE_GRADE = intent.getStringExtra("AVE_GRADE");
        COMMENTS = intent.getStringExtra("COMMENTS");


        if(USER_INFO_ID != null){

            GetData task = new GetData();
            task.execute("http://" + IP_ADDRESS + "/query_myreview.php", USER_INFO_ID);
        }

        mAdapter.notifyDataSetChanged();

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));



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
                Intent home_intent = new Intent(getApplicationContext(),MainActivity.class);
                home_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(home_intent, 101);
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

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MyPage.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            // mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){

                // mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = "USER_INFO_ID="+params[1];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }

        private void showResult(){

            String TAG_JSON= "barcode";
            String TAG_BARCODE="BARCODE";
            String TAG_CVS_NAME = "CVS_NAME";
            String TAG_PRODUCT_NAME = "PRODUCT_NAME";
            String TAG_PRODUCT_IMAGE = "PRODUCT_IMAGE";
            String TAG_AVE_GRADE = "AVE_GRADE";
            String TAG_USER_ID = "USER_INFO_ID";
            String TAG_COMMENT = "COMMENTS";
            String TAG_PRODUCT_POINT = "PRODUCT_POINT";

            try {
                JSONObject jsonObject = new JSONObject(mJsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

                for(int i=0;i<jsonArray.length();i++){

                    JSONObject item = jsonArray.getJSONObject(i);

                    String PRODUCT_NAME = item.getString(TAG_PRODUCT_NAME);
                    String COMMENTS = item.getString(TAG_COMMENT);
                    String PRODUCT_POINT = item.getString(TAG_PRODUCT_POINT);

                    Database database = new Database();

                    database.setPRODUCT_NAME(PRODUCT_NAME);
                    database.setPRODUCT_POINT(PRODUCT_POINT);
                    database.setCOMMENTS(COMMENTS);
                    Log.d("리뷰: ", COMMENTS);

                    mArrayList.add(database);
                    mAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {

                Log.d(TAG, "showResult : ", e);
            }

        }
    }
}