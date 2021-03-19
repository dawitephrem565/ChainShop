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

import com.filenber.chainshop.Pro_Tab;
import com.filenber.chainshop.R;
import com.filenber.chainshop.SharedPrefManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Models.MyBusiness_Marketer_Model;
import Models.MyBussiness_Model;

public class MyBusiness_Marketer_Adapter extends RecyclerView.Adapter<MyBusiness_Marketer_Adapter.ViewHolder> implements Filterable {
  Context mcx;
  List<MyBusiness_Marketer_Model> job_content;
  List<MyBusiness_Marketer_Model> job_content_full;
  Context context;
  QueryDocumentSnapshot response;
  FirebaseAuth firebaseAuth;
  private FirebaseAnalytics mFirebaseAnalytics;
  FirebaseFirestore db = FirebaseFirestore.getInstance();

  private StorageReference storageReference;

  // InterstitialAd mInterstitialAd;
  public MyBusiness_Marketer_Adapter(Context cnx, List<MyBusiness_Marketer_Model> job_content) {
    this.mcx = cnx;
    this.job_content = job_content;
    job_content_full = new ArrayList<>(job_content);
  }

  public MyBusiness_Marketer_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    LayoutInflater inflater = LayoutInflater.from(mcx);
    View view = inflater.inflate(R.layout.mybusiness_marketer_content, null);
    return new MyBusiness_Marketer_Adapter.ViewHolder(view);

  }


  public void onBindViewHolder(@NonNull final MyBusiness_Marketer_Adapter.ViewHolder holder, final int position) {
    final MyBusiness_Marketer_Model content = job_content.get(position);
    holder.Marketer_Name.setText(content.getUser_Name());
    // holder.user_tag.setText("Still Not Decide");
    Picasso.with(mcx).load(content.getUser_Profile_Img()).into(holder.Marketer_Product_Image);

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
      //  SharedPrefManager.getInstance(mcx).Save_Business_Data(content.getBusiness_Name(),content.Id,content.get(),content.getBusiness_Commission());
        Intent intent  = new Intent(mcx, Pro_Tab.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mcx.startActivity(intent);


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
      List<MyBusiness_Marketer_Model> filteredList = new ArrayList<>();
      if (constraint == null || constraint.length() == 0) {
        filteredList.addAll(job_content_full);
      } else {
        String filterPattern = constraint.toString().toLowerCase().trim();
        for (MyBusiness_Marketer_Model item : job_content_full) {
          if (item.getUser_Name().toLowerCase().contains(filterPattern)) {
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
    TextView user_tag, Marketer_Name;
    Button Product_price;
    ImageView Marketer_Product_Image;
    CardView Product_card;

    public ViewHolder(View itemView) {
      super(itemView);
      Marketer_Name = (TextView) itemView.findViewById(R.id.my_business_name);
      // user_tag = (TextView) itemView.findViewById(R.id.username);
      //  User_Image = (ImageView) itemView.findViewById(R.id.product_image);
      //  Product_card = (CardView)itemView.findViewById(R.id.user);
      Marketer_Product_Image = (ImageView) itemView.findViewById(R.id.profile_image);


    }
  }
}
