package Stracture;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.filenber.chainshop.Add_New_Product;
import com.filenber.chainshop.MainActivity;
import com.filenber.chainshop.SharedPrefManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class function {
  FirebaseFirestore db = FirebaseFirestore.getInstance();

  public void invitation(final Context context,
                         String id
                         ,String username,
                         String bussiness_id
                         ,String bussiness_commision
                         ,String pending
                         , String noting
  ,String Business_Name)
  {

    Map<String, Object> city = new HashMap<>();
    city.put("Marketer_name", username);
    city.put("Marketer_ID",id);
    city.put("Business_ID",bussiness_id);
    city.put("Marketer_Phone","");
    city.put("Business_name",Business_Name);
    city.put("Commission_amount",bussiness_commision);
    city.put("Status",pending);
    city.put("Business_profile_Img",noting);
    city.put("Owner_id", SharedPrefManager.getInstance(context).saved_uid());
    db.collection("Business Marketers")
      .add(city)

      .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
        @Override
        public void onSuccess(DocumentReference documentReference) {
          //save_Owner_id();
          Toast.makeText(context,"Your Business Invitation Sent Successfully ",Toast.LENGTH_LONG).show();
         // context.startActivity(new Intent(context, MainActivity.class));
        }
      })
      .addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
          Toast.makeText(context, "Error adding document" + e, Toast.LENGTH_LONG).show();
        }
      });
  }

  }

