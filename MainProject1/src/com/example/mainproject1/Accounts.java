package com.example.mainproject1;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class Accounts extends Activity {
	Button home, addAcct, wallet;
	Spinner accounts;
	
	LinearLayout setPrimary;
	
	TextView primaryAcct;
	
	private MyApplication myApp;
	
	private static final int SET_PRIMARY_ID = 0;
	/* Creates the activity, assigns the layout, 
	 * and initializes instance variables from the layout.
	 */
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.accounts);
	        
	        ActionBar actionBar = getActionBar();
	        actionBar.setDisplayHomeAsUpEnabled(true);
	        actionBar.setTitle("Accounts");
	        
	        myApp = (MyApplication)getApplication();
	        
	        ArrayList<String> paymentMethodsList = new ArrayList<String>();
	        paymentMethodsList.add("Select account");
	        for(int i = 2; i < myApp.accountsSize(); i++) {
	        	paymentMethodsList.add(myApp.accountAt(i).getName());
	        }
	        accounts = (Spinner)findViewById(R.id.bank_accounts);
			ArrayAdapter<String> accountsAdapter = new ArrayAdapter<String>(this,
			        android.R.layout.simple_spinner_item, paymentMethodsList);
			accountsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			accounts.setAdapter(accountsAdapter);
			accounts.setOnItemSelectedListener(accountSelecter);

	        wallet = (Button)findViewById(R.id.manage_wallet);
	        wallet.setOnClickListener(walletListener);
	        
	        setPrimary = (LinearLayout)findViewById(R.id.set_primary);
	        setPrimary.setOnClickListener(setPrimaryListener);
	        
	        primaryAcct = (TextView)findViewById(R.id.primary_acct);
	        primaryAcct.setText(myApp.accounts.get(myApp.preferredPaymentMethod).getName());
	 }
	 
	 private OnClickListener setPrimaryListener = new OnClickListener() {
		 public void onClick(View v) {
			 showDialog(SET_PRIMARY_ID);
		 }
	 };
	 
	protected Dialog onCreateDialog(int id) {
		Dialog dialog;
	    AlertDialog alert;
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	switch(id) {
    	case SET_PRIMARY_ID:

	    	// Gets the list of payment methods from the data in the MyApplication instance
	    	// and copies it to an array of CharSequences.
	    	ArrayList<String> paymentMethodsList = myApp.paymentNames();
	    	final CharSequence[] items;
	    	items = new CharSequence[paymentMethodsList.size()];
	    	for(int i = 0; i < paymentMethodsList.size(); i++){
		    	items[i] = (CharSequence)paymentMethodsList.get(i);
	    	}

	    	// Sets the title
	    	builder.setTitle("Select a primary account");
	    	
	    	// Sets the Dialog to display the payment options, as in a spinner.
	    	builder.setItems(items, new DialogInterface.OnClickListener() {
	    	    public void onClick(DialogInterface dialog, int item) {
	    	    	myApp.preferredPaymentMethod = item;
	    	    	primaryAcct.setText(myApp.accounts.get(item).getName());
	    	    }
	    	});
	    	
	    	alert = builder.create();
	    	return alert;
    	default:
	        dialog = null;
    	}
    	return dialog;
	}
	 
	 /* Standard listener to select an account. 
	  * TODO: Start an activity to manage the account if something is selected. 
	  * Do nothing if nothing is selected.
	  */
	 private OnItemSelectedListener accountSelecter = new OnItemSelectedListener() {
		 @Override
		 public void onItemSelected(AdapterView<?> parent, View v, int position,
		   long id) {
		 }

		 @Override
		 public void onNothingSelected(AdapterView<?> arg0) {}
	 };
	 
	 /* Sets the menu options to be determined by the appropriate menu resource */
		@Override
		 public boolean onCreateOptionsMenu(Menu menu) {
		     MenuInflater inflater = getMenuInflater();
		     inflater.inflate(R.menu.accounts, menu);
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

	 
	 /* Transfers the user to the wallet Activity. */
	 private OnClickListener walletListener = new OnClickListener() {
		 public void onClick(View v) {
			Intent intent = new Intent(getApplicationContext(), Wallet.class);
 			startActivity(intent);
		 }	 
	 };
}
