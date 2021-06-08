package edu.gcit.Insidebhutan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.regex.Pattern;

public class MainActivity2 extends AppCompatActivity {
    private EditText emailEt, passwordEt;
    private Button signInButton;
    private TextView SignUpTv;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        firebaseAuth = FirebaseAuth.getInstance();
        emailEt = findViewById(R.id.Email);
        passwordEt = findViewById(R.id.password);
        signInButton= findViewById(R.id.login);
        progressDialog = new ProgressDialog(this);
        SignUpTv =findViewById(R.id.SignUpTv);
        signInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!validateEmail() | !validatePassword()){
                    return;
                }

                Login();
            }
        });
        SignUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void Login(){
        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();
        if(TextUtils.isEmpty(email)){
            emailEt.setError("Enter your email");
            return;
        }
        else if(TextUtils.isEmpty(password)){
            passwordEt.setError("Enter your password");
            return;
        }


        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity2.this,"Login Successful",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity2.this,DashBoardActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(MainActivity2.this,"Sign In fail!",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }
    private Boolean validateEmail(){
        String val = emailEt.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern EMAIL_PATTERN = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
        if (val.isEmpty()){
            emailEt.setError("Field cannot be empty");
            return false;
        }else if (!EMAIL_PATTERN.matcher(val).matches()){
            emailEt.setError("Invalid email address");
            return false;
        }
        else{
            emailEt.setError(null);
            return true;
        }

    }
    private Boolean validatePassword(){
        String val = passwordEt.getText().toString();
        if (val.isEmpty()){
            passwordEt.setError("Field cannot be empty");
            return false;
        } else {
            passwordEt.setError(null);
            return true;
        }
    }
}