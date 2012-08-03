package com.example.mainproject1;

import java.math.BigDecimal;
import java.util.*;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import org.ksoap2.transport.AndroidHttpTransport;

import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal; 
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalPayment;

@SuppressWarnings("deprecation")
@SuppressLint("ParserError")
public class AutoTopUp extends Activity {
	MyApplication myApp;
	
	EditText amountField, phoneNumberField, recipientDialogField;
	String selectedMethod;
	CheckBox recipientIsMe;
	LinearLayout enterAmount, enterRecipient, enterMethod, enterRecurrence;
	TextView paymentAmountField, recipientField, methodField, recurrenceField;
	private int randomId;
	private String operator;
	Button makePaymentButton;
	CheckoutButton launchPayPalButton;
	
	private boolean paypalInitialized;
	
	private double topupAmount;
	private long recipient;
	private int paymentMethodItem;
	
	private int recurrence;
	private static final int DAILY = 0;
	private static final int WEEKLY = 1;
	private static final int MONTHLY = 2;
	
	private static final int PICK_CONTACT = 0;
	private static final int AMOUNT_ID = 1;
	private static final int RECIPIENT_ID = 2;
	private static final int METHOD_ID = 3;
	private static final int RECURRENCE_ID = 4;
	private static final int LOGIN_ID = 5;
	
	private static final int server = PayPal.ENV_SANDBOX;
	private static final String paypalAppId = "APP-80W284485P519543T";

