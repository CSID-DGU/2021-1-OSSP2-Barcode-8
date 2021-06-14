package com.barcode.cvs_review.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.barcode.cvs_review.CustomPreferenceManager;
import com.barcode.cvs_review.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class InsertReview extends AppCompatActivity {

    private static String IP_ADDRESS = "118.67.128.31";
    private static String TAG = "phptest";

    String PRODUCT_NAME;
    String BARCODE;
    String USER_INFO_ID;
    TextView product_name;
    TextInputEditText commentView;
    String PRODUCT_COMMENT;
    String PRODUCT_POINT;
    Button send_button;
    RatingBar product_ratingbar;
    float rating_f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_review);

        product_name = findViewById(R.id.textView3);
        commentView = findViewById(R.id.textbox_comment);
        send_button = findViewById(R.id.button_send);
        product_ratingbar = findViewById(R.id.product_ratingbar);
        product_ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rating_f = rating;
            }
        });

        Intent intent = getIntent();
        PRODUCT_NAME = intent.getStringExtra("PRODUCT_NAME");
        BARCODE = intent.getStringExtra("BARCODE");
        USER_INFO_ID = CustomPreferenceManager.getString(getApplicationContext(),"userEmail");
        product_name.setText(PRODUCT_NAME);


        Objects.requireNonNull(getSupportActionBar()).setTitle("Review");

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GetData task = new GetData();
                PRODUCT_POINT = Float.toString(rating_f);
                PRODUCT_COMMENT = Objects.requireNonNull(commentView.getText()).toString();
                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/insert_review.php", BARCODE, PRODUCT_POINT, USER_INFO_ID, PRODUCT_COMMENT);
                Toast.makeText(getApplicationContext(),"정상적으로 등록되었습니다", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
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

    private class InsertData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(InsertReview.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {
            String BARCODE = (String)params[1];
            String PRODUCT_POINT = (String)params[2];
            String USER_INFO_ID = (String)params[3];
            String COMMENTS= (String)params[4];

            String serverURL = (String)params[0];
            String postParameters = "BARCODE=" + BARCODE + "&PRODUCT_POINT=" + PRODUCT_POINT +"&USER_INFO_ID="+USER_INFO_ID+"&COMMENTS="+COMMENTS;

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

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


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(InsertReview.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            // mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){
                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_LONG).show();
            }
            else {

            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = "PRODUCT_NAME="+params[1];


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
    }


}



