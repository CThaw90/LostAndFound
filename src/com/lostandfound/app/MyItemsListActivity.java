package com.lostandfound.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import util.MockItemProfile;
import util.ProfileHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class MyItemsListActivity extends Activity {
	
	private AlertDialog.Builder aBuilder;
	private String text = new String();
	private int claimIndex = 0;
	private int index = 0;
	private List<MockItemProfile> items;
	private ReadEntity getItems;
	private Intent intent;
	private Timer timer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_items_list);
		
		try {
			getItems = new ProfileHandler();
			items = new ArrayList<MockItemProfile>(getItems.getAllMyItemsData());
			claimIndex = wasItemClaimed();
			index = wasItemFound();

			timer = new Timer();
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					showAlertDialog();
				}
				
			}, 1000); 
			
			populateItemsView();
			setClickEvent();
			
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), 
					ExceptionHandler.networkError, Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * Returns the index of the item with status Found. If none
	 * returns the integer -1.
	 * @return */
	private int wasItemFound() {
		
		int itemFoundIndex = -1;
		int amount = items.size();
		
		for (int i=0; i < amount; i++) {
			if (items.get(i).found_by.compareTo(getItems.getUsername()) != 0 && 
					items.get(i).found_by.compareTo(ItemScreenActivity.nil) != 0 && 
						items.get(i).status.compareTo("Lost") != 0) {
				itemFoundIndex = i;
				break;
			}
		}
		
		return itemFoundIndex;
	}
	
	/**
	 * Returns the index of the item with a claimed status. If none
	 * returns the integer -1.
	 * @return */
	private int wasItemClaimed() {
		
		int itemClaimedIndex = -1;
		int amount = items.size();

		for (int i=0; i < amount; i++) {

			if (items.get(i).claimed_by.compareTo(getItems.getUsername()) != 0 &&
					items.get(i).claimed_by.compareTo(ItemScreenActivity.nil) != 0 &&
					items.get(i).status.compareToIgnoreCase(ItemScreenActivity.found) == 0) {
				itemClaimedIndex = i;
				break;
			}
		}
		
		return itemClaimedIndex;
	}
	
	/**
	 * Shows an alert of Items that were tagged found or items 
	 * that were tagged claimed by other users */
	private void showAlertDialog() {
		
		runOnUiThread(new Runnable() {
			
			public void run() {
				if (index >= 0) {
			
					text = new String(items.get(index).found_by + " found your " + items.get(index).name);
					
					aBuilder = new AlertDialog.Builder(MyItemsListActivity.this);
					aBuilder.setTitle("Item Found!");
					aBuilder.setMessage(text);
					aBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					
					aBuilder.show();
				}
				
				if (claimIndex >= 0) {
					
					text = new String(items.get(claimIndex).claimed_by + 
							" claimed the " + items.get(claimIndex).name + 
							" you found!");
					
					aBuilder = new AlertDialog.Builder(MyItemsListActivity.this);
					aBuilder.setTitle("Item Claimed!");
					aBuilder.setMessage(text);
					aBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					
					aBuilder.show();
				}
			}
		});
	}
	
	private void populateItemsView() {
		ArrayAdapter<MockItemProfile> adapter = new MyListAdapter();
		ListView list = (ListView)findViewById(R.id.itemslistView);
		list.setAdapter(adapter);
	}
	
	private void setClickEvent() {
		ListView clickList = (ListView)findViewById(R.id.itemslistView);
		clickList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
				
				intent = new Intent(getApplicationContext(), ItemScreenActivity.class);
				intent.putExtra(ItemScreenActivity.type, ItemScreenActivity.own);
				intent.putExtra(ItemScreenActivity.key, position);
				startActivity(intent);
				finish();
				
			}
		});
	}
	
	private class MyListAdapter extends ArrayAdapter<MockItemProfile> {
		
		public MyListAdapter() {
			super(MyItemsListActivity.this, R.layout.custom_my_items_list, items);
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View itemView = convertView;
			
			if (itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.custom_my_items_list, parent, false);
			}
			
			// Find the item to work with or display
			MockItemProfile currentItem = items.get(position);
			
			ImageView image = (ImageView)itemView.findViewById(R.id.listItemImage);
			image.setImageResource(R.drawable.ic_launcher);
			
			TextView listItemName = (TextView)itemView.findViewById(R.id.listItemName);
			listItemName.setText(currentItem.name);
			
			TextView listItemStatus = (TextView)itemView.findViewById(R.id.listItemStatus);
			listItemStatus.setText(currentItem.status);
			
			return itemView;
		}
	}
}