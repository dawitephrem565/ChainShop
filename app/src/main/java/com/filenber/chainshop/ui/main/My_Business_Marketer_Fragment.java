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

import com.filenber.chainshop.Add_Followers;
import com.filenber.chainshop.Add_New_Product;
import com.filenber.chainshop.R;
import com.filenber.chainshop.SharedPrefManager;
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

import Adapter.MyBusiness_Marketer_Adapter;
import Models.MyBusiness_Marketer_Model;

public class My_Business_Marketer_Fragment extends Fragment {
  Dialog dialog;
  View view;
  RecyclerView recyclerView;
  ProgressBar progressBar;
  List<MyBusiness_Marketer_Model> myBusiness_marketer_list;
  FirebaseAuth firebaseAuth;
  private FirebaseAnalytics mFirebaseAnalytics;
  MyBusiness_Marketer_Adapter myBusiness_marketer_adapter;
  FloatingActionButton Add_New;
  FirebaseFirestore db = FirebaseFirestore.getInstance();
  public My_Business_Marketer_Fragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.my_business_marketer_fragemt,container,false);

    recyclerView =(RecyclerView)view.findViewById(R.id.my_business_marketer_recycle_view);

    //FloatingActionButton floatingActionButton = view.findViewById(R.id.)
    // progressBar =(ProgressBar)view.findViewById(R.id.progreessbar);
    Add_New =(FloatingActionButton) view.findViewById(R.id.add_new_my_business_marketer_btn);
    Add_New.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getContext(), Add_Followers.class));
      }
    });
    myBusiness_marketer_adapter = new MyBusiness_Marketer_Adapter(getContext(),myBusiness_marketer_list);
    recyclerView.setAdapter(myBusiness_marketer_adapter);
    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);

    recyclerView.setLayoutManager(staggeredGridLayoutManager);

    // recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setHasFixedSize(true);

    recyclerView.setAdapter(myBusiness_marketer_adapter);
    return view;

  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    myBusiness_marketer_list = new ArrayList<>();

    //new Dave().execute();

    loadProducts();
  }
  private boolean loadProducts() {

    db.collection("Business Marketers")
      .whereEqualTo("Business_Id", SharedPrefManager.getInstance(getContext()).getBussiness_ID())
      .whereEqualTo("Approval_Status", "Yes")

      .addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
          if (e != null) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
          }
          for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
            if (doc.getType() == DocumentChange.Type.ADDED) {
              String doc_id = doc.getDocument().getId();

              MyBusiness_Marketer_Model read = doc.getDocument().toObject(MyBusiness_Marketer_Model.class);
              myBusiness_marketer_list.add(read);
              myBusiness_marketer_adapter.notifyDataSetChanged();
            }
          }
        }
      });
    return true;

  }

}
