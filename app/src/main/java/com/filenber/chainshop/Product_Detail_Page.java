package com.filenber.chainshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Product_Detail_Page extends AppCompatActivity implements View.OnClickListener {
  ImageView Product_image;
  Button add, min, checkout_btn, other_product_btn;
  TextView price_tag, product_descr, amount;
  int initial = 1;
  String product_name_intent, product_image_intent, price_tag_intent, product_name, descr_tag_intent, Owner_Id_Intent;

  FirebaseFirestore db = FirebaseFirestore.getInstance();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_product__detail__page);
    Product_image = findViewById(R.id.product_detail_image);
    add = findViewById(R.id.plus_number_btn);
    min = findViewById(R.id.min_plus_btn);
    amount = findViewById(R.id.number_amount_text);
    price_tag = findViewById(R.id.product_price_tag);
    product_descr = findViewById(R.id.product_descr);
    checkout_btn = findViewById(R.id.checkout_btn);
    other_product_btn = findViewById(R.id.other_product_btn);
    if (amount.getText().toString().equals("1")) {
      min.setVisibility(View.INVISIBLE);
    }
    init_value();
    checkout_btn.setOnClickListener(this);
    other_product_btn.setOnClickListener(this);
    add.setOnClickListener(this);
    min.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.plus_number_btn:
        int total_amount = Integer.parseInt(amount.getText().toString());
        int price_tag_total = Integer.parseInt(getIntent().getStringExtra("product_price"));
        int total_price;
        total_amount = initial++;
        min.setVisibility(View.VISIBLE);
        total_price = total_amount * price_tag_total;

        amount.setText(String.valueOf(total_amount));
        price_tag.setText(String.valueOf(total_price));
        break;
      case R.id.min_plus_btn:
        int total_amount_min = Integer.parseInt(amount.getText().toString());
        int price_tag_total_min = Integer.parseInt(getIntent().getStringExtra("product_price"));
        int total_price_min;

        total_amount_min = initial--;
        if (total_amount_min <= 1) {
          price_tag.setText(getIntent().getStringExtra("product_price"));
          amount.setText(String.valueOf(1));
          Toast.makeText(this, "Check The Value  ", Toast.LENGTH_SHORT).show();
        } else {
          total_price_min = total_amount_min * price_tag_total_min;
          amount.setText(String.valueOf(total_amount_min));
          price_tag.setText(String.valueOf(total_price_min));
        }


        break;
      case R.id.checkout_btn:
        break;
      case R.id.other_product_btn:
        break;
    }

  }

  public void init_value() {

    product_name_intent = getIntent().getStringExtra("product_name");
    product_image_intent = getIntent().getStringExtra("product_image");
    price_tag_intent = getIntent().getStringExtra("product_price");
    descr_tag_intent = getIntent().getStringExtra("product_descr");
    Owner_Id_Intent = getIntent().getStringExtra("Owner_Id");
    Glide.with(Product_Detail_Page.this).load(product_image_intent).into(Product_image);

    price_tag.setText(price_tag_intent);
    product_descr.setText(descr_tag_intent);


  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.product_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.pin_product) {
      //  startActivity(new Intent(Product_Detail_Page.this,Personal_Profile_Page.class));
      pin_product();
      return true;
    }


    return super.onOptionsItemSelected(item);
  }

  public void pin_product() {
    if (Owner_Id_Intent.equals(SharedPrefManager.getInstance(Product_Detail_Page.this).saved_uid())) {
      Toast.makeText(this, "You Are Not Allowed to Pin You Own Product ", Toast.LENGTH_SHORT).show();
    } else {
      Map<String, Object> city = new HashMap<>();
      city.put("Product_name", product_name_intent.toString());
      city.put("Product_descr", descr_tag_intent.toString());
      city.put("Product_price", price_tag_intent.toString());
      city.put("Product_Image", product_image_intent.toString());
      city.put("Pinner_id", SharedPrefManager.getInstance(getBaseContext()).saved_uid().toString());
      city.put("Owner_Id", Owner_Id_Intent.toString());


      db.collection("Pinned_Product")
        .add(city)

        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
          @Override
          public void onSuccess(DocumentReference documentReference) {
            //save_Owner_id();
            Toast.makeText(getBaseContext(), "Pinned Successfully ", Toast.LENGTH_LONG).show();
            //  startActivity(new Intent(Product_Detail_Page.this,MainActivity.class));
          }
        })
        .addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
            Toast.makeText(getBaseContext(), "Error Pinning  the product try again" + e, Toast.LENGTH_LONG).show();
          }
        });
    }

  }

}
