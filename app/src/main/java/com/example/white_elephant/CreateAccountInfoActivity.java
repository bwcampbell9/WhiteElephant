package com.example.white_elephant;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.white_elephant.databinding.ActivityCreateAccountInfoBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class CreateAccountInfoActivity extends AppCompatActivity implements View.OnClickListener {


    private ActivityCreateAccountInfoBinding mBinding;
    private FirebaseAuth mAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityCreateAccountInfoBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.buttonCreateAccount.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    private void addAccountInfo(String name) {
        FirebaseUser newUser = mAuth.getCurrentUser();
        if (!validateForm() || (newUser == null)) {
            return;
        }
        Log.e("Info", "begins add account info\n");
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name).build();
        newUser.updateProfile(profileUpdates).addOnCompleteListener(this,
                task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                        Intent intent = new Intent(CreateAccountInfoActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        updateUI(null);
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;
        String name = mBinding.fieldName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            mBinding.fieldName.setError("Required.");
            valid = false;
        }
        else {
            mBinding.fieldName.setError(null);
        }
        return valid;
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            mBinding.textStatus.setText(String.format("User Name: %1$s", user.getDisplayName()));
            mBinding.buttonCreateAccount.setVisibility(View.GONE);
            mBinding.fieldName.setVisibility(View.GONE);
        }
        else {
            mBinding.textStatus.setText("Didn't work");
            mBinding.buttonCreateAccount.setVisibility(View.VISIBLE);
            mBinding.fieldName.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonCreateAccount) {
            addAccountInfo(mBinding.fieldName.getText().toString());
            // need to add other fields
        }
    }
}