package com.filenber.chainshop.personal_profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import Models.My_Product_Model;

public class Myproduct_Adapter  extends RecyclerView.Adapter<Myproduct_Adapter.ViewHolder> {
  Context mcx;
  List<My_Product_Model> job_content;
  Context context;
  QueryDocumentSnapshot response;
  FirebaseAuth firebaseAuth;
  private FirebaseAnalytics mFirebaseAnalytics;
  FirebaseFirestore db = FirebaseFirestore.getInstance();
  private CollectionReference reference = db.collection("Product");
  private StorageReference storageReference;
  // InterstitialAd mInterstitialAd;
  public Myproduct_Adapter( Context cnx, List<My_Product_Model> contents)
  {
    this.mcx = cnx;
    this.job_content=contents;
  }

  public Myproduct_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    LayoutInflater inflater =  LayoutInflater.from(mcx);
    View view = inflater.inflate(R.layout.myproduct_fragment,null);
    return new Myproduct_Adapter.ViewHolder(view);

  }



  public void onBindViewHolder(@NonNull Myproduct_Adapter.ViewHolder holder, final int  position) {
    final   My_Product_Model content = job_content.get(position);
    holder.Product_Name.setText(content.getProduct_name());
    holder.Product_price.setText(content.getProduct_price());
    Glide.with(mcx).load(content.getProduct_Image()).into(holder.Product_Image);
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
    TextView Product_decr,Product_Name ;
    Button Product_price ;
    ImageView Product_Image;
    CardView Product_card;

    public ViewHolder( View itemView) {
      super(itemView);
      Product_Name = (TextView)itemView.findViewById(R.id.my_product_name);
      Product_Image = (ImageView) itemView.findViewById(R.id.my_product_image);
      Product_card = (CardView)itemView.findViewById(R.id.my_product_card);
      Product_price = (Button)itemView.findViewById(R.id.my_product_price);


    }
  }

}
