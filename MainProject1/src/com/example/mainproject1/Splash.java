package com.example.mainproject1;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.MeasureSpec;

public class Splash extends Activity {
	
	// Length of splash display in milliseconds.
	private static final int SPLASH_DISPLAY_LENGTH = 3000;
	  
	@Override
    public void onCreate(Bundle savedInstanceState) {
        
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);
        
        View splash = findViewById(R.id.splash);
        View root = splash.getRootView();
        root.setBackgroundColor(getResources().getColor(android.R.color.white));

                
        /* New Handler to start the Menu-Activity 
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash.this,MainActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
