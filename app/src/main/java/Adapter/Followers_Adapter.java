package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.filenber.chainshop.Main_Page_Model;
import com.filenber.chainshop.Product_Detail_Page;
import com.filenber.chainshop.R;
import com.filenber.chainshop.SharedPrefManager;
import com.filenber.chainshop.personal_profile.add_followers_model;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import Stracture.function;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Followers_Adapter  extends RecyclerView.Adapter<Followers_Adapter.ViewHolder> implements Filterable {
  Context mcx;
  List<add_followers_model> job_content;
  List<add_followers_model> job_content_full;
  Context context;
  QueryDocumentSnapshot response;
  FirebaseAuth firebaseAuth;
  private FirebaseAnalytics mFirebaseAnalytics;
  FirebaseFirestore db = FirebaseFirestore.getInstance();

  private StorageReference storageReference;
  // InterstitialAd mInterstitialAd;
  public Followers_Adapter( Context cnx, List<add_followers_model> job_content)
  {
    this.mcx = cnx;
    this.job_content=job_content;
    job_content_full = new ArrayList<>(job_content);
  }

  public Followers_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    LayoutInflater inflater =  LayoutInflater.from(mcx);
    View view = inflater.inflate(R.layout.followers_content,null);
    return new Followers_Adapter.ViewHolder(view);

  }



  public void onBindViewHolder(@NonNull final Followers_Adapter.ViewHolder holder, final int  position) {
    final   add_followers_model content = job_content.get(position);
    holder.Username.setText(content.getUsername());
    holder.user_tag.setText(content.getTag_username());
     holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });
     holder.invite_btn.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
         function function = new function();
         function.invitation(mcx ,content.Id,content.getUsername(),SharedPrefManager.getInstance(mcx).getBussiness_ID(),SharedPrefManager.getInstance(mcx).getBussiness_Commision(),"Pending","noting",SharedPrefManager.getInstance(context).getBussiness_Name());
         job_content.remove(position);
         notifyDataSetChanged();
       }
     });

  }

  public int getItemCount() {
    return job_content.size();
  }

  @Override
  public Filter getFilter() {
    return exampleFilter;
  }
  private Filter exampleFilter = new Filter() {
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
      List<add_followers_model> filteredList = new ArrayList<>();
      if (constraint == null || constraint.length() == 0) {
        filteredList.addAll(job_content_full);
      } else {
        String filterPattern = constraint.toString().toLowerCase().trim();
        for (add_followers_model item : job_content_full) {
          if (item.getUsername().toLowerCase().contains(filterPattern)) {
            filteredList.add(item);
          }
        }
      }
      FilterResults results = new FilterResults();
      results.values = filteredList;
      return results;
    }
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
      job_content.clear();
      job_content.addAll((List) results.values);
      notifyDataSetChanged();
    }
  };

  public class ViewHolder extends RecyclerView.ViewHolder {
    TextView user_tag,Username ;
    Button invite_btn ;
    ImageView Product_Image;
    CardView Product_card;

    public ViewHolder( View itemView) {
      super(itemView);
      Username = (TextView)itemView.findViewById(R.id.username);
      user_tag = (TextView)itemView.findViewById(R.id.user_tag_name);
    //  User_Image = (ImageView) itemView.findViewById(R.id.product_image);
      //  Product_card = (CardView)itemView.findViewById(R.id.user);
      invite_btn = (Button)itemView.findViewById(R.id.invite_btn);


    }
  }

}
