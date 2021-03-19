package com.filenber.chainshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {
  TextView Memo_name, Memo_Year;
  ImageView Memo_Img;
  CardView Card_box;
  Button Back_btn;
  TextView newInvitation_Badge;
  int Count_Invitation =0;

  int count = 0;
  private StorageReference storageReference;
  private Uri filePath;
  ProgressDialog progressDialog;
  FirebaseFirestore db = FirebaseFirestore.getInstance();
  // private StorageReference storageReference;
  private RecyclerView list_container;
  Context ctx;
  FloatingActionButton play;
  FirebaseAuth firebaseAuth;
  private FirebaseAnalytics mFirebaseAnalytics;
  public Main_Page_Model read;
  Main_Page_adapter reading_adapter;
  DocumentReference docref;
  FloatingActionButton add_new_memo_btn;
  List<Main_Page_Model> list;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Read_Pending_Business_Partners_Invitation();
    data_holder();
  }

  public void data_holder() {
    mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    list = new ArrayList<>();
    reading_adapter = new Main_Page_adapter(getBaseContext(), list);
    list_container = (RecyclerView) findViewById(R.id.product_recycle_view);
    storageReference = FirebaseStorage.getInstance().getReference();
    list_container.setHasFixedSize(true);
    list_container.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
    list_container.setAdapter(reading_adapter);


    ReadSingleContact();
  }

  private boolean ReadSingleContact() {

    db.collection("Product")
      .addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
          if (e != null) {
            Log.d("firestoremessage", e.getMessage());
          }
          for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
            if (doc.getType() == DocumentChange.Type.ADDED) {
              String doc_id = doc.getDocument().getId();
              Toast.makeText(getBaseContext(), String.valueOf(queryDocumentSnapshots.getDocuments().size()), Toast.LENGTH_SHORT).show();

              Main_Page_Model read = doc.getDocument().toObject(Main_Page_Model.class);
              list.add(read);
              reading_adapter.notifyDataSetChanged();

            }


          }
        }
      });
    return true;

  }

  private boolean Read_Pending_Business_Partners_Invitation() {

    db.collection("Business Marketers")
      .whereEqualTo("Marketer_ID",SharedPrefManager.getInstance(getBaseContext()).saved_uid())
      .whereEqualTo("Status","Pending")


      .addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
          if (e != null) {
            Log.d("firestoremessage", e.getMessage());
          }
          for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
            if (doc.getType() == DocumentChange.Type.ADDED) {
              String doc_id = doc.getDocument().getId();
           Count_Invitation = queryDocumentSnapshots.getDocuments().size();


            }


          }
        }
      });
    return true;

  }

  private void setupBadge() {

    if (newInvitation_Badge != null) {
      if (Count_Invitation == 0) {
        if (newInvitation_Badge.getVisibility() != View.GONE) {
          newInvitation_Badge.setVisibility(View.GONE);
        }
      } else {
        newInvitation_Badge.setText(String.valueOf(Math.min(Count_Invitation, 99)));
        if (newInvitation_Badge.getVisibility() != View.VISIBLE) {
          newInvitation_Badge.setVisibility(View.VISIBLE);
        }
      }
    }
  }
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.home_menu, menu);
    final MenuItem menuItem = menu.findItem(R.id.action_cart);

    View actionView = menuItem.getActionView();
    newInvitation_Badge = (TextView) actionView.findViewById(R.id.cart_badge);
Read_Pending_Business_Partners_Invitation();
setupBadge();
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.setting) {
      startActivity(new Intent(MainActivity.this,Personal_Profile_Page.class));

      return true;
    }
    if (id == R.id.profile_icon) {
      startActivity(new Intent(MainActivity.this,MyBusiness_Activity.class));
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
