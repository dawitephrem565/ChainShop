package com.filenber.chainshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.filenber.chainshop.personal_profile.Myproduct_Adapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import Adapter.MyBusiness_Adapter;
import Models.MyBussiness_Model;
import document_id_package.Bussiness_Doc_Id;

public class MyBusiness_Activity extends AppCompatActivity {
 RecyclerView my_business_recycle_view;
  TextView Memo_name, Memo_Year;
  ImageView Memo_Img;
  private StorageReference storageReference;
  private Uri filePath;
  ProgressDialog progressDialog;
  FirebaseFirestore db = FirebaseFirestore.getInstance();
  // private StorageReference storageReference;
  private RecyclerView list_container;
  Context ctx;
  FirebaseAuth firebaseAuth;
  private FirebaseAnalytics mFirebaseAnalytics;
  public MyBussiness_Model read;
  MyBusiness_Adapter mybusiness_adapter;
  DocumentReference docref;
  FloatingActionButton add_new_memo_btn;
  List<MyBussiness_Model> list;

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_business_);
data_holder();

    }
  public void data_holder() {
    mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    list = new ArrayList<>();
    mybusiness_adapter = new MyBusiness_Adapter(getBaseContext(), list);
    list_container = (RecyclerView) findViewById(R.id.my_business_recycle_view);
    storageReference = FirebaseStorage.getInstance().getReference();
    list_container.setHasFixedSize(true);
    list_container.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
    list_container.setAdapter(mybusiness_adapter);
    MyBussiness();
  }
  private boolean MyBussiness() {

    db.collection("Business")
      .addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
          if (e != null) {
            Log.d("firestoremessage", e.getMessage());
            Toast.makeText(MyBusiness_Activity.this, e.toString(), Toast.LENGTH_SHORT).show();
          }
          for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
            if (doc.getType() == DocumentChange.Type.ADDED) {
              String doc_id = doc.getDocument().getId();

              MyBussiness_Model read = doc.getDocument().toObject(MyBussiness_Model.class).withId(doc_id);
              list.add(read);
              mybusiness_adapter.notifyDataSetChanged();
            }
          }
        }
      });
    return true;

  }
}
