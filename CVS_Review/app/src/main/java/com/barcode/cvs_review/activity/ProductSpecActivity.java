package com.barcode.cvs_review.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barcode.cvs_review.CommentAdapter;
import com.barcode.cvs_review.CustomPreferenceManager;
import com.barcode.cvs_review.Database;
import com.barcode.cvs_review.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

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

public class ProductSpecActivity extends AppCompatActivity {
    TextView product_name;
    ImageView product_image;
    RatingBar rating;
    public String PRODUCT_NAME;
    public String PRODUCT_IMAGE;
    String USER_ID;
    String COMMENT;
    public String AVE_GRADE;
    String BARCODE;
    String PRODUCT_POINT;
    Database database = new Database();

    ArrayList<Database> mArrayList;
    CommentAdapter mAdapter;
    RecyclerView mRecyclerView;

    private static String IP_ADDRESS = "118.67.128.31";
    private static String TAG = "phptest";


    private String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productspec);

        mRecyclerView = findViewById(R.id.mycomment_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mArrayList = new ArrayList<>();
        mAdapter = new CommentAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

        product_image = findViewById(R.id.imageView);
        product_name = findViewById(R.id.textView);
        rating = findViewById(R.id.ratingBar);

        Intent intent = getIntent();
        PRODUCT_NAME = intent.getStringExtra("PRODUCT_NAME");
        PRODUCT_IMAGE = intent.getStringExtra("PRODUCT_IMAGE");
        AVE_GRADE = intent.getStringExtra("AVE_GRADE");
        if(AVE_GRADE == null){
            AVE_GRADE = "0";
        }
        BARCODE = intent.getStringExtra("BARCODE");

        GetData task = new GetData();
        task.execute("http://" + IP_ADDRESS + "/getjson_reviewList.php", BARCODE);
        PRODUCT_NAME = CustomPreferenceManager.getString(getApplicationContext(),"spec_product_name");
        PRODUCT_IMAGE = CustomPreferenceManager.getString(getApplicationContext(),"spec_product_image");
        //AVE_GRADE = mArrayList.get(0).getAVE_GRADE();
        product_name.setText(PRODUCT_NAME);
        if(AVE_GRADE.equals("null")) {
            AVE_GRADE = "0";
        }
        rating.setRating(Float.parseFloat(AVE_GRADE));

        mAdapter.notifyDataSetChanged();

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));
        Glide.with(getApplicationContext()).load(PRODUCT_IMAGE).apply(requestOptions).into(product_image);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.spec_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.writeReviewButton:
                Intent intent = new Intent(getApplicationContext(),InsertReview.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("PRODUCT_NAME",PRODUCT_NAME);
                intent.putExtra("BARCODE", BARCODE);
                startActivityForResult(intent, 101);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ProductSpecActivity.this,
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

        /*@Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String TAG_JSON="barcode";
            String TAG_BARCODE="BARCODE";
            String TAG_CVS_NAME = "CVS_NAME";
            String TAG_PRODUCT_NAME = "PRODUCT_NAME";
            String TAG_PRODUCT_IMAGE = "PRODUCT_IMAGE";
            String TAG_AVE_GRADE = "AVE_GRADE";
            String TAG_USER_ID = "USER_INFO_ID";
            String TAG_COMMENT = "COMMNETS";
            progressDialog.dismiss();
            // mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);
            if (result == null){
                // mTextViewResult.setText(errorString);
            }
            else {
                mJsonString = result;
                try {
                    JSONObject jsonObject = new JSONObject(mJsonString);
                    JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject item = jsonArray.getJSONObject(i);
                        String BARCODE = item.getString(TAG_BARCODE);
                        String CVS_NAME = item.getString(TAG_CVS_NAME);
                        PRODUCT_NAME = item.getString(TAG_PRODUCT_NAME);
                        PRODUCT_IMAGE = item.getString(TAG_PRODUCT_IMAGE);
                        AVE_GRADE = item.getString(TAG_AVE_GRADE);
                        USER_ID = item.getString(TAG_USER_ID);
                        COMMENT = item.getString(TAG_COMMENT);
                        PRODUCT_POINT = item.getString(PRODUCT_POINT);
                        database.setBARCODE(BARCODE);
                        database.setCVS_NAME(CVS_NAME);
                        database.setPRODUCT_NAME(PRODUCT_NAME);
                        database.setPRODUCT_IMAGE_URL(PRODUCT_IMAGE);
                        database.setAVE_GRADE(AVE_GRADE);
                        database.setUSER_ID(USER_ID);
                        database.setCOMMENTS(COMMENT);
                        database.setPRODUCT_POINT(PRODUCT_POINT);
                        PRODUCT_IMAGE = database.getPRODUCT_IMAGE_URL();
                        PRODUCT_NAME = database.getPRODUCT_NAME();
                        AVE_GRADE = database.getAVE_GRADE();
                        product_name.setText(PRODUCT_NAME);
                        USER_ID = database.getUSER_ID();
                        COMMENT = database.getCOMMENTS();
                        PRODUCT_POINT = database.getPRODUCT_POINT();
                        rating.setRating(Float.parseFloat(AVE_GRADE));
                    }
                    showResult();
                } catch (JSONException e) {
                    Log.d(TAG, "showResult : ", e);
                }
            }
        }*/

        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = "BARCODE="+params[1];


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

            String TAG_JSON="barcode";
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

                    String BARCODE = item.getString(TAG_BARCODE);
                    String CVS_NAME = item.getString(TAG_CVS_NAME);
                    PRODUCT_NAME = item.getString(TAG_PRODUCT_NAME);
                    CustomPreferenceManager.setString(getApplicationContext(),"spec_product_name", PRODUCT_NAME);
                    PRODUCT_IMAGE = item.getString(TAG_PRODUCT_IMAGE);
                    CustomPreferenceManager.setString(getApplicationContext(),"spec_product_image", PRODUCT_IMAGE);
                    // AVE_GRADE = item.getString(TAG_AVE_GRADE);
                    String USER_ID = item.getString(TAG_USER_ID);
                    String COMMENT = item.getString(TAG_COMMENT);
                    String PRODUCT_POINT = item.getString(TAG_PRODUCT_POINT);

                    Database database = new Database();

                    database.setBARCODE(BARCODE);
                    database.setCVS_NAME(CVS_NAME);
                    database.setPRODUCT_NAME(PRODUCT_NAME);
                    database.setPRODUCT_IMAGE_URL(PRODUCT_IMAGE);
                    //database.setAVE_GRADE(AVE_GRADE);
                    database.setPRODUCT_POINT(PRODUCT_POINT);
                    database.setUSER_ID(USER_ID);
                    database.setCOMMENTS(COMMENT);
                    Log.d("리뷰: ", COMMENT);

                    mArrayList.add(database);
                    mAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {

                Log.d(TAG, "showResult : ", e);
            }

        }
    }

}