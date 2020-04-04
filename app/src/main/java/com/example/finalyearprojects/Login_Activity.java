package com.example.finalyearprojects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.content.SharedPreferences.*;

public class Login_Activity extends Activity {

    private TextInputLayout txtEmail, txtPassword;
    private TextView forget;
    private Button btnLogin;
    private TextView btnSignup;
    private ProgressBar progress;
    String forgetphone, forgetpassword;

    SharedPreferences sharedPreferences;

    String Email, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        progress = (ProgressBar)findViewById(R.id.progress);
        progress.setVisibility(View.GONE);
        txtEmail = findViewById(R.id.txtEmail);
        showSoftKeyboard(txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        int integer = sharedPreferences.getInt("first",0);
        if(integer != 0)
        {
            Intent it = new Intent(Login_Activity.this,MainActivity.class);
            startActivity(it);
        }

        forget = (TextView)findViewById(R.id.txtForget);

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login_Activity.this);
                builder.setMessage("Enter Phone Number");
                builder.setTitle("forget password");
                final  EditText input = new EditText(Login_Activity.this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(layoutParams);
                builder.setView(input);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        forgetphone = input.getText().toString();
                        if(forgetphone.length()!=10)
                        {
                            Toast.makeText(Login_Activity.this,"enter valid phone number",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                            Query checkuser = reference.orderByChild("phone").equalTo(forgetphone);
                            checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists())
                                    {
                                        forgetpassword = dataSnapshot.child(forgetphone).child("password").getValue(String.class);
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(Login_Activity.this);
                                        builder1.setMessage("Your password is:" + forgetpassword);
                                        builder1.setTitle("Password: ");
                                        AlertDialog alertDialog1 = builder1.create();
                                        alertDialog1.show();
                                        builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                    }
                                    else
                                    {
                                        Toast.makeText(Login_Activity.this,"no record of this phone number...please signup",Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnSignup = (TextView)findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login_Activity.this,Signup_Activity.class);
                startActivity(i);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                Email = txtEmail.getEditText().getText().toString();
                Password = txtPassword.getEditText().getText().toString();
                if(Email.isEmpty())
                {
                    txtEmail.setError("Enter Phone Number");
                    txtEmail.requestFocus();
                    progress.setVisibility(View.GONE);
                    return;
                }
                else if(!Patterns.PHONE.matcher(Email).matches())
                {
                    txtEmail.setError("Enter Valid Phone Number");
                    txtEmail.requestFocus();
                    progress.setVisibility(View.GONE);
                    return;
                }
                else if(Email.length()!=10)
                {
                    txtEmail.setError("Enter Valid Phone Number");
                    txtEmail.requestFocus();
                    progress.setVisibility(View.GONE);
                    return;
                }
                else if(Password.isEmpty())
                {
                    txtPassword.setError("enter password");
                    txtPassword.requestFocus();
                    progress.setVisibility(View.GONE);
                    return;
                }
                else if(Email.isEmpty() && Password.isEmpty())
                {
                    Toast.makeText(Login_Activity.this,"enter phone no and password",Toast.LENGTH_LONG).show();
                }
                else if(!(Email.isEmpty() && Password.isEmpty()))
                {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                    Query checkuser = reference.orderByChild("phone").equalTo(Email);
                    checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists())
                            {
                                txtEmail.setError(null);
                                String password = dataSnapshot.child(Email).child("password").getValue(String.class);

                                if(password.equals(Password))
                                {
                                    txtEmail.setError(null);
                                    String nameFromDB = dataSnapshot.child(Email).child("name").getValue(String.class);
                                    String emailFromDB = dataSnapshot.child(Email).child("email").getValue(String.class);
                                    String phoneFromDB = dataSnapshot.child(Email).child("phone").getValue(String.class);
                                    Intent i = new Intent(getApplicationContext(),Home_Activity.class);
                                    i.putExtra("name",nameFromDB);
                                    i.putExtra("email",emailFromDB);
                                    i.putExtra("password",password);
                                    i.putExtra("phone",phoneFromDB);
                                    sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putInt("first",1);
                                    editor.apply();
                                    startActivity(i);
                                    progress.setVisibility(View.GONE);
                                    finish();
                                }
                                else
                                {
                                    txtPassword.setError("enter correct password");
                                    txtPassword.requestFocus();
                                    progress.setVisibility(View.GONE);
                                    return;
                                }
                            }
                            else
                            {
                                txtEmail.setError("No such phone number exists, please signup");
                                txtEmail.requestFocus();
                                progress.setVisibility(View.GONE);
                                return;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Login_Activity.this,Signup_Activity.class);
        startActivity(i);
        finish();
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}
