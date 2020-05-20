package com.example.white_elephant;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.white_elephant.databinding.ActivitySignupBinding;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySignupBinding mBinding;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.createAcct.setOnClickListener(this);
        mBinding.signIn.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    private void createAccount(String email, String password) {
        if (!validateForm()) {
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }
                        else {
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }
                        else {
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mBinding.fieldEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mBinding.fieldEmail.setError("Required.");
            valid = false;
        }
        else {
            mBinding.fieldEmail.setError(null);
        }
        String password = mBinding.fieldPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mBinding.fieldPassword.setError("Required.");
            valid = false;
        }
        else {
            mBinding.fieldPassword.setError(null);
        }
        return valid;
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            mBinding.textStatus.setText(String.format("Email User: %1$s",
                    user.getEmail(), user.isEmailVerified()));
            mBinding.createAcct.setVisibility(View.GONE);
            mBinding.fieldEmail.setVisibility(View.GONE);
            mBinding.fieldPassword.setVisibility(View.GONE);
        }
        else {
            mBinding.textStatus.setText("Didn't work");
            mBinding.createAcct.setVisibility(View.VISIBLE);
            mBinding.fieldEmail.setVisibility(View.VISIBLE);
            mBinding.fieldPassword.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.createAcct) {
            createAccount(mBinding.fieldEmail.getText().toString(), mBinding.fieldPassword.getText().toString());
        }
        else if (i == R.id.signIn) {
            signIn(mBinding.fieldEmail.getText().toString(), mBinding.fieldPassword.getText().toString());
        }
    }
}