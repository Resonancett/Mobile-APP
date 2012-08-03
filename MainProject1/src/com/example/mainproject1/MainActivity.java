package com.example.mainproject1;

import android.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	private Button topUp, pay, bill, business, 
	               person, acct, bank, 
	               history, edit;
	private EditText userField;
	private EditText passField;
	private Dialog dialog;
	
    /* Adds the Mobile Wallet to the list of accounts, to initialize it,
     * and initializes the Buttons */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        initializeButtons();
        ((MyApplication)getApplication()).initiateAccounts();
        
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Home");
        //login();
    }

    /* Initializes all the buttons and connects them to the listener */
    private void initializeButtons() {
    	topUp = (Button)findViewById(R.id.top_up); topUp.setOnClickListener(buttonListener);
    	pay = (Button)findViewById(R.id.pay); pay.setOnClickListener(buttonListener);
    	bill = (Button)findViewById(R.id.bill_pay); bill.setOnClickListener(buttonListener);
    	business = (Button)findViewById(R.id.otc); business.setOnClickListener(buttonListener);
    	person = (Button)findViewById(R.id.p2p); person.setOnClickListener(buttonListener);
    	acct = (Button)findViewById(R.id.my_acct); acct.setOnClickListener(buttonListener);
    	bank = (Button)findViewById(R.id.bank_accounts); bank.setOnClickListener(buttonListener);
    	history = (Button)findViewById(R.id.history); history.setOnClickListener(buttonListener);
    	edit = (Button)findViewById(R.id.edit_acct); edit.setOnClickListener(buttonListener);
    }

    /* Displays a Dialog to prompt the user for credentials if he is not 
     * logged in. Then sets the user's credentials according to the input
     * in the Dialog.
     */
	@SuppressWarnings("deprecation")
	public void login() {
		if(!((MyApplication)getApplication()).isLogin) {
			showDialog(0);
			dialog = new Dialog(getApplicationContext());
			dialog.setContentView(R.layout.log_in_dialog);
			dialog.setTitle("Login");
 
			userField = (EditText) dialog.findViewById(R.id.user);
			userField.setText("Android custom dialog example!",TextView.BufferType.EDITABLE);

			passField = (EditText) dialog.findViewById(R.id.pass);

			Button login = (Button) dialog.findViewById(R.id.login);
			Button register = (Button) dialog.findViewById(R.id.register);
			login.setOnClickListener(loginListener);
			register.setOnClickListener(registerListener);
			dialog.setContentView(R.layout.log_in_dialog);
 
			dialog.show();
		}
	}
	
	/* By cases. 
	 * If the selected Button is either "Pay" or "Account", the app shows/hides
	 * the relevant sub-Buttons.
	 * Otherwise, an Intent carries the user to the relevant Activity. 
	 */
	 private OnClickListener buttonListener = new OnClickListener() { 
    	public void onClick(View v) {
    		if(((Button)v).getId() == topUp.getId()) {
				Intent intent = new Intent(getApplicationContext(),TopUp.class);
				startActivity(intent);
    		} else if(((Button)v).getId() == pay.getId()) {
    			bill.setVisibility(View.VISIBLE);
    			business.setVisibility(View.VISIBLE);
    			person.setVisibility(View.VISIBLE);
    			bank.setVisibility(View.INVISIBLE);
    			history.setVisibility(View.INVISIBLE);
    			edit.setVisibility(View.INVISIBLE);
    		}else if(((Button)v).getId() == bill.getId()) {
				Intent intent = new Intent(getApplicationContext(),BillPayment.class);
				startActivity(intent);	
    		}else if(((Button)v).getId() == business.getId()) {
				Intent intent = new Intent(getApplicationContext(),OTC.class);
				startActivity(intent);
    		}else if(((Button)v).getId() == person.getId()) {
				Intent intent = new Intent(getApplicationContext(),P2P.class);
				startActivity(intent);
    		}else if(((Button)v).getId() == acct.getId()) {
    			bill.setVisibility(View.INVISIBLE);
    			business.setVisibility(View.INVISIBLE);
    			person.setVisibility(View.INVISIBLE);
    			bank.setVisibility(View.VISIBLE);
    			history.setVisibility(View.VISIBLE);
    			edit.setVisibility(View.VISIBLE);
    		}else if(((Button)v).getId() == bank.getId()) {
				Intent intent = new Intent(getApplicationContext(),Accounts.class);
				startActivity(intent);
    		}else if(((Button)v).getId() == history.getId()) {
    			Intent intent = new Intent(getApplicationContext(),History.class);
				startActivity(intent);
    		}else if(((Button)v).getId() == edit.getId()) {
    			Intent intent = new Intent(getApplicationContext(),EditAccount.class);
				startActivity(intent);
    		}
    	}
	};	
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
			switch(id) {
		    case 0:
		    	Context mContext = getApplicationContext();
		    	dialog = new Dialog(mContext);
				Button login = (Button) dialog.findViewById(R.id.login);
				Button register = (Button) dialog.findViewById(R.id.register);
				login.setOnClickListener(loginListener);
				register.setOnClickListener(registerListener);

		    	dialog.setContentView(R.layout.log_in_dialog);
		    	dialog.setTitle("Login");

		        break;
		    }
		return dialog;
	 }

	 private OnClickListener loginListener = new OnClickListener() {
		 public void onClick(View v) {
			 if(validLogin(userField.getText().toString(),passField.getText().toString())) {
				 ((MyApplication)getApplication()).user = userField.getText().toString();
				 ((MyApplication)getApplication()).pass = passField.getText().toString();
				 ((MyApplication)getApplication()).isLogin = true;
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
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}

