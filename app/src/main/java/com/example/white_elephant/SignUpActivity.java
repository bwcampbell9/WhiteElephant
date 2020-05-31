package com.example.white_elephant;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.white_elephant.databinding.ActivitySignupBinding;
import com.example.white_elephant.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

        //mBinding.createAcct.setOnClickListener(this);

        mBinding.createAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mBinding.fieldEmail.getText().toString();
                String password = mBinding.fieldPassword.getText().toString();
                createAccount(email, password);
            }
        });

        mBinding.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mBinding.fieldEmail.getText().toString();
                String password = mBinding.fieldPassword.getText().toString();
                signIn(email, password);
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);

//                Bundle bundle = new Bundle();
//                bundle.putString("email", email);
//                intent.putExtras(bundle);

                startActivity(intent);
            }
        });


        //mBinding.signIn.setOnClickListener(this);

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
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            assert user != null;
                            User tempUser = new User(user.getUid());
                            db.collection("users").document(user.getUid()).set(tempUser);
                            Intent intent = new Intent(SignUpActivity.this,
                                    CreateAccountInfoActivity.class);

                            Bundle bundle = new Bundle();
                            bundle.putString("uid", user.getUid());
                            bundle.putString("email", email);
                            intent.putExtras(bundle);

                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(SignUpActivity.this,
                                    "Authentication failed.", Toast.LENGTH_SHORT).show();
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
                            Intent intent = new Intent(SignUpActivity.this,
                                    MainActivity.class);

                            Bundle bundle = new Bundle();
                            bundle.putString("uid", user.getUid());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(SignUpActivity.this,
                                    "Authentication failed.", Toast.LENGTH_SHORT).show();
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
        String email = mBinding.fieldEmail.getText().toString();
        String password = mBinding.fieldPassword.getText().toString();
        if (i == R.id.createAcct) {
            createAccount(email, password);
        }
        else if (i == R.id.signIn) {
            signIn(email, password);
        }
    }
}