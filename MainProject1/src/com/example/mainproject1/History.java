package com.example.mainproject1;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

public class History extends Activity {
	Button home;
	
	
	/* Does the majority of the work for the Activity, which is
	 * to display the different elements in the History.
	 * TODO: Get it to dynamically display the elements of the History
	 */
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.history);
	        
	        ActionBar actionBar = getActionBar();
	        actionBar.setDisplayHomeAsUpEnabled(true);
	        actionBar.setTitle("History");
	        /*LinearLayout ll = (LinearLayout) findViewById(R.id.history_ll);
	        app.addTransaction("Top up", 10.00, "7479091");
	        app.addTransaction("P2P", 15.00, "1234567");
	        ArrayList<Transaction> transactions = app.transactions;
	        for(int i = 0; i < transactions.size(); i++) {
	        	TextView tv1 = new TextView(this);
	        	Transaction transaction = transactions.get(i);
	        	tv1.setText(transaction.type + "	" + Double.toString(transaction.amount));
	        	ll.addView(tv1);
	        	TextView tv2 = new TextView(this);
	        	if(transaction.success) {
	        		tv2.setText(transaction.recipient + "	Succeeded!");
	        	} else {
	        		tv2.setText(transaction.recipient + "	Failed");
	        	}
	        	ll.addView(tv2);
	        }*/
	       
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
