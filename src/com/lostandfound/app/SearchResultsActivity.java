package com.lostandfound.app;

import java.util.ArrayList;
import java.util.List;

import util.MockItemProfile;
import util.ProfileHandler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import entities.ReadEntity;
import exception.ExceptionHandler;

public class SearchResultsActivity extends Activity {
	
	private ListView searchResultsListView;
	ArrayAdapter<MockItemProfile> adapter;
	private List<MockItemProfile> reports;
	private ReadEntity getReports;
	
	private Intent intent;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_results);

		getReports = new ProfileHandler();
		reports = new ArrayList<MockItemProfile>(getReports.getLoadedMockItemProfiles());
		populateReports();
		setClickEvent();
	}
	
	private void setClickEvent() {
		
		ListView list = (ListView)findViewById(R.id.searchResultsListView);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
				
				intent = new Intent(getApplicationContext(), ItemScreenActivity.class);
				intent.putExtra(ItemScreenActivity.type, ItemScreenActivity.mock);
				intent.putExtra(ItemScreenActivity.key, position);
				startActivity(intent);
			}
		});
	}
	
	private void populateReports() {
		try {
		adapter = new MyArrayAdapter(getApplicationContext(), R.layout.custom_recent_reports, reports);
		searchResultsListView = (ListView)findViewById(R.id.searchResultsListView);
		searchResultsListView.setAdapter(adapter);
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), 
					ExceptionHandler.networkError, Toast.LENGTH_LONG).show();
		}
	}
	
	

	private class MyArrayAdapter extends ArrayAdapter<MockItemProfile> {

		public MyArrayAdapter(Context context, int resource, List<MockItemProfile> objects) {
			super(context, resource, objects);
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View itemView = convertView;
			
			if (itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.custom_recent_reports, parent, false);
			}
			
			MockItemProfile currentReport = reports.get(position);
			
			ImageView image = (ImageView)itemView.findViewById(R.id.recentReportImage);
			image.setImageResource(R.drawable.ic_launcher);
			
			TextView recentReportName = (TextView)itemView.findViewById(R.id.recentReportName);
			recentReportName.setText(currentReport.name);
			
			TextView recentReportStatus = (TextView)itemView.findViewById(R.id.recentReportStatus);
			recentReportStatus.setText(currentReport.status);
			
			TextView recentReportOwner = (TextView)itemView.findViewById(R.id.recentReportOwner);
			recentReportOwner.setText(currentReport.owned_by);
			
			return itemView;
		}
		
	}
}
