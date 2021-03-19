package com.filenber.chainshop.ui.main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.filenber.chainshop.Add_New_Product;
import com.filenber.chainshop.R;
import com.filenber.chainshop.SharedPrefManager;

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

public class Pinned_Fragment extends Fragment { Dialog dialog;
  View view;
  RecyclerView recyclerView;
  ProgressBar progressBar;
  List<Pinned_Model> pinned_list_item;
  FirebaseAuth firebaseAuth;
  private FirebaseAnalytics mFirebaseAnalytics;
  Pinned_Adapter pinned_adapter;
  FloatingActionButton Add_New;
  Button Pinned_Btn;
  FirebaseFirestore db = FirebaseFirestore.getInstance();
  public Pinned_Fragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.pinned_fragment_recycle_view,container,false);

    recyclerView =(RecyclerView)view.findViewById(R.id.pinned_fragment_recycle_view);
    //FloatingActionButton floatingActionButton = view.findViewById(R.id.)
    // progressBar =(ProgressBar)view.findViewById(R.id.progreessbar);
    Add_New =(FloatingActionButton) view.findViewById(R.id.add_new_Product_btn);
    Add_New.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getContext(), Add_New_Product.class));
      }
    });
    pinned_adapter = new Pinned_Adapter(getContext(),pinned_list_item);
    recyclerView.setAdapter(pinned_adapter);
    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);

    recyclerView.setLayoutManager(staggeredGridLayoutManager);

    // recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setHasFixedSize(true);

    recyclerView.setAdapter(pinned_adapter);
    return view;

  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    pinned_list_item = new ArrayList<>();

    //new Dave().execute();

    loadProducts();
  }
  private boolean loadProducts() {

    db.collection("Pinned_Business").whereEqualTo("Business_Id",SharedPrefManager.getInstance(getContext()).getBussiness_ID())
      .addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
          if (e != null) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
          }
          for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
            if (doc.getType() == DocumentChange.Type.ADDED) {
              String doc_id = doc.getDocument().getId();
             // Toast.makeText(getContext(),doc_id.toString(), Toast.LENGTH_SHORT).show();
              Pinned_Model read = doc.getDocument().toObject(Pinned_Model.class).withId(doc_id);
              pinned_list_item.add(read);
              pinned_adapter.notifyDataSetChanged();
            }
          }
        }
      });
    return true;

  }

}
