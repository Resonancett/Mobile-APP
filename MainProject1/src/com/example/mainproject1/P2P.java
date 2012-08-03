package com.example.mainproject1;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class P2P extends Activity {
	private static final int PICK_CONTACT = 0;
	ImageButton selectSendContact, selectRequestContact;
	EditText sendPhone, requestPhone;
	Spinner paymentMethods;
	Button homeSend, homeRequest;
	String selectedMethod;
	private boolean tab1Contact = false;
	
	/* In addition to the usual, takes care of initializing the tabs. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.p2p);
		TabHost tabHost=(TabHost)findViewById(R.id.tabHost);
		tabHost.setup();
		
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.p2p);

		TabSpec spec1=tabHost.newTabSpec("Tab 1");
		spec1.setContent(R.id.tab1);
		spec1.setIndicator("Send");

		TabSpec spec2=tabHost.newTabSpec("Tab 2");
		spec2.setIndicator("Request");
		spec2.setContent(R.id.tab2);

		tabHost.addTab(spec1);
		tabHost.addTab(spec2);

        sendPhone = (EditText)findViewById(R.id.send_phone);
        requestPhone = (EditText)findViewById(R.id.phone_request);
        
        selectSendContact = (ImageButton)findViewById(R.id.select_contact_send);
        selectSendContact.setOnClickListener(sendContactListener);
        selectRequestContact = (ImageButton)findViewById(R.id.select_contact_request);
        selectRequestContact.setOnClickListener(requestContactListener);
        
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
	 /* First of two methods (one for each tab) that handle the contact choosing 
	  * mechanisms for the application.
	  */
	 private OnClickListener sendContactListener = new OnClickListener() {
		 public void onClick(View v) {
	    		try {
	    			tab1Contact = true;
	    			Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
	    			startActivityForResult(intent,PICK_CONTACT);
	    		} catch(Exception ex) {
	    			Toast.makeText(getApplicationContext(), 
	    					       "We couldn't access your phone book. Please enter the phone number manually.", 
	    					       Toast.LENGTH_LONG).show();
	    		}
		 }	 
	 };
	 /* Second of two methods (one for each tab) that handle the contact choosing 
	  * mechanisms for the application.
	  */
	 private OnClickListener requestContactListener = new OnClickListener() {
		 public void onClick(View v) {
	    		try {
	    			tab1Contact = false;
	    			Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
	    			startActivityForResult(intent,PICK_CONTACT);
	    		} catch(Exception ex) {
	    			Toast.makeText(getApplicationContext(), 
	    					       "We couldn't access your phone book. Please enter the phone number manually.", 
	    					       Toast.LENGTH_LONG).show();
	    		}
		 }	 
	 };
 
	 /* Returns the contact's phone number to the application and 
	  * populates the phone number field with it.
	  */
	 protected void onActivityResult(int requestCode, int resultCode,
	         Intent data) {
	     if (requestCode == PICK_CONTACT) {
	         if (resultCode == RESULT_OK) {
	        	 Uri contactData = data.getData();
	             Cursor c = getApplicationContext().getContentResolver().query(contactData, null, null, null, null);
	             if (c.moveToFirst()) {
	            	 String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
	               String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
	               if(hasPhone.equals("1")) {
	            	   Cursor phones = getContentResolver().query( 
	                           ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, 
	                           ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id, 
	                           null, null);
	                 phones.moveToFirst();
	                 String cNumber = phones.getString(phones.getColumnIndex("data1"));
	            	 String number = "";
	            	 for(int i = 0; i < cNumber.length(); i++) {
	            		 char ch = cNumber.charAt(i);
	            		 if(Character.isDigit(ch)) {
	            			 number = number + ch;
	            		 }
	            	 }
	            	 if(tab1Contact) {
	            		 sendPhone.setText(number, TextView.BufferType.EDITABLE);
	            	 } else {
	            		 requestPhone.setText(number, TextView.BufferType.EDITABLE);
	            	 }
	               }
	             }
	         }
	     }
	 }
}