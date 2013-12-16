package com.lostandfound.app;

import java.util.concurrent.ExecutionException;

import util.ProfileHandler;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import entities.CreateEntity;
import exception.ExceptionHandler;

public class ReportFoundItemActivity extends Activity {
	
	private String success = new String("Created!");
	private String status = new String("Found");
	
	private Button foundReportButton;
	
	private EditText foundItemName;
	private EditText foundLocation;
	private EditText foundDescription;
	
	private ReportFoundItem report;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_found_item);
		
		foundItemName = (EditText)findViewById(R.id.foundItemName);
		foundLocation = (EditText)findViewById(R.id.foundLocation);
		foundDescription = (EditText)findViewById(R.id.foundDescription);
		
		foundReportButton = (Button)findViewById(R.id.foundReportButton);
		
		foundReportButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				String name = new String(foundItemName.getText().toString());
				String location = new String(foundLocation.getText().toString());
				String description = new String(foundDescription.getText().toString());
				
				report = new ReportFoundItem();
				report.execute(name, location, description, status);
				
				String response = new String();
				try {
					response = new String(report.get());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				
				Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
				
				if (response.contains(success)) {
					finish();
				} else if (response.compareToIgnoreCase("Exception") == 0) {
					Toast.makeText(getApplicationContext(), 
							ExceptionHandler.networkError, Toast.LENGTH_LONG).show();
				}
			}
		});
	}
	
	private class ReportFoundItem extends AsyncTask<String, String, String> {
		
		private String exception = new String("Exception");
		private String returnString = new String();
		private CreateEntity reportItem;
		
		public String doInBackground(String...args) {
			
			reportItem = new ProfileHandler();
			try {
				returnString = reportItem.createItemProfile(args[0], args[1], args[2], args[3]);
			} catch (Exception e) {
				returnString = new String(exception);
			}
			
			return returnString;
		}
	}
}