	private static final String SOAP_ACTION = "http://InfoTechHubServices.com/MCheck/DoPosting";
	private static final String METHOD_NAME = "DoPosting";
	private static final String NAMESPACE = "http://InfoTechHubServices.com/MCheck";
	private static final String URL = "http://10.251.64.30:8081/mcheck.asmx";

	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.auto_top_up);
	        
			
	        ActionBar actionBar = getActionBar();
	        actionBar.setDisplayHomeAsUpEnabled(true);
	        actionBar.setTitle("Auto Top up");
	        
	        enterAmount = (LinearLayout)findViewById(R.id.auto_enter_amount);
	        enterAmount.setOnClickListener(enterListener);
	        
	        enterRecipient = (LinearLayout)findViewById(R.id.auto_enter_recipient);
	        enterRecipient.setOnClickListener(enterListener);
	        
	        enterMethod = (LinearLayout)findViewById(R.id.auto_enter_method);
	        enterMethod.setOnClickListener(enterListener);
	        
	        enterRecurrence = (LinearLayout)findViewById(R.id.auto_enter_recurrence);
	        enterRecurrence.setOnClickListener(enterListener);
	        
	        paymentAmountField = (TextView)findViewById(R.id.auto_top_up_amount);
	        recipientField = (TextView)findViewById(R.id.auto_top_up_recipient);
	        methodField = (TextView)findViewById(R.id.auto_top_up_method);
	        recurrenceField = (TextView)findViewById(R.id.auto_top_up_recurrence);
	        
	        makePaymentButton = (Button)findViewById(R.id.auto_top_up_make_payment);
	        makePaymentButton.setOnClickListener(makePayment);
	        
	        Random rgen = new Random();
	        randomId = 300000 + rgen.nextInt();
	        
	        myApp = (MyApplication)getApplication();    
	        String paymentNames = "";
	        for(int i = 0; i < myApp.accountsSize(); i++) {
	        	paymentNames = paymentNames + myApp.paymentNames().get(i) + " ";
	        }
	        Toast.makeText(getApplicationContext(), paymentNames, Toast.LENGTH_LONG).show();
	        
	        if(!myApp.isLogin) showDialog(LOGIN_ID);
	 }
	 
	 private OnClickListener enterListener = new OnClickListener() {
		 public void onClick(View v) {
			 if(enterAmount.getId() == ((LinearLayout)v).getId()) {
				 showDialog(AMOUNT_ID);
			 } else if(enterRecipient.getId() == ((LinearLayout)v).getId()) {
				 showDialog(RECIPIENT_ID);
			 } else if(enterMethod.getId() == ((LinearLayout)v).getId()) {
				 showDialog(METHOD_ID);
			 } else if(enterRecurrence.getId() == ((LinearLayout)v).getId()) {
				 showDialog(RECURRENCE_ID);
			 }
		 }	 
	 };
	 
	 protected Dialog onCreateDialog(int id) {
	    Dialog dialog;
	    AlertDialog alert;
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    switch(id) {
	    case AMOUNT_ID:
	    	builder.setTitle("Enter a Top up Amount");
	    	final EditText input = new EditText(this);
	    	input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
	    	input.setHint("Amount in TTD");
	    	builder.setView(input);

	    	builder.setPositiveButton("Set Top up Amount", new DialogInterface.OnClickListener() {
		    	public void onClick(DialogInterface dialog, int whichButton) {
		    		try {
		    			String value = input.getText().toString();
				    	topupAmount = (double)((int)(Double.parseDouble(value)*100))/100;
						paymentAmountField.setText("TTD " + (CharSequence)Double.toString(topupAmount));
		    		} catch(Exception ex) {}
				}
	    	});
	    	alert = builder.create();
	    	return alert;
	    case RECIPIENT_ID:
	    	builder.setTitle("Select Recipient");
	    	final LinearLayout recipientOptions = new LinearLayout(this);
	    	recipientOptions.setOrientation(LinearLayout.HORIZONTAL);
	    	recipientIsMe = new CheckBox(this);
	    	recipientIsMe.setText("Me    ");
	    	recipientIsMe.setOnClickListener(onCheckboxClicked);
	    	final ImageView selectFromContacts = new ImageView(this);
	    	selectFromContacts.setImageResource(R.drawable.ic_launcher_shortcut_contact_48);
	    	selectFromContacts.setClickable(true);
	    	selectFromContacts.setOnClickListener(selectContactListener);
	    	recipientDialogField = new EditText(this);
	    	recipientDialogField.setInputType(InputType.TYPE_CLASS_PHONE);
	    	recipientDialogField.setHint("Phone number");
	    	recipientDialogField.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
	    	recipientOptions.addView(recipientIsMe);
	    	recipientOptions.addView(recipientDialogField);
	    	recipientOptions.addView(selectFromContacts);
	    	builder.setView(recipientOptions);
	    	builder.setPositiveButton("Set Recipient", new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog, int whichButton) {
			    	String value = recipientDialogField.getText().toString();
			    	if(value.charAt(0) != '1') {
			    		value = "1868" + value;
			    	}
			    	recipient = Long.parseLong(value);
					recipientField.setText((CharSequence)Long.toString(recipient));			    	  
				}
		    });
	    	
	    	alert = builder.create();
	    	return alert;
	    case METHOD_ID:
	    	ArrayList<String> paymentMethodsList = ((MyApplication)getApplication()).paymentNames();
	    	final CharSequence[] items;
	    	items = new CharSequence[paymentMethodsList.size()];
	    	for(int i = 0; i < paymentMethodsList.size(); i++){
		    	items[i] = (CharSequence)paymentMethodsList.get(i);
	    	}

	    	builder.setTitle("Choose a payment method");
	    	builder.setItems(items, new DialogInterface.OnClickListener() {
	    	    public void onClick(DialogInterface dialog, int item) {
	    	    	methodField.setText(items[item]);
	    	    	paymentMethodItem = item;
	    	    	if(items[item].equals("PayPal")) {
	    	    		if(!paypalInitialized) {
	    	    			initPayPal();
	    	    			paypalInitialized = true;
	    	    		}
	    	    		makePaymentButton.setVisibility(View.INVISIBLE);
	    	    		launchPayPalButton.setVisibility(View.VISIBLE);
	    	    	} else {
	    	    		makePaymentButton.setVisibility(View.VISIBLE);
	    	    		launchPayPalButton.setVisibility(View.INVISIBLE);
	    	    	}
	    	    }
	    	});
	    	alert = builder.create();
	    	return alert;
	    case RECURRENCE_ID:
	    	final CharSequence[] recurrences = {"Daily", "Weekly", "Monthly" };
	    	builder.setTitle("Choose a recurrence");
	    	builder.setItems(recurrences, new DialogInterface.OnClickListener() {
	    	    public void onClick(DialogInterface dialog, int item) {
	    	    	recurrenceField.setText(recurrences[item]);
	    	    	recurrence = item;
	    	    }
	    	});
	    	alert = builder.create();
	    	return alert;
	    case LOGIN_ID:
	    	builder.setCancelable(false);
	    	builder.setTitle("Login");
	    	final LinearLayout layout = new LinearLayout(this);
	    	layout.setOrientation(LinearLayout.VERTICAL);
	    	final EditText userField = new EditText(this);
	    	userField.setInputType(InputType.TYPE_CLASS_TEXT);
	    	userField.setHint("Username");
	    	layout.addView(userField);
	    	final EditText passwordField = new EditText(this);
	    	passwordField.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
	    	passwordField.setHint("Password");
	    	layout.addView(passwordField);
	    	builder.setView(layout);
	    	builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
		    	public void onClick(DialogInterface dialog, int whichButton) {
			    	String username = userField.getText().toString();
			    	String password = userField.getText().toString();
			    	if(loginSuccessful(username,password)) {
			    		myApp.isLogin = true;
			    	} else {
			    		Toast.makeText(getApplicationContext(), "Incorrect username or password" , Toast.LENGTH_LONG).show();
			    	}
				}
	    	});
	    	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog, int whichButton) {
	    			Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		 			startActivity(intent);		    	  
				}
		    });
	    	builder.setNeutralButton("Register", new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog, int whichButton) {
	    			Intent intent = new Intent(getApplicationContext(), Register.class);
		 			startActivity(intent);		    	  
				}
		    });
	    	alert = builder.create();
	    	return alert;
	    default:
	        dialog = null;
	    }
	    return dialog;
	}
	 
	 private boolean loginSuccessful(String username, String password) {
		 return true;
	 }
	 
	 @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	     MenuInflater inflater = getMenuInflater();
	     inflater.inflate(R.menu.auto_top_up, menu);
	     return true;
	 }
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	     switch (item.getItemId()) {
	         case android.R.id.home:
	             // app icon in action bar clicked; go home
	             Intent intent = new Intent(this, TopUp.class);
	             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	             startActivity(intent);
	             return true;
	         case R.id.home_action:
	 			Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
	 			startActivity(intent2);
	         default:
	             return super.onOptionsItemSelected(item);
	     }
	 }
	
	 private void initPayPal() {
		PayPal pp = PayPal.getInstance();
		if (pp == null) {
		    try {
		        pp = PayPal.initWithAppID(getApplicationContext(), paypalAppId, server);
		    } catch (IllegalStateException e) {
		        throw new RuntimeException(e);
		    }
		    pp.setLanguage("en_US"); 
		    pp.setShippingEnabled(false);
		}
     	launchPayPalButton = pp.getCheckoutButton(this, PayPal.BUTTON_194x37, CheckoutButton.TEXT_PAY);
     	RelativeLayout buttonLayout = (RelativeLayout)findViewById(R.id.top_up_button_layout);
     	
     	buttonLayout.addView(launchPayPalButton);
     	launchPayPalButton.setVisibility(View.INVISIBLE);
     	launchPayPalButton.setOnClickListener(makePayPalPayment);
	 }
	 
	 private OnClickListener makePayPalPayment = new OnClickListener() { 
    	public void onClick(View v) {
    		PayPalPayment newPayment = new PayPalPayment();
    		newPayment.setSubtotal(new BigDecimal(topupAmount));
    		newPayment.setCurrencyType("USD");
    		newPayment.setRecipient("my@email.com"); // Merchant email address.
    		newPayment.setMerchantName("My Company");
    		Intent paypalIntent = PayPal.getInstance().checkout(newPayment, getApplicationContext());
    		startActivityForResult(paypalIntent, 1);
    	}
    };	 

	 private OnClickListener onCheckboxClicked = new OnClickListener() {
		 public void onClick(View v) {
			 if(recipientIsMe.isChecked()) {
				 try {
					 TelephonyManager telephonyManager = 
					 (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);

					 String phoneNumber = telephonyManager.getLine1Number();
					 operator = telephonyManager.getNetworkOperatorName().toUpperCase(); 
					 recipientDialogField.setText(phoneNumber, TextView.BufferType.EDITABLE);
				 } catch(Exception ex) {
					 Toast.makeText(getApplicationContext(), 
							        "We couldn't find your phone number. Please enter it in manually." , 
							        Toast.LENGTH_LONG).show();
				 }
			 } else {
				 recipientDialogField.setText("",TextView.BufferType.EDITABLE);
			 }
		 }	 
	 };
	 
	 private OnClickListener selectContactListener = new OnClickListener() { 
	    	public void onClick(View v) {
	    		try {
	    			Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
	    			startActivityForResult(intent,PICK_CONTACT);
	    		} catch(Exception ex) {
	    			Toast.makeText(getApplicationContext(), 
	    					       "We couldn't access your phone book. Please enter the phone number manually.", 
	    					       Toast.LENGTH_LONG).show();
	    		}
	    	}
	    };	
	 
	    @Override
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
		        		 recipientIsMe.setChecked(false);
		        		 recipientDialogField.setText(number, TextView.BufferType.EDITABLE);
		        	 }
		         }
		     }
		 } else if(requestCode == 1) {
			 if(resultCode == RESULT_OK) {
				 Toast.makeText(getApplicationContext(), data.getStringExtra(PayPalActivity.EXTRA_PAY_KEY), Toast.LENGTH_LONG).show();
			 } else if(resultCode == RESULT_CANCELED) {
				 Toast.makeText(getApplicationContext(), "Canceled!", Toast.LENGTH_LONG).show();
			 } else if(resultCode == PayPalActivity.RESULT_FAILURE) {
				 Toast.makeText(getApplicationContext(), PayPalActivity.EXTRA_ERROR_MESSAGE, Toast.LENGTH_LONG).show();
			 }
		 }
	}
	    
	 private OnClickListener makePayment = new OnClickListener() { 
	    	public void onClick(View v) {
	    		try {
	    			String phone = phoneNumberField.getText().toString();
	    			String amount = amountField.getText().toString();
	    			if(phone.length() == 7) {
	    				phone = "1868" + phone;
	    			}
	    			long phoneNumber = Long.parseLong(phone);
	    			int paymentAmount = Integer.parseInt(amount);
	    			Toast.makeText(getApplicationContext(), "Topping up $" + amount + " to " + phone, Toast.LENGTH_LONG).show();
	    			String paymentMethod = ""; //Change this to be an encoding of credit card info
	    			String error = paymentSuccessful(phoneNumber,paymentAmount,paymentMethod);
	    			if(error.equals("")) {
		    			Toast.makeText(getApplicationContext(), 
		    					       "The top up of $" + amount + " to the phone number " 
		    			               + phone + " was successful.", Toast.LENGTH_LONG).show();
	    			} else {
	    				Toast.makeText(getApplicationContext(), 
	    					       error, Toast.LENGTH_LONG).show();
	    			}
	    		} catch(Exception ex) {
	    			Toast.makeText(getApplicationContext(), "There was an error.", Toast.LENGTH_LONG).show();
	    		}
	    	}
	    };
	    
	 private String paymentSuccessful(long phone, int amount, String paymentMethod) {
		 SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
		 Request.addProperty("msisdn", phone);
		 Request.addProperty("mChekReferenceId", Integer.toString(randomId));
		 Request.addProperty("msisdnOperator", operator);
		 Request.addProperty("Amount", (double)amount);
		 Request.addProperty("AccountType", "C");
		 Request.addProperty("KeyValues", "");
		 SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		 soapEnvelope.dotNet = true;
		 soapEnvelope.setOutputSoapObject(Request);
		 AndroidHttpTransport aht = new AndroidHttpTransport(URL);
		 try {
			 aht.call(SOAP_ACTION, soapEnvelope);
			 SoapPrimitive resultString = (SoapPrimitive)soapEnvelope.getResponse();
			 CharSequence result = (CharSequence)resultString;
			 return (String) result;
		 } catch(Exception e) {
			 Toast.makeText(getApplicationContext(), "SOAP failed", Toast.LENGTH_LONG).show();
			 e.printStackTrace();
			 return "Failed Top up";
		 }
	 }
}
