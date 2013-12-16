package com.lostandfound.app;

import java.util.concurrent.ExecutionException;

import util.ProfileHandler;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import entities.CreateEntity;
import exception.ExceptionHandler;

public class ItemSearchActivity extends Activity {
	
	private String finished = new String("Finished");
	private CreateEntity loadResults;
	private SearchForItems search;
	
	private ImageButton searchButton;
	private EditText searchString;
	private Intent intent;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_search);
		
		searchString = (EditText)findViewById(R.id.searchString);
		searchButton = (ImageButton)findViewById(R.id.searchButton);
		
		searchButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				/* */
				String string = new String(searchString.getText().toString());
				search = new SearchForItems();
				search.execute(string);

				try {
					if (search.get().compareTo(finished) == 0) {
						intent = new Intent(getApplicationContext(), SearchResultsActivity.class);
					//	intent = new Intent(getApplicationContext(), ExceptionActivity.class);
					//	intent.putExtra("Name", "Exception was Thrown");
						startActivity(intent);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
		});
	}
	
	private class SearchForItems extends AsyncTask<String, String, String> {

		private String returnString = new String(finished);
		@Override
		protected String doInBackground(String... arg) {
			loadResults = new ProfileHandler();
			try {
				loadResults.loadMockItemProfiles(false, arg[0]);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), 
						ExceptionHandler.networkError, Toast.LENGTH_LONG).show();
			}
			return returnString;
		}
		
	}
}