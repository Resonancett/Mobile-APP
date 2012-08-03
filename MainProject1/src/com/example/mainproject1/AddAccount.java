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
import android.widget.EditText;
import android.widget.Toast;


public class AddAccount extends Activity {
	Button home, add;
	EditText acctName;
	
	/* Creates the activity, assigns the layout, 
	 * and initializes instance variables from the layout.
	 */
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.add_account);
	        
	        ActionBar actionBar = getActionBar();
	        actionBar.setDisplayHomeAsUpEnabled(true);
	        actionBar.setTitle("Add Accounts");

	        add = (Button)findViewById(R.id.add_account);
	        add.setOnClickListener(addListener);
	        
	        acctName = (EditText)findViewById(R.id.new_acct_name);
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
		             Intent intent = new Intent(this, Accounts.class);
		             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		             startActivity(intent);
		             return true;
		         case R.id.home_action:
		        	// Home icon selected. Go home. 
		 			Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
		 			startActivity(intent2);
		         default:
		             return super.onOptionsItemSelected(item);
		     }
		 }

	 /* Adds an account with the name in the EditText field to the list of Accounts.
	  * TODO: Make the adding work properly.
	  */
	 private OnClickListener addListener = new OnClickListener() {
		 public void onClick(View v) {
			MyApplication myApp = ((MyApplication)getApplication());
			String name = acctName.getText().toString();
			PaymentMethod newMethod = new PaymentMethod();
			newMethod.setName(name);
			newMethod.setInfo("");
			myApp.addAccount(newMethod);
			Toast.makeText(getApplicationContext(), 
					       myApp.paymentNames().get(myApp.accountsSize() - 1) + "has been added", 
					       Toast.LENGTH_LONG).show();
		 }	 
	 };
}
