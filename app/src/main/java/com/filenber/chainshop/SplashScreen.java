package com.filenber.chainshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import static java.lang.Thread.sleep;

public class SplashScreen extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash_screen);
    Thread sleepThread = new Thread(
      new Runnable() {
        @Override
        public void run() {
          try {
             sleep(1000);
            startActivity(new Intent (SplashScreen.this,Login_Page.class));
          }
          catch (Exception ex)
          {

          }
        }
      }

    );
    sleepThread.start();
  }
}
