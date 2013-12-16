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
import android.widget.TextView;
import android.widget.Toast;
import entities.ReadEntity;
import entities.UpdateEntity;
import exception.ExceptionHandler;

public class EditItemScreenActivity extends Activity {
	
	private EditText editItemProfileName;
	private EditText editItemProfileLocation;
	private EditText editItemProfileDescription;
	
	private TextView editItemProfileOwnedBy;
	private TextView editItemProfileStatus;
	private TextView editItemProfileLocationTitle;
	private TextView editItemProfileFoundBy;
	private TextView editItemProfileReportCreated;
	
	private Button editItemUpdateButton;
	
	private UpdateItemReport uir;
	
	private UpdateEntity updateItem;
	private ReadEntity itemInfo;
	private int index = 0;
	private Bundle extras;

	private String name = new String();
	private String location = new String();
	private String description = new String();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item_screen);
		
		editItemProfileName = (EditText)findViewById(R.id.editItemProfileName);
		editItemProfileLocation = (EditText)findViewById(R.id.editItemProfileLocation);
		editItemProfileDescription = (EditText)findViewById(R.id.editItemProfileDescription);
		
		editItemProfileOwnedBy = (TextView)findViewById(R.id.editItemProfileOwnedBy);
		editItemProfileStatus = (TextView)findViewById(R.id.editItemProfileStatus);
		editItemProfileLocationTitle = (TextView)findViewById(R.id.editItemProfileLocationTitle);
		editItemProfileFoundBy = (TextView)findViewById(R.id.editItemProfileFoundBy);
		editItemProfileReportCreated = (TextView)findViewById(R.id.editItemProfileReportCreated);
		
		editItemUpdateButton = (Button)findViewById(R.id.editItemUpdateButton);
		
		extras = getIntent().getExtras();
		
		setTextViews();
		
		editItemUpdateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				name = editItemProfileName.getText().toString();
				location = editItemProfileLocation.getText().toString();
				description = editItemProfileDescription.getText().toString();
				
				uir = new UpdateItemReport();
				updateItem = new ProfileHandler();
				
				uir.execute(name, location, description);
				
				try {
					uir.get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String text = new String("Item Profile Updated!");
				Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
				finish();
			}
		});
	}
	
	private void setTextViews() {
		
		if (extras != null && extras.containsKey(ItemScreenActivity.profile)) {

			index = extras.getInt(ItemScreenActivity.profile);
			itemInfo = new ProfileHandler();
			
			editItemProfileName.setText(itemInfo.getItemName(index));
			editItemProfileLocation.setText(itemInfo.getItemLocation(index));
			editItemProfileDescription.setText(itemInfo.getItemDescription(index));
			
			editItemProfileOwnedBy.setText(itemInfo.getItemOwnedBy(index));
			editItemProfileStatus.setText(itemInfo.getItemStatus(index));
			editItemProfileFoundBy.setText((itemInfo.getItemFoundBy(index).compareTo(ItemScreenActivity.nil) != 0 ? 
					itemInfo.getItemFoundBy(index) : ItemScreenActivity.item_missing)); 
			
			editItemProfileLocationTitle.setText(ItemScreenActivity.location + editItemProfileStatus.getText().toString());
			editItemProfileReportCreated.setText(itemInfo.getItemCreatedAt(index));
		}
	}
	
	private class UpdateItemReport extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String...params) {
			
			try {
				updateItem.updateItemReport(name, location, description, index);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), 
						ExceptionHandler.networkError, Toast.LENGTH_LONG).show();
			}
			
			return null;
		}
		
	}
}
