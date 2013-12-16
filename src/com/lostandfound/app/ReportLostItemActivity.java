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

public class ReportLostItemActivity extends Activity {

	private Button lostReportButton;
	
	private EditText lostItemName;
	private EditText lostLocation;
	private EditText lostDescription;
	
	private String success = new String("Created!");
	private String status = new String("Lost");
	
	private CreateEntity createReport;
	private ReportLostItem report;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_lost_item);
		
		lostItemName = (EditText)findViewById(R.id.lostItemName);
		lostLocation = (EditText)findViewById(R.id.lostLocation);
		lostDescription = (EditText)findViewById(R.id.lostDescription);
		
		lostReportButton = (Button)findViewById(R.id.lostReportButton);
		
		lostReportButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				String name = new String(lostItemName.getText().toString());
				String location = new String(lostLocation.getText().toString());
				String description = new String(lostDescription.getText().toString());
				createReport = new ProfileHandler();
				report = new ReportLostItem();
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
	
	class ReportLostItem extends AsyncTask<String, String, String> {
		/* For ExceptionHandling Testing */
		String exception = new String("Exception");
		String returnString = new String();
		@Override
		protected String doInBackground(String ...arg) {
			// TODO Auto-generated method stub
			createReport = new ProfileHandler();
			
			try {
				returnString = createReport.createItemProfile(arg[0], arg[1], arg[2], arg[3]);
			} catch (Exception e) {
				returnString = exception;
			}
			
			return returnString;
		}
	}
}
