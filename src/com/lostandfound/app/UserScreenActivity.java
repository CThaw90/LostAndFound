package com.lostandfound.app;

import util.MockUserProfile;
import util.ProfileHandler;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import entities.ReadEntity;
import exception.ExceptionHandler;

public class UserScreenActivity extends Activity {
	
	private ImageButton editProfileButton;
	private Button mainMenuBackButton;
	private Button myReportedItems;
	
	private TextView editProfileHintTitle;
	private TextView user_phoneNumber;
	private TextView user_username;
	private TextView user_address;
	private TextView user_email;
	private TextView user_name;
	
	private MockUserProfile mup;
	private ReadEntity read;
	
	private String type = new String();
	
	private Intent intent;
	private Bundle extras;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_screen);
		
		extras = getIntent().getExtras();
		
		setViews();
		
		if (extras.containsKey(ItemScreenActivity.type)) {
			type = new String(extras.getString(ItemScreenActivity.type));
		} else {
			Toast.makeText(getApplicationContext(), 
					ExceptionHandler.networkError, Toast.LENGTH_LONG).show();
		}
		
		showAppropriateButtons();
		addButtonListeners();
		setUserData();
	}

	public void onResume() {
		super.onResume();
		setViews();
		showAppropriateButtons();
		addButtonListeners();
		setUserData();
	}

	/**
	 * Sets the Views of All Buttons TextViews and ImageButtons */
	private void setViews() {
		user_name = (TextView)findViewById(R.id.user_name);
		user_email = (TextView)findViewById(R.id.user_email);
		user_address = (TextView)findViewById(R.id.user_address);
		user_username = (TextView)findViewById(R.id.user_username);
		user_phoneNumber = (TextView)findViewById(R.id.userPhoneNumber);
		editProfileHintTitle = (TextView)findViewById(R.id.edit_profile_hint_title);
		
		editProfileButton = (ImageButton)findViewById(R.id.editProfileButton);
		mainMenuBackButton = (Button)findViewById(R.id.mainMenuBackButton);
		myReportedItems = (Button)findViewById(R.id.myItemsButton);
	}
	
	private void showAppropriateButtons() {
		
		if (type.compareTo(ItemScreenActivity.own) != 0) {
			myReportedItems.setVisibility(View.INVISIBLE);
			editProfileButton.setClickable(false);
		}
	}
	
	/**
	 * Displays all appropriate User data in their selected views */
	private void setUserData() {
		
		read = new ProfileHandler();
		
		String na = new String("Nothing Attributed");
		String no = new String("LostAndFound User");
		String nil = new String(ItemScreenActivity.nil);
		
		String name = new String();
		String email = new String();
		String address = new String();
		String username = new String();
		String phoneNumber = new String();
		
		if (type.compareTo(ItemScreenActivity.own) == 0) {
		
			name = new String("Hello, " + ((read.getUserName().compareTo(nil) != 0) ? read.getUserName() : no));
			email = new String((read.getUserEmail().compareTo(nil) != 0) ? read.getUserEmail() : na);
			address = new String((read.getUserAddress().compareTo(nil) != 0) ? read.getUserAddress() : na);
			username = new String((read.getUsername().compareTo(nil) != 0) ? read.getUsername() : na);
			phoneNumber = new String((read.getUserPhoneNumber().compareTo(nil) != 0) ? read.getUserPhoneNumber() : na);
			
		} else {
			
			mup = read.getLoadedMockUserProfile();
			editProfileHintTitle.setVisibility(View.INVISIBLE);
			
			name = new String(mup.name.compareTo(nil) != 0 ? mup.name : no);
			email = new String(mup.email.compareTo(nil) != 0 ? mup.email : na);
			address = new String(mup.address.compareTo(nil) != 0 ? mup.email : na);
			username = new String(mup.username.compareTo(nil) != 0 ? mup.username : na);
			phoneNumber = new String(mup.phoneNumber.compareTo(nil) != 0 ? mup.phoneNumber : na);
		}
		
		user_name.setText(name);
		user_email.setText(email);
		user_username.setText(username);
		user_address.setText(address);
		user_phoneNumber.setText(phoneNumber);
	}
	
	/**
	 *  Adds all Button Listeners for UI navigation */
	public void addButtonListeners() {
		
		editProfileButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				intent = new Intent(getApplicationContext(), EditUserScreenActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		mainMenuBackButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				finish();
			}
		});
		
		myReportedItems.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				intent = new Intent(getApplicationContext(), MyItemsListActivity.class);
				startActivity(intent);
			}
		});
	}
}
