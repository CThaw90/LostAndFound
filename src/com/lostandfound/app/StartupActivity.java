package com.lostandfound.app;

import java.util.concurrent.ExecutionException;

import android.app.Activity;

import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;

import util.DatabaseHandler;

import ws.ReadInterface;

public class StartupActivity extends Activity {
	
	private final String REGISTER = new String("REGISTER");
	private final String LOGIN = new String("LOGIN");
	
	private static final String LogTag = new String("StartupActivity");
	private Intent intent;
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startup);
		Log.i(LogTag, "Activity Started");
		
		ChooseActivityWindow caw = new ChooseActivityWindow();
		caw.execute();

		try {
			String intentStatus = new String(caw.get());
			
			if (intentStatus.compareToIgnoreCase(LOGIN) == 0) {
				intent = new Intent(getApplicationContext(), LoginActivity.class);
				
			} else if (intentStatus.compareToIgnoreCase(REGISTER) == 0) {
				intent = new Intent(getApplicationContext(), RegisterActivity.class);
				intent.putExtra(REGISTER, REGISTER);
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		startActivity(intent); 
		finish();
		
		/*
		
		timer = new Timer();
		timer.schedule(new TimerTask() {
			
			public void run() {
				Log.i(LogTag, "ChooseActivityWindow started!");
				Looper.prepare();
				ChooseActivityWindow caw = new ChooseActivityWindow();
				caw.execute();
		
				try {
					String intentStatus = new String(caw.get());
					
					if (intentStatus.compareToIgnoreCase(LOGIN) == 0) {
						intent = new Intent(getApplicationContext(), LoginActivity.class);
						
					} else if (intentStatus.compareToIgnoreCase(REGISTER) == 0) {
						intent = new Intent(getApplicationContext(), RegisterActivity.class);
						intent.putExtra(REGISTER, REGISTER);
					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				startActivity(intent); 
				
				Looper.myLooper().quit();
				finish();
			} 
			
		}, 2000); /* */
	}

	/**
	 * Inner class ChooseActivityWindow determines which Activity Screen the user
	 * is brought to after the application starts up. If the user has not logged
	 * out they are brought to the main menu screen. If this is the user's first
	 * time using the application they are brought to the Registration Screen. If 
	 * the user has used this application before but has logged out they are brought
	 * to the Login Screen.
	 * 
	 * @author Chris
	 *
	 */
	private class ChooseActivityWindow extends AsyncTask<String, String, String> {
		
		private final String REGISTER = new String("REGISTER");
		private final String LOGIN = new String("LOGIN");
		
		
		private final String LogTag = new String("ChooseActivityWindow");
		
		private String returnMessage = new String();
		
		
		@Override
		protected void onPreExecute() {
			
		}

		@Override
		protected String doInBackground(String ...arg0) {
			// TODO Auto-generated method stub
			Log.i(LogTag, "doInBackground Executed!");
			
			ReadInterface checkLog = new DatabaseHandler();
			
			// Checks to see if the user has logged
			if (checkLog.checkLocalLogDatabase()) {
				// TODO finish coding logic for repeat usage Activity
				returnMessage = LOGIN;
			} else {
				Log.i(LogTag, "No Local Database. Register Activity will be invoked!");
				returnMessage = REGISTER;
			}
			
			return returnMessage;
		}
		
		@Override
		protected void onPostExecute(String result) {

		}
	}
}