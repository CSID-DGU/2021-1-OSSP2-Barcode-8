package com.barcode.cvs_review.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.barcode.cvs_review.Database;
import com.barcode.cvs_review.R;
import com.barcode.cvs_review.UsersAdapter;
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
    String PRODUCT_NAME;
    String PRODUCT_IMAGE;
    Database database = new Database();

    private static String IP_ADDRESS = "118.67.128.31";
    private static String TAG = "phptest";


    private String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productspec);

        product_image = findViewById(R.id.imageView);
        product_name = findViewById(R.id.textView);

        Intent intent = getIntent();
        PRODUCT_NAME = intent.getStringExtra("PRODUCT_NAME");
        PRODUCT_IMAGE = intent.getStringExtra("PRODUCT_IMAGE");
        String barcode = intent.getStringExtra("barcode");
        if(barcode != null){
            GetData task = new GetData();
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"http://" + IP_ADDRESS + "/getjson_barcode.php", barcode);
        }
        product_name.setText(PRODUCT_NAME);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));
        Glide.with(getApplicationContext()).load(PRODUCT_IMAGE).apply(requestOptions).into(product_image);

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

            String TAG_JSON="barcode";
            String TAG_BARCODE="BARCODE";
            String TAG_CVS_NAME = "CVS_NAME";
            String TAG_PRODUCT_NAME = "PRODUCT_NAME";
            String TAG_PRODUCT_IMAGE = "PRODUCT_IMAGE";

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

                        database.setBARCODE(BARCODE);
                        database.setCVS_NAME(CVS_NAME);
                        database.setPRODUCT_NAME(PRODUCT_NAME);
                        database.setPRODUCT_IMAGE_URL(PRODUCT_IMAGE);

                        PRODUCT_IMAGE = database.getPRODUCT_IMAGE_URL();
                        PRODUCT_NAME = database.getPRODUCT_NAME();
                        product_name.setText(PRODUCT_NAME);
                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));
                        Glide.with(getApplicationContext()).load(PRODUCT_IMAGE).apply(requestOptions).into(product_image);
                    }

                } catch (JSONException e) {

                    Log.d(TAG, "showResult : ", e);
                }
            }
        }

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
    }

}
