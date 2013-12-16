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
import entities.ReadEntity;

public class RecentReportsActivity extends Activity {
	
	private ArrayAdapter<MockItemProfile> adapter;
	private List<MockItemProfile> reports;
	private ListView list;

	private ReadEntity getReports;
	
	private Intent intent;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recent_reports);
		getReports = new ProfileHandler();
		reports = new ArrayList<MockItemProfile>(getReports.getLoadedMockItemProfiles());
		populateReports();
		setClickEvent();
	}
	
	private void populateReports() {
		adapter = new MyArrayAdapter(getApplicationContext(), R.layout.custom_recent_reports, reports);
		list = (ListView)findViewById(R.id.recentReportsListView);
		list.setAdapter(adapter);
	}
	
	private void setClickEvent() {
		ListView list = (ListView)findViewById(R.id.recentReportsListView);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
				
				intent = new Intent(getApplicationContext(), ItemScreenActivity.class);
				intent.putExtra(ItemScreenActivity.key, position);
				intent.putExtra(ItemScreenActivity.type, ItemScreenActivity.mock);
				startActivity(intent);
			}
		});
	}
	
	class MyArrayAdapter extends ArrayAdapter<MockItemProfile> {

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
