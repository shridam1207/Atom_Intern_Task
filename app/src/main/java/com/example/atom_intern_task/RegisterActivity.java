package com.example.atom_intern_task;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.navigation.ui.AppBarConfiguration;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private EditText nameuser, emailid, passworduser;
    private Button btnRegister;
    private TextView logintv;
  //  private ProgressDialog progressDialog = new ProgressDialog(this);
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeruser);
        firebaseAuth = FirebaseAuth.getInstance();
        nameuser = findViewById(R.id.name);
        emailid = findViewById(R.id.email);
        passworduser = findViewById(R.id.password);
        logintv = findViewById(R.id.login);
        btnRegister = findViewById(R.id.register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });


        logintv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Register() {
        String name = nameuser.getText().toString();
        String email = emailid.getText().toString();
        String password = passworduser.getText().toString();
        if (TextUtils.isEmpty(name)) {
            nameuser.setError("Enter yor name");
            return;
        } else if (TextUtils.isEmpty(email)) {
            emailid.setError("Enter yor email");
            return;
        } else if (!isValidEmail(email)) {
            emailid.setError("Invalid credentials");
            return;
        }
//        else if (!isValidPassword(password)) {
//            passworduser.setError("Password should contain Alphanumeric digits in both cases along with special characters");
//            return;
//        }
        else if (TextUtils.isEmpty(password))
            {
            passworduser.setError("Enter yor password");
            return;
        } else if (password.length() < 8) {
            passworduser.setError("Length of password should be greater than 8.");
            return;
        } else if (name.isEmpty() && email.isEmpty() && password.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
        } else if (!(name.isEmpty() && email.isEmpty() && password.isEmpty())) {
//            progressDialog.setMessage("Please wait...");
//            progressDialog.show();
//            progressDialog.setCanceledOnTouchOutside(false);
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Could not register successfully", Toast.LENGTH_LONG).show();
                    }
                 //   progressDialog.dismiss();
                }
            });
        }
    }
    private static boolean isValidEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    private static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}

