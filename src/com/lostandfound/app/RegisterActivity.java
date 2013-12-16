package com.lostandfound.app;

import java.io.PrintWriter;
import java.io.StringWriter;

import exception.ExceptionHandler;
import entities.CreateEntity;

import util.ProfileHandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.view.View;

public class RegisterActivity extends Activity {
	
	private StringWriter sw = new StringWriter();
	private PrintWriter err = new PrintWriter(sw);

	private EditText emailId;
	
	private EditText usernameId;
	private EditText passwordId;
	private EditText confirmId;
	
	private Button registerButton;
	private Button gotoLoginButton;
	private Intent intent;

	private CreateEntity create;
	
	private RegisterAttempt register;
	
	private String USER_EXISTS = new String("Username already Exists!");
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		emailId = (EditText)findViewById(R.id.email);
		
		usernameId = (EditText)findViewById(R.id.username);
		passwordId = (EditText)findViewById(R.id.password);
		confirmId = (EditText)findViewById(R.id.confirm);
		
		registerButton = (Button)findViewById(R.id.registerButton);
		gotoLoginButton = (Button)findViewById(R.id.gotoLoginButton);
		
		registerButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
/* */
				String email = new String(emailId.getText().toString());
				
				String username = new String(usernameId.getText().toString());
				String password = new String(passwordId.getText().toString());
				String confirm = new String(confirmId.getText().toString());
				
				// Regular Expression pattern verifies username is alphanumeric
				String regex = new String("^[a-zA-Z0-9]*$");
				
				try {
					// If username is not alphanumeric throws Exception
					if (!username.matches(regex)) {
						String err = new String("Invalid Username Entry");
						err += " must be alphanumeric!";
						
						throw new ExceptionHandler(0, err);
					}
					
					// If Password is too short throws an Exception
					if (password.length() < 5) {
						String err = new String("Password is too short!");
						throw new ExceptionHandler(2, err);
					}
					
					// If Passwords entered do not match an Exception is thrown
					if (password.compareToIgnoreCase(confirm) != 0) {
						String err = new String("Passwords do not match!");
						throw new ExceptionHandler(1, err);
					}

					register = new RegisterAttempt(email, username, password);
					register.execute();
					
					create = new ProfileHandler();
					try {
						String returned = new String(register.get());
						
						if (returned.compareToIgnoreCase(USER_EXISTS) != 0) {
							intent = new Intent(getApplicationContext(), MainMenuActivity.class);
							startActivity(intent);
							finish();
						} 
						
					} catch (Exception e) {
						Toast.makeText(getApplicationContext(), 
								ExceptionHandler.networkError, Toast.LENGTH_LONG).show();
					}
		
				} catch (ExceptionHandler e) {
					
					Toast.makeText(getApplicationContext(), 
						e.exceptionHandle(), Toast.LENGTH_LONG).show();
				}
			} 
		});
		
		gotoLoginButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				intent = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
	
	public class RegisterAttempt extends AsyncTask<String, String, String> {
		
		private ProgressDialog pDialog = new ProgressDialog(RegisterActivity.this);

		private String email = new String();
		private String username = new String();
		private String password = new String();

		public RegisterAttempt(String email, String username, String password) {
			
			this.username = username;
			this.password = password;
			this.email = email;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog.setTitle("Creating Account");
			pDialog.setMessage("Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		protected String doInBackground(String... args) {
			
			String returnString = new String();
			
			try {
				create = new ProfileHandler();
				create.createBasicUserProfile(email, username, password);
				
				returnString = create.saveUserProfile();
				
			} catch (Exception e) {
				
				e.printStackTrace(err);
				returnString = sw.toString();
			}

			return returnString;
		}
		
		@Override
		protected void onPostExecute(String file_url) {

			pDialog.cancel();
			
			if (file_url != null) {
				Toast.makeText(RegisterActivity.this, file_url, Toast.LENGTH_LONG).show();
			}
		}
	}

}
