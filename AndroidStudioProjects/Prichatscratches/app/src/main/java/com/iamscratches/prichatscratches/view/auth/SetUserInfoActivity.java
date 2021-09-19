package com.iamscratches.prichatscratches.view.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.iamscratches.prichatscratches.R;
import com.iamscratches.prichatscratches.databinding.ActivitySetUserInfoBinding;
import com.iamscratches.prichatscratches.model.user.Users;
import com.iamscratches.prichatscratches.view.MainActivity;

public class SetUserInfoActivity extends AppCompatActivity {

    private ActivitySetUserInfoBinding binding;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_user_info);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        if(firebaseUser!=null){
            progressDialog.setMessage("Please wait a sec...");
            progressDialog.show();
            Task<DocumentSnapshot> doc = firebaseFirestore.collection("Users").document(firebaseUser.getUid()).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Users users = documentSnapshot.toObject(Users.class);
                            binding.edName.setText(users.getUsername());
                            binding.edDesc.setText(users.getBio());
                            progressDialog.dismiss();
                        }
                    });
        }


        initButtonClick();
    }

    private void initButtonClick() {
        binding.btnNext.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //pickImage()
                if(TextUtils.isEmpty(binding.edName.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Please give urself an username", Toast.LENGTH_SHORT).show();
                }
                else{
                    doUpdate();
                }
            }
        });

        binding.imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pickImage()
                Toast.makeText(getApplicationContext(), "This function is not ready to use", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void doUpdate(){
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        if(firebaseUser!=null){
            String userID = firebaseUser.getUid();
            Users users = new Users(
                    userID,
                    binding.edName.getText().toString(),
                    firebaseUser.getPhoneNumber(),
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    binding.edDesc.getText().toString());
            firebaseFirestore.collection("Users").document(firebaseUser.getUid()).set(users)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Update Successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Update Unsuccessful", Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(), "you need to log in first", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }

    }
}