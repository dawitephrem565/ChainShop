package com.filenber.chainshop;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.filenber.chainshop.Tab.Pager;
import com.filenber.chainshop.Tab.TabAdapter;
import com.filenber.chainshop.ui.main.MyProduct_Fragment;
import com.filenber.chainshop.ui.main.My_Business_Marketer_Fragment;
import com.google.android.material.tabs.TabLayout;

public class Pro_Tab extends AppCompatActivity {

  //This is our tablayout
  private TabLayout tabLayout;
TabAdapter adapter;
  //This is our viewPager
   ViewPager viewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.tab_custom);

    //Adding toolbar to the activity
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    //Initializing the tablayout
    tabLayout = (TabLayout) findViewById(R.id.tabLayout);
    adapter = new TabAdapter(getSupportFragmentManager());
      adapter.addFragment(new MyProduct_Fragment(), "My Product");
    adapter.addFragment(new My_Business_Marketer_Fragment(), "Advertiser");

    adapter.addFragment(new Pinned_Fragment(), "Pinned");

    viewPager = (ViewPager) findViewById(R.id.pager);
    viewPager.setAdapter(adapter);
    tabLayout.setupWithViewPager(viewPager);
  }
}
