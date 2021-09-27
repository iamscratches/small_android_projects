package com.iamscratches.prichatscratches.view.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.iamscratches.prichatscratches.R;
import com.iamscratches.prichatscratches.common.Common;
import com.iamscratches.prichatscratches.databinding.ActivityProfileBinding;
import com.iamscratches.prichatscratches.view.auth.PhoneLoginActivity;
import com.iamscratches.prichatscratches.view.display.ViewImageActivity;
import com.iamscratches.prichatscratches.view.settings.SettingsActivity;
import com.iamscratches.prichatscratches.view.startup.WelcomeScreenActivity;

import java.util.HashMap;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;

    private BottomSheetDialog bottomSheetDialog;
    private int IMAGE_GALLERY_REQUEST = 111;
    private Uri imageUri;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);

        if(firebaseUser!=null){
            getInfo();
        }

        binding.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, SettingsActivity.class));
                finish();
            }
        });
        initActionClick();
    }

    private void initActionClick(){

        binding.fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showButtonSheetPicPhoto();
            }
        });

        binding.ivUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showButtonSheetEditName();
            }
        });

        binding.imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.imageProfile.invalidate();
                Drawable dr = binding.imageProfile.getDrawable();
                Common.IMAGE_BITMAP = ((GlideBitmapDrawable)dr.getCurrent()).getBitmap();
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation
                        ((Activity)ProfileActivity.this, binding.imageProfile,"image");

                startActivity(new Intent(ProfileActivity.this, ViewImageActivity.class),
                        activityOptionsCompat.toBundle());
                finish();
            }
        });
    }

    private void showButtonSheetPicPhoto(){

        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_pick);
        bottomSheetDialog.show();

        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                bottomSheetDialog = null;
            }
        });

        LinearLayout lytCamera = bottomSheetDialog.findViewById(R.id.lyt_camera);
        lytCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "camera", Toast.LENGTH_SHORT).show();
//                dispatchTakePictureIntent();
                bottomSheetDialog.dismiss();
            }
        });

        LinearLayout lytGallery = bottomSheetDialog.findViewById(R.id.lyt_gallery);
        lytGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
//                Toast.makeText(getApplicationContext(), "gallery", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });

        LinearLayout lytAvatar = bottomSheetDialog.findViewById(R.id.lyt_avatar);
        lytAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "avatar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showButtonSheetEditName(){
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_edit_name);
        bottomSheetDialog.show();

        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                bottomSheetDialog = null;
            }
        });

        EditText username = bottomSheetDialog.findViewById(R.id.ed_username);
        Button btnSave = bottomSheetDialog.findViewById(R.id.btn_save);
        Button btnCancel = bottomSheetDialog.findViewById(R.id.btn_cancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                bottomSheetDialog = null;
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUsername(username.getText().toString());
                bottomSheetDialog.dismiss();
                bottomSheetDialog = null;
            }
        });
    }

    private void getInfo(){
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        firebaseFirestore.collection("Users").document(firebaseUser.getUid()).get().addOnSuccessListener(
                new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String username = documentSnapshot.getString("username");
                        String userPhone = documentSnapshot.getString("userPhone");
                        String bio = documentSnapshot.getString("bio");
                        String imageProfile = documentSnapshot.getString("imageProfile");
                        binding.tvName.setText(username);
                        binding.tvPhone.setText(userPhone);
                        binding.tvBio.setText(bio);
                        if(imageProfile.length()>0) {
                            Glide.with(ProfileActivity.this).load(imageProfile).into(binding.imageProfile);
                        }
                        progressDialog.dismiss();
                    }
                }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                binding.tvName.setText("");
                binding.tvPhone.setText("");
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Loading failed pls check ur network connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select image"), IMAGE_GALLERY_REQUEST);
    }

    private void saveUsername(String username){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("username", username);
        progressDialog.setMessage("updating username...");
        progressDialog.show();
        firebaseFirestore.collection("Users").document(firebaseUser.getUid()).update(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "username updated successfully", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "username update failure", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_GALLERY_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null){
            imageUri = data.getData();
            boolean success = true;
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                binding.imageProfile.setImageBitmap(bitmap);
            }catch (Exception e){
                Log.e("profile:", "failed: " + e.getMessage());
                e.printStackTrace();
                success = false;
            }
            if(success){
                uploadToFirebase();
            }
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver =getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadToFirebase(){
        progressDialog.setMessage("uploading profile pic...");
        if(imageUri!=null){
            progressDialog.show();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("ImageProfile/" +
                    System.currentTimeMillis() + "." + getFileExtension(imageUri));

            storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while(!urlTask.isSuccessful());
                    Uri downloadUrl = urlTask.getResult();

                    final String sDownloadUrl = String.valueOf(downloadUrl);


                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("imageProfile", sDownloadUrl);

                    firebaseFirestore.collection("Users").document(firebaseUser.getUid()).update(hashMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "uploaded Successfully", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Image setting upload unsuccessful", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Image upload unsuccessful", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}