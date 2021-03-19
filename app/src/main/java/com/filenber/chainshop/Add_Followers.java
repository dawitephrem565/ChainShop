package com.filenber.chainshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.filenber.chainshop.personal_profile.add_followers_model;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import Adapter.Followers_Adapter;

public class Add_Followers extends AppCompatActivity {
  private EditText mSearchField;
  private ImageButton mSearchBtn;
  List<add_followers_model> list;
  private RecyclerView search_result_recycle_view;
  FirebaseFirestore db = FirebaseFirestore.getInstance();
  private DatabaseReference mUserDatabase;
  Followers_Adapter followers_adapter;

Button find_btn;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add__followers);



    //mSearchBtn = (ImageButton) findViewById(R.id.search_btn);
    list = new ArrayList<>();

search_followers();

    search_result_recycle_view=(RecyclerView)findViewById(R.id.search_recycle_view);
    search_result_recycle_view.setHasFixedSize(true);
    search_result_recycle_view.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));




  }

  private boolean search_followers() {

    db.collection("Users")
      .addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
          if (e != null) {
            Toast.makeText(Add_Followers.this, e.toString(), Toast.LENGTH_SHORT).show();
          }
          for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
            if (doc.getType() == DocumentChange.Type.ADDED) {
              String doc_id = doc.getDocument().getId();
              add_followers_model   read = doc.getDocument().toObject(add_followers_model.class).withId(doc_id);
              list.add(read);
              //Toast.makeText(Add_Followers.this, read.getUsername(), Toast.LENGTH_SHORT).show();
              followers_adapter = new Followers_Adapter(Add_Followers.this, list);
              search_result_recycle_view.setAdapter(followers_adapter);
              followers_adapter.notifyDataSetChanged();

            }
          }
        }
      });
    return true;

  }
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.search_menu, menu);
    MenuItem searchItem = menu.findItem(R.id.action_search);
    SearchView searchView = (SearchView) searchItem.getActionView();
    searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        return false;
      }
      @Override
      public boolean onQueryTextChange(String newText) {
        followers_adapter.getFilter().filter(newText);
        return false;
      }
    });
    return true;
  }

}
