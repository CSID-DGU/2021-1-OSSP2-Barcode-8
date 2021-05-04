package com.barcode.cvs_review;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    private SignInButton btn_google;  // 구글 로그인 버튼
    private FirebaseAuth auth;  //파이어 베이스 인증 객체
    private GoogleApiClient googleApiClient; //구글 API 클라이언트 객체
    private static final int REQ_SIGN_GOOGLE= 100; //구글 로그인 결과 코드
    private ISessionCallback msessionCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {  //앱이 실행될때 수행되는 곳
        super.onCreate(savedInstanceState);
        // 만약 로그인이 되어 있는 상태라면 자동 로그인
        if(CustomPreferenceManager.getBoolean(getApplicationContext(),"userLogged")){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        setContentView(R.layout.activity_login);
        msessionCallback = new ISessionCallback() {//카카오톡 부분입니다.
            @Override
            public void onSessionOpened() {
                //로그인 요청을 한다.
                UserManagement.getInstance().me(new MeV2ResponseCallback() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        //로그인인 실패를 한 상황
                        Toast.makeText(LoginActivity.this, "로그인이 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        //세션이 닫힘..
                        Toast.makeText(LoginActivity.this, "세션이 닫혔습니다.", Toast.LENGTH_SHORT).show();
                       }

                    @Override
                    public void onSuccess(MeV2Response result) {
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra("name",result.getKakaoAccount().getProfile().getNickname());
                        intent.putExtra("profileImg", result.getKakaoAccount().getProfile().getProfileImageUrl());
                        intent.putExtra("email",result.getKakaoAccount().getEmail());
                        Toast.makeText(LoginActivity.this, "환영합니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {

            }
        };
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions)
                .build();

        auth= FirebaseAuth.getInstance();  //파이어 베이스 인증 객체 초기화.

        btn_google = findViewById(R.id.btn_google);
        btn_google.setOnClickListener(new View.OnClickListener() { //구글 로그인 버튼을 클릭했을 때 이곳을 수행
            @Override
            public void onClick(View v) {//버튼을 클릭했을 경우
                Intent intent  =  Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, REQ_SIGN_GOOGLE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { //구글 로그인 인증을 요청했을 때 결과값을 되돌리 받는 곳.
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_SIGN_GOOGLE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess())
            {
                GoogleSignInAccount account = result.getSignInAccount(); //account 라는 데이터는 구글 로그인 정보를 닮고 있다.(닉네임 , 프로필 사진....)
                resultLogin(account);  //로그인 결과값 출력하라는 메소드
            }
        }

    }

    private void resultLogin(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){ //로그인이 성공했으면...
                            Toast.makeText(LoginActivity.this, "로그인 성공",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            // intent.putExtra("nickName",account.getDisplayName());
                            // intent.putExtra("photoUrl", String.valueOf(account.getPhotoUrl()));  //사진 url을 특정 자료형을 String 형태로 변환시키는 방법
                            CustomPreferenceManager.setString(getApplicationContext(),"nickname", account.getDisplayName());    // CustomPreference
                            CustomPreferenceManager.setString(getApplicationContext(), "photoUrl", String.valueOf(account.getPhotoUrl()));
                            CustomPreferenceManager.setBoolean(getApplicationContext(), "userLogged", true);
                            startActivity(intent);
                        }else{//로그인이 실패했으면...
                            Toast.makeText(LoginActivity.this,"로그인 실패",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
