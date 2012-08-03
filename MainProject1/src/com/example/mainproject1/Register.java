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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

/* import com.google.gdata.client.authn.oauth.*;
import com.google.gdata.client.spreadsheet.*;
import com.google.gdata.data.*;
import com.google.gdata.data.batch.*;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.*;
import com.google.gdata.client.authn.oauth.GoogleOAuthHelper;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gdata.client.authn.oauth.OAuthRsaSha1Signer;
import com.google.gdata.client.authn.oauth.OAuthSigner;
import com.google.gdata.data.Link;
import com.google.gdata.data.batch.BatchOperationType;
import com.google.gdata.data.batch.BatchStatus;
import com.google.gdata.data.batch.BatchUtils; */

import java.io.IOException;
import java.util.*;
import java.net.*;


public class Register extends Activity {
	Button home, register;
	EditText usernameField, passwordField;
	String username;
	String password;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.register);
	        
	        ActionBar actionBar = getActionBar();
	        actionBar.setDisplayHomeAsUpEnabled(true);
	        actionBar.setTitle("Register");

	        usernameField = (EditText)findViewById(R.id.username);
	        passwordField = (EditText)findViewById(R.id.password);
	        
	        register = (Button)findViewById(R.id.register);
	        register.setOnClickListener(registerListener);
	 }
	
	 
	 @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	     MenuInflater inflater = getMenuInflater();
	     inflater.inflate(R.menu.register, menu);
	     return true;
	 }
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	     switch (item.getItemId()) {
	         case R.id.home_action:
	 			Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
	 			startActivity(intent2);
	         default:
	             return super.onOptionsItemSelected(item);
	     }
	 }
	 
	 private OnClickListener registerListener = new OnClickListener() {
		 public void onClick(View v) {
			username = usernameField.getText().toString();
			password = passwordField.getText().toString();
			if(addedAccount(username,password)) {
				Toast.makeText(getApplicationContext(), "Added account", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(), "Could not add account", Toast.LENGTH_LONG).show();
			}
			/*try {
				SpreadsheetService service =
				        new SpreadsheetService("MySpreadsheetIntegration-v1");

				    // TODO: Authorize the service object for a specific user (see other sections)
			    String USERNAME = "prithvi2206@gmail.com";
			    String PASSWORD = "Sao123456";

			    service.setUserCredentials(USERNAME, PASSWORD);
				    // Define the URL to request.  This should never change.
				    URL SPREADSHEET_FEED_URL = new URL(
				        "https://spreadsheets.google.com/feeds/spreadsheets/private/full");

				    // Make a request to the API and get all spreadsheets.
				    SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL,
				        SpreadsheetFeed.class);
				    List<SpreadsheetEntry> spreadsheets = feed.getEntries();

				    if (spreadsheets.size() == 0) {
				      // TODO: There were no spreadsheets, act accordingly.
				    }

				    // TODO: Choose a spreadsheet more intelligently based on your
				    // app's needs.
				    String spreadsheetName = "";
				    int i = -1;
				    while(spreadsheetName != "Login Data") {
				    	i++;
				    	spreadsheetName = spreadsheets.get(i).getTitle().getPlainText();
				    }
				    SpreadsheetEntry spreadsheet = spreadsheets.get(i);
				    Toast.makeText(getApplicationContext(), spreadsheetName, Toast.LENGTH_LONG).show();
				    // Get the first worksheet of the first spreadsheet.
				    // TODO: Choose a worksheet more intelligently based on your
				    // app's needs.
				    WorksheetFeed worksheetFeed = service.getFeed(
				        spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
				    List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
				    WorksheetEntry worksheet = worksheets.get(0);

				    // Fetch the list feed of the worksheet.
				    URL listFeedUrl = worksheet.getListFeedUrl();
				    ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);

				    // TODO: Choose a row more intelligently based on your app's needs.
				    ListEntry row = listFeed.getEntries().get(0);

				    // Update the row's data.
				    row.getCustomElements().setValueLocal("Username", username);
				    row.getCustomElements().setValueLocal("Password", password);

				    // Save the row using the API.
				    row.update();
			} catch(Exception e) {
				Toast.makeText(getApplicationContext(), "Couldn't write the name to the database", Toast.LENGTH_LONG).show();
			}*/
		 }	 
	 };
	 
	 private boolean addedAccount(String username, String password) {
		 return true;
	 }
}
