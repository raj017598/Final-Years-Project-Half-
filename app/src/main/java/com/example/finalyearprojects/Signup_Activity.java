package com.example.finalyearprojects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Signup_Activity extends Activity {

    private TextInputLayout edtTxtName, edtTxtEmail, edtTxtPassword, edtTxtPhone;
    private TextView txtLicence,txtLogin;
    private Button btnRegister;
    private ProgressBar progressBar;

    String name, email, password, phone;

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_);

        edtTxtName = findViewById(R.id.edtTxtName);
        edtTxtEmail = findViewById(R.id.edtTxtEmail);
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");

        txtLicence =  (TextView)findViewById(R.id.txtLicense);
        txtLogin = (TextView)findViewById(R.id.txtLogin);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        edtTxtPassword = findViewById(R.id.edtTxtPassword);
        edtTxtPhone = findViewById(R.id.edtTxtPhone);
        edtTxtName.requestFocus();
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Intent i = new Intent(Signup_Activity.this,Login_Activity.class);
                startActivity(i);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edtTxtName.getEditText().getText().toString();
                email = edtTxtEmail.getEditText().getText().toString();
                password = edtTxtPassword.getEditText().getText().toString();
                phone = edtTxtPhone.getEditText().getText().toString();
                if(name.isEmpty())
                {
                    edtTxtName.setError("Enter name");
                    edtTxtName.requestFocus();
                    return;
                }
                if(email.isEmpty())
                {
                    edtTxtEmail.setError("Field cannot be empty");
                    edtTxtEmail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    edtTxtEmail.setError("Enter valid email");
                    edtTxtEmail.requestFocus();
                    return;
                }
                if(password.isEmpty())
                {
                    edtTxtPassword.setError("Field cannot be empty.");
                    edtTxtPassword.requestFocus();
                    return;
                }
                if(password.length()<8)
                {
                    edtTxtPassword.setError("Password is too short.");
                    edtTxtPassword.requestFocus();
                    return;
                }
                if(phone.isEmpty())
                {
                    edtTxtPhone.setError("Plese enter your phone number.");
                    edtTxtPhone.requestFocus();
                    return;
                }
                if(!Patterns.PHONE.matcher(phone).matches())
                {
                    edtTxtPhone.setError("enter valid phone number.");
                    edtTxtPhone.requestFocus();
                    return;
                }
                if(phone.length()!=10)
                {
                    edtTxtPhone.setError("Enter valid phone number.");
                    edtTxtPhone.requestFocus();
                    return;
                }
                try
                {
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                    Query checkuser = reference.orderByChild("phone").equalTo(phone);
                    checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists())
                            {
                                progressBar.setVisibility(View.VISIBLE);
                                Toast.makeText(Signup_Activity.this,"phone is already registered, please login",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                progressBar.setVisibility(View.VISIBLE);
                                txtLicence.setVisibility(View.GONE);
                                Intent intent = new Intent(getApplicationContext(),Verify_Activity.class);
                                intent.putExtra("phone",phone);
                                intent.putExtra("name",name);
                                intent.putExtra("email",email);
                                intent.putExtra("password",password);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    /*
                    progressBar.setVisibility(View.VISIBLE);
                    txtLicence.setVisibility(View.GONE);
                    Intent intent = new Intent(getApplicationContext(),Verify_Activity.class);
                    intent.putExtra("phone",phone);
                    startActivity(intent);
                    User user = new User(name, email, password, phone, college, course);
                    reference.child(phone).setValue(user);
                   Toast.makeText(Signup_Activity.this,"Signup Successfull",Toast.LENGTH_SHORT).show();
                   edtTxtName.setText(null);
                   edtTxtCourse.setSelection(0);
                   edtTxtCollege.setText(null);
                   edtTxtEmail.setText(null);
                   edtTxtPassword.setText(null);
                   edtTxtPhone.setText(null);*/
                    /*Intent i = new Intent(Signup_Activity.this,Login_Activity.class);
                    startActivity(i);
                    finish();*/
                }
                catch(Exception e)
                {
                    Toast.makeText(Signup_Activity.this,"Connection Failed, Please try Again",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
        edtTxtName.getEditText().setText(null);
        edtTxtPhone.getEditText().setText(null);
        edtTxtPassword.getEditText().setText(null);
        edtTxtEmail.getEditText().setText(null);
    }

}
