package com.iamscratches.prichatscratches.view.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.iamscratches.prichatscratches.R;
import com.iamscratches.prichatscratches.databinding.ActivityPhoneLoginBinding;

import java.util.concurrent.TimeUnit;

public class PhoneLoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static String TAG = "PhoneLoginActivity";
    private ActivityPhoneLoginBinding binding;
    private FirebaseAuth mAuth;
    private String mVerificationID;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;

    private ProgressDialog progressDialog;

    String[] country = {"India", "USA", "UK", "China", "Japan", "Other"};
    String[] c_code = {"91", "1", "44", "86", "81"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_phone_login);
        mAuth = FirebaseAuth.getInstance();

        Spinner spinner = findViewById(R.id.spinner_country);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> aa = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, country);
        aa.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(aa);



        progressDialog = new ProgressDialog(this);

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if(binding.btnNext.getText().toString().equals("Next")) {
                    progressDialog.setMessage("Sending OTP to ur no...");
                    progressDialog.show();
                    String phone = "+" + binding.edCodeCountry.getText().toString() +  binding.edPhone.getText().toString();
                    startPhoneNumberVerification(phone);
                }
                else if(binding.edCode.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Enter the OTP first", Toast.LENGTH_LONG).show();
                }
                else{
                    progressDialog.setMessage("Please wait while we verify...");
                    progressDialog.show();
                    verifyPhoneNumberWithCode(mVerificationID, binding.edCode.getText().toString());
                }
            }
        });
        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted: Complete");
                signInWithPhoneAuthCredential(phoneAuthCredential);
                progressDialog.dismiss();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.d(TAG, "onVerificationFailed:" + e.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Verification failed please enter the details correctly and try again",
                        Toast.LENGTH_LONG)
                        .show();
                progressDialog.dismiss();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationID = verificationId;
                mResendToken = token;

                binding.btnNext.setText("Confirm");
                binding.edCode.setVisibility(View.VISIBLE);
                binding.edPhone.setEnabled(false);
                binding.edCodeCountry.setEnabled(false);
                binding.spinnerCountry.setEnabled(false);
                binding.edCode.setEnabled(true);
                progressDialog.dismiss();
            }
        };
    }

    private void startPhoneNumberVerification(String phoneNumber){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            if(user != null){
                                startActivity(new Intent(PhoneLoginActivity.this, SetUserInfoActivity.class));
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "Something went wrong, pls try again", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),
                                    "Verification failed please enter the details correctly and try again",
                                    Toast.LENGTH_LONG)
                                    .show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Log.d(TAG, "onComplete" + task.getException().toString());
                            }
                        }
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(getApplicationContext(), country[position], Toast.LENGTH_SHORT).show();
        binding.edCodeCountry.setEnabled(false);
        if(position!=5){
            binding.edCodeCountry.setText(c_code[position]);
        }
        else{
            binding.edCodeCountry.setEnabled(true);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        binding.edCodeCountry.setEnabled(false);
    }
}