package com.filenber.chainshop.ui.main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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

import com.filenber.chainshop.Add_New_Product;
import com.filenber.chainshop.R;
import com.filenber.chainshop.SharedPrefManager;
import com.filenber.chainshop.personal_profile.Myproduct_Adapter;
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

import Models.My_Product_Model;

public class MyProduct_Fragment extends Fragment {
  Dialog dialog;
  View view;
  RecyclerView recyclerView;
  ProgressBar progressBar;
  List<My_Product_Model> myproduct_list_item;
  FirebaseAuth firebaseAuth;
  private FirebaseAnalytics mFirebaseAnalytics;
  Myproduct_Adapter myproduct_adapter;
  FloatingActionButton Add_New;
  FirebaseFirestore db = FirebaseFirestore.getInstance();
  public MyProduct_Fragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.myproduct_recycle_view,container,false);

    recyclerView =(RecyclerView)view.findViewById(R.id.myproduct_recycle_veiw);

    //FloatingActionButton floatingActionButton = view.findViewById(R.id.)
    // progressBar =(ProgressBar)view.findViewById(R.id.progreessbar);
      Add_New =(FloatingActionButton) view.findViewById(R.id.add_new_Product_btn);
      Add_New.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          startActivity(new Intent(getContext(), Add_New_Product.class));
        }
      });
     myproduct_adapter = new Myproduct_Adapter(getContext(),myproduct_list_item);
    recyclerView.setAdapter(myproduct_adapter);
    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);

    recyclerView.setLayoutManager(staggeredGridLayoutManager);

    // recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setHasFixedSize(true);

    recyclerView.setAdapter(myproduct_adapter);
    return view;

  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    myproduct_list_item = new ArrayList<>();

    //new Dave().execute();

    loadProducts();
  }
  private boolean loadProducts() {

    db.collection("Product")
      .whereEqualTo("Owner_id",SharedPrefManager.getInstance(getContext()).saved_uid())
      .whereEqualTo("Business_ID",SharedPrefManager.getInstance(getContext()).getBussiness_ID())
      .addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
          if (e != null) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
          }
          for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
            if (doc.getType() == DocumentChange.Type.ADDED) {
              String doc_id = doc.getDocument().getId();

              My_Product_Model read = doc.getDocument().toObject(My_Product_Model.class);
              myproduct_list_item.add(read);
              myproduct_adapter.notifyDataSetChanged();
            }
          }
        }
      });
    return true;

  }

}

