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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

/* 
 * Note: QR Code integration was suggested as a useful and cool feature
 * for this Activity. There wasn't time to implement it, but there is 
 * an open-source library at http://code.google.com/p/zxing/ that would
 * be able to manage QR codes.
 */
public class OTC extends Activity {
	Button home;
	Spinner paymentMethods;
	String selectedMethod;
	EditText merchantIdField;
	EditText amountField;
	Button makePaymentButton;
	ImageView qrScan;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.otc);
	        
	        ActionBar actionBar = getActionBar();
	        actionBar.setDisplayHomeAsUpEnabled(true);
	        actionBar.setTitle("Business Payment");
	        
	        merchantIdField = (EditText)findViewById(R.id.merchant);
	        amountField = (EditText)findViewById(R.id.payment_amount);
	        
	        makePaymentButton = (Button)findViewById(R.id.make_otc);
	        makePaymentButton.setOnClickListener(makePayment);
	        
	        qrScan = (ImageView)findViewById(R.id.scan_qr);
	        qrScan.setOnClickListener(qrScanListener);
	        
	        // Initializes the payment methods spinner, and adds its listener.
	        ArrayList<String> paymentMethodsList = ((MyApplication)getApplication()).paymentNames();      
	        paymentMethods = (Spinner)findViewById(R.id.otc_payment_methods);
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
	
		
	private OnClickListener qrScanListener = new OnClickListener() {
		public void onClick(View v) {
			int merchantId = 0;
			double paymentAmount = 0;
			//TODO: Implement QR Code recognition to fill merchantId and paymentAmount
			merchantIdField.setText(Integer.toString(merchantId));
			amountField.setText(Double.toString(paymentAmount));
		}	
	};

	 /* Gathers the data from the form and makes the payment, reporting an error/success message */
	 private OnClickListener makePayment = new OnClickListener() {
		 public void onClick(View v) {
			 double paymentAmount;
			 int merchantID;
			 try{
				 paymentAmount  = Double.parseDouble(amountField.getText().toString());
				 merchantID = Integer.parseInt(merchantIdField.getText().toString());
				String user = ((MyApplication)getApplication()).user;
				String error = "";
				otcPayment(paymentAmount, merchantID, user, selectedMethod, error);	
			 } catch(Exception ex) {
				 Toast.makeText(getApplicationContext(), "Please enter a valid payment amount and merchant ID", Toast.LENGTH_LONG).show();
			 }
				
		 }	 
	 };
	 
	 /* Makes the payment and updates the error message 
	  * TODO: Make this method actually do something.
	  */
	 private void otcPayment(double paymentAmount, int merchantID, String user, 
			 String selectedMethod, String error) {
		 
	 }
	 
	 /* Updates the selectedMethod when an option is chosen from the Spinner. */
	 private OnItemSelectedListener paymentSelecter = new OnItemSelectedListener() {
		 @Override
		 public void onItemSelected(AdapterView<?> parent, View v, int position,
		   long id) {
			 selectedMethod =  parent.getItemAtPosition(position).toString();
		 }
		 @Override
		 public void onNothingSelected(AdapterView<?> arg0) {}
	 };

}
