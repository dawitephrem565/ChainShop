package com.filenber.chainshop.personal_profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.filenber.chainshop.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class Business_Pinned_Adapter extends RecyclerView.Adapter<Business_Pinned_Adapter.ViewHolder> {
  Context mcx;
  List<Business_Model> job_content;
  Context context;
  QueryDocumentSnapshot response;
  FirebaseAuth firebaseAuth;
private FirebaseAnalytics mFirebaseAnalytics;
  FirebaseFirestore db = FirebaseFirestore.getInstance();
private CollectionReference reference = db.collection("product");
private StorageReference storageReference;
// InterstitialAd mInterstitialAd;
public Business_Pinned_Adapter(Context cnx, List<Business_Model> contents)
  {
  this.mcx = cnx;
  this.job_content=contents;
  }

public Business_Pinned_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
  LayoutInflater inflater =  LayoutInflater.from(mcx);
  View view = inflater.inflate(R.layout.pinned_fragment,null);
  return new Business_Pinned_Adapter.ViewHolder(view);

  }



public void onBindViewHolder(@NonNull Business_Pinned_Adapter.ViewHolder holder, final int  position) {
final   Business_Model content = job_content.get(position);
  holder.Owner_Name.setText(content.getUsername());
  holder.Owner_Descr.setText(content.getQoute_Descr());
  Glide.with(mcx).load(content.getProfile_Img()).into(holder.Owner_Profile_pic);


  holder.itemView.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {


  }
  });


  }


public int getItemCount() {
  return job_content.size();
  }

public class ViewHolder extends RecyclerView.ViewHolder {
  TextView Owner_Name, Owner_Descr;
  ImageView Owner_Profile_pic;
  CardView Product_card;
  public ViewHolder(View itemView) {
    super(itemView);
    Owner_Name = (TextView) itemView.findViewById(R.id.owner_name);
    Owner_Profile_pic = (ImageView) itemView.findViewById(R.id.Owner_Profile_Pic);
    //Product_card = (CardView)itemView.findViewById(R.id.my_product_card);
    Owner_Descr = (TextView) itemView.findViewById(R.id.Owner_description);


  }
}
}
