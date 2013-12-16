package com.lostandfound.app;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ExecutionException;

import android.app.Activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

import util.ProfileHandler;
import entities.CreateEntity;
import exception.ExceptionHandler;

public class LoginActivity extends Activity {

	private final String INVALID = new String("Invalid Username or Password!");
	private final String VALID = new String("Welcome to LostAndFound!");
	
	private EditText loginUsername;
	private EditText loginPassword;
	
	private Button gotoRegister;
	private Button loginButton;
	
	private LoginAttempt login;
	private Intent intent;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		loginUsername = (EditText)findViewById(R.id.loginUsername);
		loginPassword = (EditText)findViewById(R.id.loginPassword);
		
		gotoRegister = (Button)findViewById(R.id.goToRegisterButton);
		loginButton = (Button)findViewById(R.id.loginButton);
		
		setButtonListeners();
	}

	private void setButtonListeners() {
		
		// Sends the Focus to the RegisterActivity Screen
		gotoRegister.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				intent = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		// Attempts to login a user using a unigue username and password
		loginButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				String username = new String(loginUsername.getText().toString());
				String password = new String(loginPassword.getText().toString());
				login = new LoginAttempt(username, password);
				login.execute();
				
				try {
					String message = login.get();
					
					if (message.compareToIgnoreCase(VALID) == 0) {
						
						intent = new Intent(getApplicationContext(), MainMenuActivity.class);
						intent.putExtra("Login", "LoadUserData");
						intent.putExtra("username", username);
						startActivity(intent);
						finish();
						
					} else if (message.compareToIgnoreCase(INVALID) == 0) {
						// If Invalid response the Activity Screen stays here
						// This else if method does nothing
					} else {
						Toast.makeText(getApplicationContext(), 
							ExceptionHandler.networkError, Toast.LENGTH_LONG).show();
					}
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		});
	}

	class LoginAttempt extends AsyncTask<String, String, String> {
		
		private CreateEntity create;
		
		private StringWriter sw = new StringWriter();
		private PrintWriter err = new PrintWriter(sw);
		private String username = new String();
		private String password = new String();
		
		ProgressDialog pDialog = new ProgressDialog(LoginActivity.this);
		
		public LoginAttempt(String username, String password) {
			create = new ProfileHandler();
			this.username = username;
			this.password = password;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(LoginActivity.this);
			pDialog.setTitle("Logging In...");
			pDialog.setMessage("Please Wait");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String...args) {
			String returnString = new String();
			
			if (username.length() > 0 && password.length() > 0) {
				create.createBasicLoginProfile(username, password);
			
				try {
					returnString = create.tryLoginProfile();
				
				} catch (Exception e) {
					e.printStackTrace(err);
					returnString = sw.toString();
				}
				
			} else {
				returnString = INVALID;
			}
			
			return returnString;
		}
		
		@Override
		protected void onPostExecute(String message) {
			
			if (message != null) {
				pDialog.cancel();
			}
		}
	}
}
