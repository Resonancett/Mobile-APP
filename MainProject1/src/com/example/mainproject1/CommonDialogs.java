package com.example.mainproject1;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

public class CommonDialogs {
	private static final int FIRST_LOGIN = 0;
	private static final int WRONG_LOGIN = 1;
	private static final int PAYMENT_AMOUNT = 2;
	private static final int PHONE_NUMBER = 3;
	private static final int PAYMENT_METHOD = 4;
	private static final int CONFIRM_PAYMENT = 5;
	private static final int DISPLAY_RECEIPT = 6;
	
	private static final int PICK_CONTACT = 0;
	
	private static final int LOGIN_SUCCESSFUL = 0;
	private static final int LOGIN_FAIL = 1;
	private static final int LOGIN_CANCEL = 2;
	private static final int LOGIN_REGISTER = 3;
	
	private double topupAmount = 0;
	private long recipient = 0;
	private int paymentItem = 0;
	private boolean paymentConfirmed;
	
	private CheckBox recipientIsMe;
	private EditText recipientDialogField;
	private CharSequence[] paymentMethods;
	private ArrayList<String> receiptItems;
	
	private MyApplication myApp;
	private Activity activity;
	
	public void login(MyApplication ma, Activity act) {
		myApp = ma;
		if(!myApp.isLogin) {
			firstLoginDialog(act);
		}
	}
	
