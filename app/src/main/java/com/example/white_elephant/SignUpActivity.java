package com.example.white_elephant;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.white_elephant.databinding.ActivitySignupBinding;
import com.example.white_elephant.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

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
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        assert user != null;
                        User tempUser = new User(user.getUid());
                        db.collection("users").document(user.getUid()).set(tempUser);
                        Intent intent = new Intent(SignUpActivity.this,
                                CreateAccountInfoActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignUpActivity.this,
                                "Authentication failed.", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                        Intent intent = new Intent(SignUpActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignUpActivity.this,
                                "Authentication failed.", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mBinding.fieldEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mBinding.fieldEmail.setError("Required.");
            valid = false;
        } else {
            mBinding.fieldEmail.setError(null);
        }
        String password = mBinding.fieldPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mBinding.fieldPassword.setError("Required.");
            valid = false;
        } else {
            mBinding.fieldPassword.setError(null);
        }
        return valid;
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            mBinding.textStatus.setText(String.format("Email User: %1$s", user.getEmail()));
            mBinding.createAcct.setVisibility(View.GONE);
            mBinding.fieldEmail.setVisibility(View.GONE);
            mBinding.fieldPassword.setVisibility(View.GONE);
        } else {
            mBinding.textStatus.setText("Didn't work");
            mBinding.createAcct.setVisibility(View.VISIBLE);
            mBinding.fieldEmail.setVisibility(View.VISIBLE);
            mBinding.fieldPassword.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        String email = mBinding.fieldEmail.getText().toString();
        String password = mBinding.fieldPassword.getText().toString();
        if (i == R.id.createAcct) {
            createAccount(email, password);
        } else if (i == R.id.signIn) {
            signIn(email, password);
        }
    }
}