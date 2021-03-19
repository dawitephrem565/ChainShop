package com.filenber.chainshop.personal_profile;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.filenber.chainshop.MainActivity;
import com.filenber.chainshop.Pro_Tab;
import com.filenber.chainshop.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import document_id_package.Pinned_doc_id;

public class Pinned_Adapter extends RecyclerView.Adapter<Pinned_Adapter.ViewHolder> {
  Context mcx;
  List<Pinned_Model> job_content;
  Context context;
  QueryDocumentSnapshot response;
  FirebaseAuth firebaseAuth;
  private FirebaseAnalytics mFirebaseAnalytics;
  FirebaseFirestore db = FirebaseFirestore.getInstance();
  private CollectionReference reference = db.collection("product");
  private StorageReference storageReference;
  // InterstitialAd mInterstitialAd;
  public Pinned_Adapter(Context cnx, List<Pinned_Model> contents)
  {
    this.mcx = cnx;
    this.job_content=contents;
  }

  public Pinned_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    LayoutInflater inflater =  LayoutInflater.from(mcx);
    View view = inflater.inflate(R.layout.pinned_fragment,null);
    return new Pinned_Adapter.ViewHolder(view);

  }



  public void onBindViewHolder(@NonNull Pinned_Adapter.ViewHolder holder, final int  position) {
    final   Pinned_Model content = job_content.get(position);
    holder.Owner_Name.setText(content.getProduct_name());
  //  holder.Owner_Descr.setText(content.getQoute_Descr());

   Glide.with(mcx).load(content.getProduct_Image()).into(holder.Owner_Profile_pic);
   holder.Pinned_btn.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
       db.collection("Pinned_Product").document(job_content.get(position).Id)
         .delete()
         .addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void aVoid) {
            Toast.makeText(mcx, "DocumentSnapshot successfully deleted!",Toast.LENGTH_LONG).show();
            mcx.startActivity(new Intent(mcx, Pro_Tab.class));
           }
         })
         .addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
             Toast.makeText(mcx,  "Error deleting document",Toast.LENGTH_LONG).show();
           }
         });

     }
   });
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
    TextView Owner_Name,Owner_Descr ;

    ImageView Owner_Profile_pic;
    CardView Product_card;
    Button Pinned_btn;

    public ViewHolder( View itemView) {
      super(itemView);
      Owner_Name = (TextView)itemView.findViewById(R.id.owner_name);
      Owner_Profile_pic = (ImageView) itemView.findViewById(R.id.Owner_Profile_Pic);
      //Product_card = (CardView)itemView.findViewById(R.id.my_product_card);
      Owner_Descr = (TextView) itemView.findViewById(R.id.Owner_description);
     Pinned_btn = (Button)itemView.findViewById(R.id.pinned_btn_businnes_frag);

    }
  }

}
