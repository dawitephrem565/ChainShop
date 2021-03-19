package com.filenber.chainshop;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.filenber.chainshop.personal_profile.Myproduct_Adapter;
import com.filenber.chainshop.personal_profile.Pinned_Adapter;
import com.filenber.chainshop.personal_profile.Pinned_Model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Pinned_Fragment extends Fragment {
  Dialog dialog;
  View view;
  RecyclerView recyclerView;
  ProgressBar progressBar;
  List<Pinned_Model> myproduct_list_item;FirebaseAuth firebaseAuth;
  private FirebaseAnalytics mFirebaseAnalytics;
  Pinned_Adapter myproduct_adapter;
 FloatingActionButton floatingActionButton;
  FirebaseFirestore db = FirebaseFirestore.getInstance();
  public Pinned_Fragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.pinned_fragment_recycle_view,container,false);
    floatingActionButton = view.findViewById(R.id.add_new_Product_btn);
    recyclerView =(RecyclerView)view.findViewById(R.id.pinned_fragment_recycle_view);
    Pinned_Adapter adapter = new Pinned_Adapter(getContext(),myproduct_list_item);
    recyclerView.setAdapter(adapter);
    floatingActionButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
       startActivity(new Intent(getContext(),Add_Followers.class));
      }
    });
    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(staggeredGridLayoutManager);
    // recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(adapter);
    return view;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    myproduct_list_item = new ArrayList<>();
    //new Dave().execute();
   // loadProducts();
  }
  private boolean loadProducts() {

    db.collection("Pinned_Product").whereEqualTo("Pinner_Id",SharedPrefManager.getInstance(getContext()).saved_uid())
      .addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
          if (e != null) {
            Log.d("firestoremessage", e.getMessage());
          }
          for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
            if (doc.getType() == DocumentChange.Type.ADDED) {
              String doc_id = doc.getDocument().getId();

              Pinned_Model read = doc.getDocument().toObject(Pinned_Model.class);
              myproduct_list_item.add(read);
              myproduct_adapter.notifyDataSetChanged();
            }
          }
        }
      });
    return true;

  }

}

