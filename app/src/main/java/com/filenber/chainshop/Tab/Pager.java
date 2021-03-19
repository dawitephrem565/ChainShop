package com.filenber.chainshop.Tab;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.filenber.chainshop.Pinned_Fragment;
import com.filenber.chainshop.ui.main.MyProduct_Fragment;

public class Pager extends FragmentStatePagerAdapter {

  //integer to count number of tabs
  int tabCount;

  //Constructor to the class
  public Pager(FragmentManager fm, int tabCount) {
    super(fm);
    //Initializing tab count
    this.tabCount= tabCount;
  }

  //Overriding method getItem
  @Override
  public Fragment getItem(int position) {
    //Returning the current tabs
    switch (position) {
      case 0:
        MyProduct_Fragment tab1 = new MyProduct_Fragment();
        return tab1;
      case 1:
        Pinned_Fragment tab2 = new Pinned_Fragment();
        return tab2;

      default:
        return null;
    }
  }

  //Overriden method getCount to get the number of tabs
  @Override
  public int getCount() {
    return tabCount;
  }
}

