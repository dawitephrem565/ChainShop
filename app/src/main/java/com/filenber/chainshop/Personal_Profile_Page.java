package com.filenber.chainshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Personal_Profile_Page extends AppCompatActivity implements View.OnClickListener {
TextInputEditText Username , Desc;
ImageView profile_pic;
Button Update_btn , camera_btn;
  FirebaseStorage storage;
  String Upload_img_path;
  Uri imageUri;
  private static final int REQUEST_CODE = 101;
  private static int RESULT_LOAD_IMAGE = 1;
  FirebaseFirestore db = FirebaseFirestore.getInstance();
  StorageReference storageReference;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.personal_profile_content);
    Upload_img_path="";
    storage = FirebaseStorage.getInstance();
    storageReference = storage.getReference();
 ini();
 get_current_profile();
  }
  public  void ini()
  {
    Username = (TextInputEditText)findViewById(R.id.Profile_UserName);
    Desc = (TextInputEditText)findViewById(R.id.Profile_Descr);
    profile_pic=(ImageView)findViewById(R.id.profile_image);
    Update_btn=findViewById(R.id.Update_btn);
    camera_btn = findViewById(R.id.take_camera_btn2);
    Update_btn.setOnClickListener(this);
    camera_btn.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId())
    {
      case R.id.Update_btn:
        uploadImage();
       // Toast.makeText(this, SharedPrefManager.getInstance(getBaseContext()).saved_uid(), Toast.LENGTH_SHORT).show();
        break;
      case R.id.take_camera_btn2:
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 100);
        break;

    }
  }
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK && requestCode == 100){
      imageUri = data.getData();
      try {
        Bitmap bitmap =  MediaStore.Images.Media.getBitmap(getContentResolver(),
          imageUri);
        profile_pic.setImageBitmap(bitmap);
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }
  private void uploadImage()
  {
    if (imageUri != null) {

      // Code for showing progressDialog while uploading
      final ProgressDialog progressDialog
        = new ProgressDialog(this);
      progressDialog.setTitle("Uploading...");
      progressDialog.show();

      // Defining the child of storageReference
      storageReference
        = storageReference
        .child(
          "Users_images/"
            + UUID.randomUUID().toString());

      // adding listeners on upload
      // or failure of image
      storageReference.putFile(imageUri)
        .addOnSuccessListener(
          new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(
              UploadTask.TaskSnapshot taskSnapshot)
            {
              storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                  // getting image uri and converting into string
                  Uri downloadUrl = uri;
                  Upload_img_path = downloadUrl.toString();
                  Update_Profile();

                }
              });
              // Image uploaded successfully
              // Dismiss dialog
              progressDialog.dismiss();

            }
          })

        .addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e)
          {

            // Error, Image not uploaded
            progressDialog.dismiss();
            Toast
              .makeText(Personal_Profile_Page.this,
                "Failed " + e.getMessage(),
                Toast.LENGTH_SHORT)
              .show();
          }
        })
        .addOnProgressListener(
          new OnProgressListener<UploadTask.TaskSnapshot>() {

            // Progress Listener for loading
            // percentage on the dialog box
            @Override
            public void onProgress(
              UploadTask.TaskSnapshot taskSnapshot)
            {
              double progress
                = (100.0
                * taskSnapshot.getBytesTransferred()
                / taskSnapshot.getTotalByteCount());
              progressDialog.setMessage(
                "Uploaded "
                  + (int)progress + "%");
            }
          });
    }
  }
  public void Update_Profile()
  {

    Map<String, Object> city = new HashMap<>();
    city.put("Username", Username.getText().toString());
    city.put("Qoute_Descr", Desc.getText().toString());
    city.put("Profile_Img", Upload_img_path.toString());
    db.collection("Users").document(SharedPrefManager.getInstance(getBaseContext()).saved_uid())
      .set(city)
      .addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
          Toast.makeText(Personal_Profile_Page.this, "Data Updated Successfully ", Toast.LENGTH_SHORT).show();
    startActivity(new Intent(Personal_Profile_Page.this,MainActivity.class));

        }
      })
      .addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
          Toast.makeText(Personal_Profile_Page.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
      });
  }

  public void get_current_profile(){
    DocumentReference docRef = db.collection("Users").document(SharedPrefManager.getInstance(getBaseContext()).saved_uid());
     docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
      @Override
      public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {
          DocumentSnapshot document = task.getResult();
          if (document.exists()) {
           Username.setText(document.getString("Username"));
            Desc.setText(document.getString("Qoute_Descr"));
            Glide.with(getBaseContext()).load(document.getString("Profile_Img")).into(profile_pic);



          }
        } else {
          //Log.d(TAG, "get failed with ", task.getException());
        }
      }
    });
  }
}
