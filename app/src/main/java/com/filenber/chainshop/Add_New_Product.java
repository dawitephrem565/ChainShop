package com.filenber.chainshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Add_New_Product extends AppCompatActivity implements View.OnClickListener {
Button Post_btn , Take_Camera;
EditText Price , Desc , Product_Name;
ImageView Profile_Image;
  FirebaseStorage storage;
  String Upload_img_path;
  Uri imageUri;

  private static final int REQUEST_CODE = 101;
  private static int RESULT_LOAD_IMAGE = 1;
  FirebaseFirestore db = FirebaseFirestore.getInstance();
  CoordinatorLayout coordinatorLayout;
  StorageReference storageReference;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add__new__product);
    Price = findViewById(R.id.Add_Price);
    Desc=findViewById(R.id.Add_Description);
    Profile_Image = findViewById(R.id.Add_New_Image_Product);
    Post_btn=findViewById(R.id.post_btn);
    Product_Name=findViewById(R.id.Product_Name);
    Take_Camera=findViewById(R.id.take_camera_btn);
    Upload_img_path="";
    storage = FirebaseStorage.getInstance();
    storageReference = storage.getReference();
    Post_btn.setOnClickListener(this);
    Take_Camera.setOnClickListener(this);
  }
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK && requestCode == 100){
      imageUri = data.getData();
      try {
        Bitmap bitmap =  MediaStore.Images.Media.getBitmap(getContentResolver(),
          imageUri);
        Profile_Image.setImageBitmap(bitmap);
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }
  @Override
  public void onClick(View v) {
    switch (v.getId())
    {
      case R.id.take_camera_btn:
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 100);
        break;
      case R.id.post_btn:
        if(Product_Name.getText().toString().isEmpty() || Desc.getText().toString().isEmpty()||Price.getText().toString().isEmpty())
        {

          Snackbar.make(v, "Please Fill The Form ", Snackbar.LENGTH_LONG).show();
        }
        else
        {
          uploadImage();
        }
        break;
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
          "product_images/"
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
                save_In_database();
                }
              });
              // Image uploaded successfully
              // Dismiss dialog
              progressDialog.dismiss();
              startActivity(new Intent(Add_New_Product.this,MainActivity.class));

            }
          })

        .addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e)
          {

            // Error, Image not uploaded
            progressDialog.dismiss();
            Toast
              .makeText(Add_New_Product.this,
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
  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)

  {
    switch (requestCode) {
      case REQUEST_CODE:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          //fetchlocation_long_and_lat();
        }
        break;
    }


  }
  public void save_In_database() {


    Map<String, Object> city = new HashMap<>();
    city.put("Product_name", Product_Name.getText().toString());
    city.put("Product_descr",Desc.getText().toString());
    city.put("Product_price",Price.getText().toString());
    city.put("Product_Image",Upload_img_path.toString());

    city.put("Business_ID",SharedPrefManager.getInstance(getBaseContext()).getBussiness_ID());
    city.put("Owner_id",SharedPrefManager.getInstance(getBaseContext()).saved_uid().toString());



    db.collection("Product")
      .add(city)

      .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
        @Override
        public void onSuccess(DocumentReference documentReference) {
        //save_Owner_id();
        Toast.makeText(getBaseContext(),"Product Posted Successfully ",Toast.LENGTH_LONG).show();
        startActivity(new Intent(Add_New_Product.this,MainActivity.class));
        }
      })
      .addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
          Toast.makeText(getBaseContext(), "Error adding document" + e, Toast.LENGTH_LONG).show();
        }
      });
  }

}
