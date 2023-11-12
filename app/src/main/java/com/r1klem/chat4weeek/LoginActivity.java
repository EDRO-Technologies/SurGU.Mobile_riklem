package com.r1klem.chat4weeek;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    int coun = 0;
    //firestore - testuser@test.com testuser
    int count = 0;
    EditText mEmail, mPassword;
    Button mLoginBtn, mShowPswrdBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    String userID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mLoginBtn = findViewById(R.id.login);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();


        /* final ProgressBar loadingProgressBar = binding.loading; */

        ImageButton showpassword = (ImageButton) findViewById(R.id.showpswrd);
        Button enter = (Button) findViewById(R.id.login);



        showpassword.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch ( event.getAction() ) {

                    case MotionEvent.ACTION_UP:
                        mPassword.setInputType(InputType.TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD);
                        mPassword.setTypeface(null, Typeface.BOLD);
                        mPassword.setSelection(mPassword.length());
                        break;

                    case MotionEvent.ACTION_DOWN:
                        mPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        mPassword.setTypeface(null, Typeface.BOLD);
                        mPassword.setSelection(mPassword.length());
                        break;
                }
                return true;
            }
        });



        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Необходимо ввести свой Email");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Необходимо ввести пароль");
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Пароль должен содержать >5 символов");
                    return;
                }


                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            FirebaseAuth.AuthStateListener mAuthListener;
                            mAuthListener = new FirebaseAuth.AuthStateListener() {
                                @Override
                                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    if(user !=null){
                                        updateUI(user);
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        finish();
                                    }
                                }
                            };

                            fAuth.addAuthStateListener(mAuthListener);



                        } else {
                            Toast.makeText(getApplicationContext(), "Ошибка!(проверьте правильность ввода эл.почты и пароля)", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    private void updateUI(FirebaseUser userr) {
        if (userr != null) {

        } else {

        }
    }

    private void createOneButtonAlertDialog(String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setPositiveButton(Html.fromHtml("<b><font color=black>" + "OK" + "</font><b>"),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                    }
                });
        builder.show();


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}