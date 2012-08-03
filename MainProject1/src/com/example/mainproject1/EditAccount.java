package com.example.mainproject1;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/* TODO: Complete the Activity based on the options for editing an account
 * 
 */
public class EditAccount extends Activity {
	Button home;
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.edit_account);
	        
	        ActionBar actionBar = getActionBar();
	        actionBar.setDisplayHomeAsUpEnabled(true);
	        actionBar.setTitle("Preferences");
	               
	 }
	 
	 /* Sets the menu options to be determined by the appropriate menu resource */
		@Override
		 public boolean onCreateOptionsMenu(Menu menu) {
		     MenuInflater inflater = getMenuInflater();
		     inflater.inflate(R.menu.register, menu);
		     return true;
		 }
		
		 /* Does stuff for each of the menu selections.
		 */
		@Override
		 public boolean onOptionsItemSelected(MenuItem item) {
		     switch (item.getItemId()) {
		         case android.R.id.home:
		             // App icon in action bar clicked; go home
		             Intent intent = new Intent(this, MainActivity.class);
		             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		             startActivity(intent);
		             return true;
		         case R.id.home_action:
		        	// Home icon selected. Go home. 
		 			Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
		 			startActivity(intent2);
		         case R.id.new_account_action:
		        	 Intent intent3 = new Intent(getApplicationContext(), AddAccount.class);
			 		 startActivity(intent3);
		         default:
		             return super.onOptionsItemSelected(item);
		     }
		}
}
