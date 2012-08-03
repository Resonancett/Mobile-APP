package com.example.mainproject1;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Wallet extends Activity {
	Button home;
	String selectedMethod;
	Spinner paymentMethods;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.wallet);
	 
	        ActionBar actionBar = getActionBar();
	        actionBar.setDisplayHomeAsUpEnabled(true);
	        actionBar.setTitle("Wallet");
	        
	        ArrayList<String> paymentMethodsList = ((MyApplication)getApplication()).paymentNames();
	        paymentMethods = (Spinner)findViewById(R.id.payment_method);
			ArrayAdapter<String> paymentMethodsAdapter = new ArrayAdapter<String>(this,
			        android.R.layout.simple_spinner_item, paymentMethodsList);
			paymentMethodsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			paymentMethods.setAdapter(paymentMethodsAdapter);
			paymentMethods.setOnItemSelectedListener(paymentSelecter);
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
	 
	 private OnItemSelectedListener paymentSelecter = new OnItemSelectedListener() {
		 @Override
		 public void onItemSelected(AdapterView<?> parent, View v, int position,
		   long id) {
			 selectedMethod =  parent.getItemAtPosition(position).toString();
		 }

		 @Override
		 public void onNothingSelected(AdapterView<?> arg0) {


		 }
	 };
}
