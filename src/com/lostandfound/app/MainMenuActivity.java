package com.lostandfound.app;

import java.util.concurrent.ExecutionException;

import util.ProfileHandler;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import entities.CreateEntity;
import exception.ExceptionHandler;

public class MainMenuActivity extends Activity {
	
	private String RECENT_REPORTS = new String("RECENT_REPORTS");
	private String LOAD_USER = new String("LOAD_USER");

	private Button reportLostItem;
	private Button reportFoundItem;
	private Button recentReports;
	private Button myProfile;
	private Button searchItems;
	
	private LoadData loadData;
	private LoadData loadMockItems;
	private Intent intent;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		Bundle extras = getIntent().getExtras();
		
		if (extras != null && (extras.get("Login").toString().compareToIgnoreCase("LoadUserData") == 0)) {
			
			try {
				loadData = new LoadData();
				loadData.execute(LOAD_USER, extras.getString("username"));
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), 
						ExceptionHandler.networkError, Toast.LENGTH_LONG).show();
			}
		}
		
		reportLostItem = (Button)findViewById(R.id.reportLostItem);
		reportFoundItem = (Button)findViewById(R.id.reportFoundItem);
		recentReports = (Button)findViewById(R.id.recentReports);
		myProfile = (Button)findViewById(R.id.myProfile);
		searchItems = (Button)findViewById(R.id.searchItems);
		
		addButtonListeners();
	}
	
	public void addButtonListeners() {
		
		/**
		 * Creates a Button Listener that sends the activity
		 * Screen to the ReportLostItemActvity Screen **/
		reportLostItem.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				intent = new Intent(getApplicationContext(), ReportLostItemActivity.class);
				startActivity(intent);
			}
		});
		
		reportFoundItem.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				intent = new Intent(getApplicationContext(), ReportFoundItemActivity.class);
				startActivity(intent);
			}
		});
		
		recentReports.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				String response = new String(RECENT_REPORTS);
				
				try {
					loadMockItems = new LoadData();
					loadMockItems.execute(RECENT_REPORTS);
					
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), 
							ExceptionHandler.networkError, Toast.LENGTH_LONG).show();
				}
				
				try {
					response = new String(loadMockItems.get());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				
				if (response.compareToIgnoreCase(RECENT_REPORTS) == 0) {
					intent = new Intent(getApplicationContext(), RecentReportsActivity.class);
					startActivity(intent);
				}
			}
		});
		
		/**
		 * Creates a Button Listener that sends the activity 
		 * Screen to the UserProfileActivity screen  **/
		myProfile.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				intent = new Intent(getApplicationContext(), UserScreenActivity.class);
				intent.putExtra(ItemScreenActivity.type, ItemScreenActivity.own);
				startActivity(intent);
			}
		});
		
		/**
		 * Creates a Button Listener that sends the activity
		 * Screen to the ItemSearchActivity screen (change) **/
		searchItems.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				/**  DEBUGGING PURPOSES
				intent = new Intent(getApplicationContext(), ExceptionActivity.class);
				intent.putExtra("Name", "Debugging Screen"); */
				
				intent = new Intent(getApplicationContext(), ItemSearchActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private class LoadData extends AsyncTask<String, String, String> {
		
		private String returnString = new String(RECENT_REPORTS);
		private CreateEntity create;

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			create = new ProfileHandler();
			
			try {
				if (params[0].compareToIgnoreCase(LOAD_USER) == 0) {
					returnString = create.loadAllUserData(params[1]);
				} else if (params[0].compareToIgnoreCase(RECENT_REPORTS) == 0) {
					create.loadMockItemProfiles(true, null);
				}
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), 
						ExceptionHandler.networkError, Toast.LENGTH_LONG).show();
			}
			
			return returnString;
		}
	}
}
