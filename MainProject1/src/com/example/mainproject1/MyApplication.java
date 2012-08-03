package com.example.mainproject1;

import java.util.ArrayList;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MyApplication extends Application {
	public boolean isLogin = false;
	public String user = "";
	public String pass = null;
	public ArrayList<PaymentMethod> accounts;
	public ArrayList<Transaction> transactions;
	public int preferredPaymentMethod;
	
	private EditText userField;
	private EditText passField;
	private Dialog dialog;
	private boolean accountsInitiated = false;
	
	public static final String appId = "0";
	
	/* Adds PayPal and the Mobile Wallet to the list of accounts
	 * 
	 */
	public void initiateAccounts() {
		if(!accountsInitiated) {
			accounts = new ArrayList<PaymentMethod>();
			PaymentMethod wallet = new PaymentMethod();
			wallet.setName("Mobile Wallet");
			wallet.setInfo("0");
			PaymentMethod paypal = new PaymentMethod();
			paypal.setName("PayPal");
			paypal.setInfo("1");
			accounts.add(wallet);
			accounts.add(paypal);
			preferredPaymentMethod = 0;
			accountsInitiated = true;
		}
	}
	
	/* Constructs a transaction from the type, amount, and recipient 
	 * given as parameters.
	 */
	public void addTransaction(String type, double amount, String recipient) {
		Transaction transaction = new Transaction();
		transaction.amount = amount;
		transaction.type = type;
		transaction.recipient = recipient;
		transactions.add(transaction);
	}
	
	public void addAccount(PaymentMethod newMethod) {
		accounts.add(newMethod);
	}
	
	public PaymentMethod accountAt(int i) {
		return accounts.get(i);
	}
	
	public int accountsSize() {
		return accounts.size();
	}
	
	public void clearAccounts() {
		accounts.clear();
	}
	
	/* Returns an ArrayList of the names of all the payments in the 
	 * accounts ArrayList.
	 */
	public ArrayList<String> paymentNames() {
		ArrayList<String> paymentNames = new ArrayList<String>();
		for(int i = 0; i < accounts.size(); i++) {
			paymentNames.add(accounts.get(i).getName());
		}
		return paymentNames;
	}
	
	public void login() {
		if(!isLogin) {
			dialog = new Dialog(getApplicationContext());
			dialog.setContentView(R.layout.log_in_dialog);
			dialog.setTitle("Login");
 
			// set the custom dialog components - text, image and button
			userField = (EditText) dialog.findViewById(R.id.user);
			userField.setText("Android custom dialog example!",TextView.BufferType.EDITABLE);

			passField = (EditText) dialog.findViewById(R.id.pass);

			Button login = (Button) dialog.findViewById(R.id.login);
			Button register = (Button) dialog.findViewById(R.id.register);
			// if button is clicked, close the custom dialog
			login.setOnClickListener(loginListener);
			register.setOnClickListener(registerListener);
 
			dialog.show();
		}
	}
	 private OnClickListener loginListener = new OnClickListener() {
		 public void onClick(View v) {
			 if(validLogin(userField.getText().toString(),passField.getText().toString())) {
				 user = userField.getText().toString();
				 pass = passField.getText().toString();
				 isLogin = true;
				 dialog.dismiss();
			 }
		 }	 
	 };
	 
	 private OnClickListener registerListener = new OnClickListener() {
		 public void onClick(View v) {
			Intent intent = new Intent(getApplicationContext(),Register.class);
			startActivity(intent);
		 }	 
	 };
	 
	 private boolean validLogin(String user, String pass) {
		 return true;
	 }
	
	
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
			switch(id) {
		    case 0:
		    	Context mContext = getApplicationContext();
		    	dialog = new Dialog(mContext);

		    	dialog.setContentView(R.layout.log_in_dialog);
		    	dialog.setTitle("Login");

		        break;
		    }
		return dialog;
	 }
}
