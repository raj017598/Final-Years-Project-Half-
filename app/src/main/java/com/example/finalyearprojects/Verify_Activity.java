package com.example.finalyearprojects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class Verify_Activity extends Activity {

    private TextInputLayout txtOTP;
    private Button btnVerify;
    private TextView slogan;
    private ProgressBar progress_bar;
    String verificationCodeSentToSystem;
    String name, email, password, phone;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_);
        txtOTP = findViewById(R.id.txtOtp);
        showSoftKeyboard(txtOTP);
        btnVerify = (Button)findViewById(R.id.btnVerify);
        slogan = (TextView)findViewById(R.id.slogan);
        progress_bar = (ProgressBar)findViewById(R.id.progress_bar);
        progress_bar.setVisibility(View.VISIBLE);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        phone = getIntent().getStringExtra("phone");
        slogan.setText("Enter OTP sent to +91 " + phone);
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");

        sendCodetoUser(phone);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = txtOTP.getEditText().getText().toString();
                if(code.isEmpty() || code.length() != 6)
                {
                    txtOTP.setError("Wrong OTP...");
                    txtOTP.requestFocus();
                    showSoftKeyboard(txtOTP);
                    return;
                }
                progress_bar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        });
    }

    private void sendCodetoUser(String phone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + phone,60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,mCallback);
    }
    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            //code has been sent to another device
            verificationCodeSentToSystem = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if(code!=null)
            {
                progress_bar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            Toast.makeText(Verify_Activity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            progress_bar.setVisibility(View.GONE);
        }
    };
    private  void verifyCode(String CodebyUser)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeSentToSystem,CodebyUser);
        signInTheUserByCredentials(credential);
    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(Verify_Activity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                    User user = new User(name, email, password, phone);
                    reference.child(phone).setValue(user);
                    Toast.makeText(Verify_Activity.this,"Your account has been created successfully!",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Verify_Activity.this,Login_Activity.class);
                    //if users press back, he cant come back to this page..so i use flags
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(Verify_Activity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Verify_Activity.this,Signup_Activity.class);
        startActivity(i);
    }
}
