package edu.gcit.Insidebhutan;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private EditText emailEt,nameEt, passwordEt1, getPasswordEt2;
    private Button signUpButton;
    private TextView SignInTv;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        firebaseAuth = FirebaseAuth.getInstance();
        emailEt = findViewById(R.id.Email);
        nameEt = findViewById(R.id.name);
        passwordEt1 = findViewById(R.id.password1);
        getPasswordEt2 = findViewById(R.id.password2);
        signUpButton= findViewById(R.id.register);
        progressDialog = new ProgressDialog(this);
        SignInTv =findViewById(R.id.signInTv);
        reference= FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Register();
            }
        });
        SignInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUpActivity.this,MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void Register(){
        String name= nameEt.getText().toString();
        String email = emailEt.getText().toString();
        String password1 = passwordEt1.getText().toString();
        String password2 = getPasswordEt2.getText().toString();

        user user = new user (name, email, password2);
        reference.child(name).setValue(user);
        if(TextUtils.isEmpty(email)){
            emailEt.setError("Enter your email");
            return;
        }
        else if(TextUtils.isEmpty(password1)){
            passwordEt1.setError("Enter your password");
            return;
        }
        else if(TextUtils.isEmpty(password2)){
            getPasswordEt2.setError("confirm password");
            return;
        }
//        else if(!password1.equals(password2)){
//            getPasswordEt2.setError("Different password");
//            return;
//
//        }
        else if(password1.length()<4){
            passwordEt1.setError("Length should be > 4");
            return;

        }
        else if(!isVallidEmail(email)){
            emailEt.setError("invalid email");
            return;

        }
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser fuser = firebaseAuth.getCurrentUser();
                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"user Registered. Verification Email Has been sent." +
                                    "\nVerify Your Email To Login,", Toast.LENGTH_LONG).show();
                            emailEt.setText("");
                            passwordEt1.setText("");
                            getPasswordEt2.setText("");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Log.d("TAG","onFailure: Email not sent " + e.getMessage());
                        }
                    });
                }

//                    Toast.makeText(SignUpActivity.this,"Successfully registered",Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(SignUpActivity.this,DashBoardActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//                else{
//                    Toast.makeText(SignUpActivity.this,"Sign up fail!",Toast.LENGTH_LONG).show();
//                }
//                progressDialog.dismiss();
            }
        });
    }
    private Boolean isVallidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}