	public void firstLoginDialog(final Activity act) {
	    AlertDialog alert;
    	AlertDialog.Builder builder = new AlertDialog.Builder(act);
    	// Doesn't allow the Dialog to be cancelable, ensuring that the user cannot bypass
    	// the login screen
    	builder.setCancelable(false);
    	builder.setTitle("Login");
    	
    	// Constructs a vertical LinearLayout to contain the EditTexts for the username
    	// and password, adds EditTexts for the username and for the password to it, and
    	// sets the builder to display the LinearLayout
    	final LinearLayout layout = new LinearLayout(act);
    	layout.setOrientation(LinearLayout.VERTICAL);
    	final EditText userField = new EditText(act);
    	userField.setInputType(InputType.TYPE_CLASS_TEXT);
    	userField.setHint("Username");
    	layout.addView(userField);
    	final EditText passwordField = new EditText(act);
    	passwordField.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
    	passwordField.setHint("Password");
    	layout.addView(passwordField);
    	builder.setView(layout);
    	
    	// Sets the username and the password to be the entered username and password
    	// Sets the app to be logged in if the login is successful, and goes to the Login
    	// Failed Dialog otherwise.
    	builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
	    	public void onClick(DialogInterface dialog, int whichButton) {
		    	String username = userField.getText().toString();
		    	String password = passwordField.getText().toString();
		    	try {
					if(loginSuccessful(username,password)) {
						myApp.isLogin = true;
						myApp.user = username;
						myApp.pass = password;
					} else {
						wrongLoginDialog(act);
					}
				} catch (Exception e) {
					Toast.makeText(act.getApplicationContext(), "Could not verify username/password", Toast.LENGTH_LONG).show();
				}
			}
    	});
    	
    	// Returns home
    	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int whichButton) {
    			Intent intent = new Intent(act.getApplicationContext(), MainActivity.class);
	 			act.startActivity(intent);		    	  
			}
	    });
    	
    	// Goes to the registration screen.
    	builder.setNeutralButton("Register", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int whichButton) {
    			Intent intent = new Intent(act.getApplicationContext(), Register.class);
	 			act.startActivity(intent);		    	  
			}
	    });
    	alert = builder.create();
    	alert.show();
	}
	
	private void wrongLoginDialog(final Activity act) {
		AlertDialog alert;
    	AlertDialog.Builder builder = new AlertDialog.Builder(act);
    	// Doesn't allow the Dialog to be cancelable, ensuring that the user cannot bypass
    	// the login screen
    	builder.setCancelable(false);
    	builder.setTitle("Login");
    	
    	builder.setMessage("The username-password combination was incorrect. Try again, or register for an account.");
    	
    	// Constructs a vertical LinearLayout to contain the EditTexts for the username
    	// and password, adds EditTexts for the username and for the password to it, and
    	// sets the builder to display the LinearLayout
    	final LinearLayout layout1 = new LinearLayout(act);
    	layout1.setOrientation(LinearLayout.VERTICAL);
    	final EditText userField1 = new EditText(act);
    	userField1.setInputType(InputType.TYPE_CLASS_TEXT);
    	userField1.setHint("Username");
    	layout1.addView(userField1);
    	final EditText passwordField1 = new EditText(act);
    	passwordField1.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
    	passwordField1.setHint("Password");
    	layout1.addView(passwordField1);
    	builder.setView(layout1);
    	
    	// Sets the username and the password to be the entered username and password. 
    	// Sets the app to be logged in if the login is successful, and repeats the Login
    	// Failed Dialog otherwise.
    	builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
	    	public void onClick(DialogInterface dialog, int whichButton) {
		    	String username = userField1.getText().toString();
		    	String password = passwordField1.getText().toString();
		    	try {
					if(loginSuccessful(username,password)) {
						myApp.isLogin = true;
						myApp.user = username;
						myApp.pass = password;
					} else {
						wrongLoginDialog(act);
					}
				} catch (Exception e) {
					Toast.makeText(act.getApplicationContext(), "Could not verify username/password", Toast.LENGTH_LONG).show();
				}
			}
    	});
    	
    	// Returns home.
    	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int whichButton) {
    			Intent intent = new Intent(act.getApplicationContext(), MainActivity.class);
    			act.startActivity(intent);		    	  
			}
	    });
    	
    	// Goes to the registration view.
    	builder.setNeutralButton("Register", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int whichButton) {
    			Intent intent = new Intent(act.getApplicationContext(), Register.class);
    			act.startActivity(intent);		    	  
			}
	    });
    	alert = builder.create();
    	alert.show();
	}

	public void getPaymentAmount(Activity act) {
		getPaymentAmountDialog(act);
		Toast.makeText(act.getApplicationContext(), Double.toString(topupAmount), Toast.LENGTH_LONG).show();
	}
	
	private void getPaymentAmountDialog(final Activity act) {
		AlertDialog alert;
    	AlertDialog.Builder builder = new AlertDialog.Builder(act);
    	// Sets the title of the Dialog
    	builder.setTitle("Enter a Top up Amount");
    	
    	// Creates a new EditText, which will be the only component of the Dialog
    	final EditText input = new EditText(act);
    	
    	// Sets the type of the input of the EditText to be a decimal number.
    	input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
    	
    	// Sets the hint of the EditText
    	input.setHint("Amount in TTD");	    	
    	// Sets the builder to display the EditText
    	builder.setView(input);

    	/* Sets the positive button to set the amount, setting topUpAmount and 
    	 * updating the paymentAmountField to display the entered value. It's 
    	 * surrounded in a try-catch to avoid any errors caused by invalid inputs.
    	 */
    	builder.setPositiveButton("Set Top up Amount", new DialogInterface.OnClickListener() {
	    	public void onClick(DialogInterface dialog, int whichButton) {
	    		try {
	    			String value = input.getText().toString();
			    	topupAmount = (double)((int)(Double.parseDouble(value)*100))/100;
			    	TextView tv = (TextView) act.findViewById(R.id.top_up_amount);
			    	tv.setText("TTD " + Double.toString(topupAmount));
	    		} catch(Exception ex) {}
			}
    	});   	
    	
    	// Creates the AlertDialog from the builder, and returns it.
    	alert = builder.create();
    	alert.show();
	}
	
	public long getPhoneNumber(Activity act) {
		getPhoneNumberDialog(act);
		return recipient;
	}
	
	private void getPhoneNumberDialog(final Activity act) {
		activity = act;
		AlertDialog alert;
    	AlertDialog.Builder builder = new AlertDialog.Builder(activity);
    	builder.setTitle("Select Recipient");
    	
    	// Sets a horizontal LinearLayout to contain all the elements needed in the Dialog
    	final LinearLayout recipientOptions = new LinearLayout(activity);
    	recipientOptions.setOrientation(LinearLayout.HORIZONTAL);
    	
    	// Creates a CheckBox to check if the user has selected himself as the user.
    	recipientIsMe = new CheckBox(activity);
    	recipientIsMe.setText("Me    ");
    	
    	// Calls an OnClickListener when the CheckBox is clicked, which updates the 
    	// field next to it to display the phone number of the user, if possible.
    	recipientIsMe.setOnClickListener(onCheckboxClicked);
    	
    	// Constructs a clickable ImageView to serve as the Contacts Button, 
    	// choosing the appropriate resource from the drawables folder.
    	final ImageView selectFromContacts = new ImageView(activity);
    	selectFromContacts.setImageResource(R.drawable.ic_launcher_shortcut_contact_48);
    	selectFromContacts.setClickable(true);
    	
    	// Adds an OnClickListener to go through the sequence to enter Android's 
    	// contacts page, select the contact, and return the primary phone number
    	// associated with the contact, displaying it in the adjacent EditText.
    	selectFromContacts.setOnClickListener(selectContactListener);
    	
    	// Constructs an EditText to manually input a phone number.
    	recipientDialogField = new EditText(activity);
    	
    	// Specifies the type of the input and the hint.
    	recipientDialogField.setInputType(InputType.TYPE_CLASS_PHONE);
    	recipientDialogField.setHint("Phone number");
    	
    	// Instructs the EditText to fill in all the remaining space in the 
    	// Dialog after the CheckBox and the ImageView are displayed.
    	recipientDialogField.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
    	
    	// Adds the CheckBox, the EditText, and the ImageView to the LinearLayout
    	// in order.
    	recipientOptions.addView(recipientIsMe);
    	recipientOptions.addView(recipientDialogField);
    	recipientOptions.addView(selectFromContacts);
    	
    	// Sets the builder to display the LinearLayout as the display in the Builder. 
    	builder.setView(recipientOptions);
    	
    	// Sets the positive button to set the recipient variable and the recipient Field
    	// to the value in the EditText, which might have been populated from checking the 
    	// CheckBox or selecting a contact. Prepends "1868" to the phone number if the phone 
    	// number does not start with 1 (it's assumed that there are no seven digit phone 
    	// numbers in Trinidad that begin with the digit 1). 
    	builder.setPositiveButton("Set Recipient", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int whichButton) {
		    	String value = recipientDialogField.getText().toString();
		    	if(value.charAt(0) != '1') {
		    		value = "1868" + value;
		    	}
		    	recipient = Long.parseLong(value);
		    	TextView tv = (TextView)act.findViewById(R.id.top_up_recipient);
		    	tv.setText(Long.toString(recipient));
			}
	    });
    	alert = builder.create();
    	alert.show();
	}
	
	public int getPaymentMethodItem(CharSequence[] myItems, Activity act) {
		paymentMethods = myItems;
		getPaymentMethodDialog(act);
		return paymentItem;
	}
	
	private void getPaymentMethodDialog(final Activity act) {
		AlertDialog alert;
    	AlertDialog.Builder builder = new AlertDialog.Builder(act);
    	// Sets the title
    	builder.setTitle("Choose a payment method");
    	
    	// Sets the Dialog to display the payment options, as in a spinner.
    	builder.setItems(paymentMethods, new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int item) {
    	    	paymentItem = item;
    	    	TextView tv = (TextView)act.findViewById(R.id.top_up_method);
    	    	tv.setText(paymentMethods[item]);
    	    }
    	});
    	
    	alert = builder.create();
    	alert.show();
    
	}
	
	public boolean confirmPayment(ArrayList<String> items, Activity act) {
		paymentConfirmed = false;
		receiptItems = items;
		confirmPaymentDialog(act);
		return paymentConfirmed;
	}
	
	private void confirmPaymentDialog(final Activity act) {
		AlertDialog alert;
    	AlertDialog.Builder builder = new AlertDialog.Builder(act);
    	builder.setTitle("Enter your password for this payment");
    	LinearLayout ll = new LinearLayout(act);
    	ll.setOrientation(LinearLayout.VERTICAL);
    	ll.setPadding(10, 10, 10, 10);
    	for(int i = 0; i < receiptItems.size(); i++) {
    		TextView paymentInfoItem = new TextView(act);
    		paymentInfoItem.setText(receiptItems.get(i));
    		ll.addView(paymentInfoItem);
    	}
    	
    	final EditText passwordDialogField = new EditText(act);
    	
    	// Sets the type of the input of the EditText to be a password
    	passwordDialogField.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
    	
    	// Sets the hint of the EditText
    	passwordDialogField.setHint("Password");
    	ll.addView(passwordDialogField);
    	// Sets the builder to display the EditText
    	builder.setView(ll);

    	/* Sets the positive button to set the amount, setting topUpAmount and 
    	 * updating the paymentAmountField to display the entered value. It's 
    	 * surrounded in a try-catch to avoid any errors caused by invalid inputs.
    	 */
    	builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
	    	public void onClick(DialogInterface dialog, int whichButton) {
	    		try {
	    			if(passwordValid(passwordDialogField.getText().toString())) {
	    				
	    			} else {
	    			}
	    		} catch(Exception ex) {}
			}
    	});
    	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	    	public void onClick(DialogInterface dialog, int whichButton) {
	    		paymentConfirmed = false;
			}
    	});
    	
    	// Creates the AlertDialog from the builder, and returns it.
    	alert = builder.create();
    	alert.show();
	}
	
	private boolean passwordValid(String password) {
		return true;
	}
	
	public void displayReceipt(ArrayList<String> items, Activity act) {
		receiptItems = items;
		displayReceiptDialog(act);
	}
	
	private void displayReceiptDialog(final Activity act) {
		AlertDialog alert;
    	AlertDialog.Builder builder = new AlertDialog.Builder(act);
    	builder.setTitle("Payment was successful!");
    	LinearLayout linlayout = new LinearLayout(act);
    	linlayout.setOrientation(LinearLayout.VERTICAL);
    	linlayout.setPadding(10, 10, 10, 10);
    	for(int i = 0; i < receiptItems.size(); i++) {
    		TextView paymentInfoItem = new TextView(act);
    		paymentInfoItem.setText(receiptItems.get(i));
    		linlayout.addView(paymentInfoItem);
    	}

    	/* Sets the positive button to set the amount, setting topUpAmount and 
    	 * updating the paymentAmountField to display the entered value. It's 
    	 * surrounded in a try-catch to avoid any errors caused by invalid inputs.
    	 */
    	builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
	    	public void onClick(DialogInterface dialog, int whichButton) {
	    		
			}
    	});   	
    	
    	// Creates the AlertDialog from the builder, and returns it.
    	alert = builder.create();
    	alert.show();
	}
	
    /* Responds to the CheckBox for recipientIsMe being clicked. */
	 private OnClickListener onCheckboxClicked = new OnClickListener() {
		 public void onClick(View v) {
			 /* If the CheckBox is checked, get the user's phone number and display it in the field */
			 if(recipientIsMe.isChecked()) {
				 try {
					 /* Gets the phone number and displays it. This method doesn't work on all phones. 
					  * In fact, there is no method to consistently get a phone number from all Android 
					  * devices with a phone number.
					  */
					 TelephonyManager telephonyManager = 
					 (TelephonyManager)activity.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);

					 String phoneNumber = telephonyManager.getLine1Number();
					 //operator = telephonyManager.getNetworkOperatorName().toUpperCase(); 
					 recipientDialogField.setText(phoneNumber, TextView.BufferType.EDITABLE);
				 } catch(Exception ex) {
					 /* Makes a Toast saying that the user's phone number couldn't be accessed */
					 Toast.makeText(activity.getApplicationContext(), 
							        "We couldn't find your phone number. Please enter it in manually." , 
							        Toast.LENGTH_LONG).show();
				 }
			 } else {
				 /* If the CheckBox is not checked, empty the field. */
				 recipientDialogField.setText("",TextView.BufferType.EDITABLE);
			 }
		 }	 
	 };
	 
	 /* Responds when the ImageView for selecting contacts is clicked */
	 private OnClickListener selectContactListener = new OnClickListener() { 
    	public void onClick(View v) {
    		try {
    			/* Creates an intent to start the Activity to pick a contact */
    			Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
    			activity.startActivityForResult(intent,PICK_CONTACT);
    		} catch(Exception ex) {
    			/* Makes a Toast if the contacts list couldn't be accessed */
    			Toast.makeText(activity.getApplicationContext(), 
    					       "We couldn't access your phone book. Please enter the phone number manually.", 
    					       Toast.LENGTH_LONG).show();
    		}
    	}
    };	
	
	/* Collects and deals with results returned from the Contacts and PayPal activities. */
	protected void onActivityResult(int requestCode, int resultCode,
	         Intent data) {
		/* Standard procedure for retrieving the phone number from the Contact result */
		 if (requestCode == PICK_CONTACT) {
		     if (resultCode == activity.RESULT_OK) {
		    	 Uri contactData = data.getData();
		         Cursor c = activity.getApplicationContext().getContentResolver().query(contactData, null, null, null, null);
		         if (c.moveToFirst()) {
		        	 String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
		        	 String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
		        	 if(hasPhone.equals("1")) {
		        		 Cursor phones = activity.getContentResolver().query( 
		                       ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, 
		                       ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id, 
		                       null, null);
		        		 phones.moveToFirst();
		        		 String cNumber = phones.getString(phones.getColumnIndex("data1"));
		        		 String number = "";
		        		 
		        		 // Removes all non-numerical characters from the phone number
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
		     
		 /* Returns a Toast result based on the result of the payment. */
		 }
	}
	
	 /**Checks if the username password combination is valid.
	 * @param username entered by the user
	 * @param password entered by the user
	 * @return true if the user/pass combination is valid, false otherwise.
	 * 
	 * TODO: Implement this to check user/password keys against a database.
	 * Currently it just returns true if the username is the same string as
	 * the password.
	 */
	private boolean loginSuccessful(String username, String password) {
		return username.equals(password);    
	 }
}
