package com.iamscratches.prichatscratches.view.display;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.iamscratches.prichatscratches.R;
import com.iamscratches.prichatscratches.common.Common;
import com.iamscratches.prichatscratches.databinding.ActivityViewImageBinding;
import com.iamscratches.prichatscratches.view.profile.ProfileActivity;
import com.iamscratches.prichatscratches.view.settings.SettingsActivity;

import java.util.HashMap;

public class ViewImageActivity extends AppCompatActivity {

    private ActivityViewImageBinding binding;
    private BottomSheetDialog bottomSheetDialog;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private int IMAGE_GALLERY_REQUEST = 111;
    private Uri imageUri;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_image);

        progressDialog = new ProgressDialog(this);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        binding.imageView.setImageBitmap(Common.IMAGE_BITMAP);

        binding.editPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showButtonSheetPicPhoto();
            }
        });

        binding.ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewImageActivity.this, ProfileActivity.class));
                finish();
            }
        });
    }

    private void showButtonSheetPicPhoto(){
        Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();
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
//                bottomSheetDialog.dismiss();
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

    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select image"), IMAGE_GALLERY_REQUEST);
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
                binding.imageView.setImageBitmap(bitmap);
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