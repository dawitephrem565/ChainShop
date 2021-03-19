package com.filenber.chainshop;

import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Main_Page_adapter extends RecyclerView.Adapter<Main_Page_adapter.ViewHolder> {
  Context mcx;
  List<Main_Page_Model> job_content;
  Context context;
  QueryDocumentSnapshot response;
  FirebaseAuth firebaseAuth;
  private FirebaseAnalytics mFirebaseAnalytics;
  FirebaseFirestore db = FirebaseFirestore.getInstance();
  private CollectionReference reference = db.collection("product");
  private StorageReference storageReference;
  // InterstitialAd mInterstitialAd;
  public Main_Page_adapter( Context cnx, List<Main_Page_Model> contents)
  {
    this.mcx = cnx;
    this.job_content=contents;
  }

  public Main_Page_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    LayoutInflater inflater =  LayoutInflater.from(mcx);
    View view = inflater.inflate(R.layout.custom_main_content,null);
    return new Main_Page_adapter.ViewHolder(view);

  }



  public void onBindViewHolder(@NonNull Main_Page_adapter.ViewHolder holder, final int  position) {
    final   Main_Page_Model content = job_content.get(position);
    holder.Product_Name.setText(content.getProduct_name());
    holder.Product_price.setText(content.getProduct_price());

    Glide.with(mcx).load(content.getProduct_Image()).into(holder.Product_Image);



    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
       Intent detail_activity = new Intent(mcx,Product_Detail_Page.class);
       detail_activity.putExtra("product_image", content.Product_Image);
       detail_activity.putExtra("product_name",content.Product_name);
       detail_activity.putExtra("product_price",content.Product_price);
       detail_activity.putExtra("product_descr",content.Product_descr);
        detail_activity.putExtra("Owner_Id",content.Owner_id);
       detail_activity.addFlags(FLAG_ACTIVITY_NEW_TASK);
       mcx.startActivity(detail_activity);

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
      Product_Name = (TextView)itemView.findViewById(R.id.product_name);
      Product_Image = (ImageView) itemView.findViewById(R.id.product_image);
      Product_card = (CardView)itemView.findViewById(R.id.product_card);
      Product_price = (Button)itemView.findViewById(R.id.product_price);


    }
  }

}
