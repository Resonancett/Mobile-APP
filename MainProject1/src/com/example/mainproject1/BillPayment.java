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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class BillPayment extends Activity {
	Button back, home;
	Spinner payees, payments;
	String payee, payment;
	LinearLayout paymentAmountLayout;
	double paymentAmount;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.bill_payment);
	        
	        ActionBar actionBar = getActionBar();
	        actionBar.setDisplayHomeAsUpEnabled(true);
	        actionBar.setTitle("Bill Payment");
	    	/*ArrayList<String> paymentMethodsList = ((MyApplication)getApplication()).paymentNames();
	    	final CharSequence[] items;
	    	items = new CharSequence[paymentMethodsList.size()];
	    	for(int i = 0; i < paymentMethodsList.size(); i++){
		    	items[i] = (CharSequence)paymentMethodsList.get(i);
	    	}
	        Spinner paymentMethods = (Spinner)findViewById(R.id.payment_method);
			ArrayAdapter<CharSequence> buyAdapter = ArrayAdapter.createFromResource(this,
			        items, android.R.layout.simple_spinner_item);
			buyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			paymentMethods.setAdapter(buyAdapter);*/

			ArrayList<String> payeeList = getPayeeList(((MyApplication)getApplication()).user);
			payees = (Spinner)findViewById(R.id.payees);
			ArrayAdapter<String> payeesAdapter = new ArrayAdapter<String>(this,
			        android.R.layout.simple_spinner_item, payeeList);
			payeesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			payees.setAdapter(payeesAdapter);
			payees.setOnItemSelectedListener(payeeSelecter);
					
			paymentAmountLayout = (LinearLayout)findViewById(R.id.enter_amount);
			
			
			paymentAmount = paymentAmountFor(((MyApplication)getApplication()).user, payee, payment);
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
	 
	 /* TODO: Get the list of payees for the given user. 
	  * 
	  */
	 private ArrayList<String> getPayeeList(String user) {
		 ArrayList<String> result = new ArrayList<String>();
		 result.add("Payee 1");
		 result.add("Payee 2");
		 return result;
	 }
	 
	 /* TODO: Get the list of payments for the given user-payment combination. 
	  * 
	  */
	 private ArrayList<String> getPaymentList(String user, String payee) {
		 ArrayList<String> result = new ArrayList<String>();
		 if(payee.equals("Payee 1")) {
			 result.add("Payment 1.1");
			 result.add("Payment 1.2");
		 } else if(payee.equals("Payee 2")) {
			 result.add("Payment 2.1");
			 result.add("Payment 2.2");
		 } else {
			 result.add("Payment infinity");
		 }
		 return result;
	 }
	 
	 /* TODO: Return the payment due for the given user, payee, and payment 
	  * 	 
	  */
	 private double paymentAmountFor(String user, String payee, String payment) {
		 return 0;
	 }
	 /* Standard listener to select a payee */
	 private OnItemSelectedListener payeeSelecter = new OnItemSelectedListener() {
		 @Override
		 public void onItemSelected(AdapterView<?> parent, View v, int position,
		   long id) {
			 	payee =  parent.getItemAtPosition(position).toString();
				ArrayList<String> paymentList = getPaymentList(((MyApplication)getApplication()).user, payee);
				payments = (Spinner)findViewById(R.id.bills);
				ArrayAdapter<String> paymentsAdapter = new ArrayAdapter<String>(BillPayment.this,
				        android.R.layout.simple_spinner_item, paymentList);
				paymentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				payments.setAdapter(paymentsAdapter);
				payments.setOnItemSelectedListener(paymentSelecter);
		 }
		 @Override
		 public void onNothingSelected(AdapterView<?> arg0) {}
	 };
	 
	 /* Standard listener to select a payment */
	 private OnItemSelectedListener paymentSelecter = new OnItemSelectedListener() {
		 @Override
		 public void onItemSelected(AdapterView<?> parent, View v, int position,
		   long id) {
			 payment =  parent.getItemAtPosition(position).toString();
			 paymentAmount = paymentAmountFor(((MyApplication)getApplication()).user,payee,payment);
		 }

		 @Override
		 public void onNothingSelected(AdapterView<?> arg0) {}
	 };
	 

}
