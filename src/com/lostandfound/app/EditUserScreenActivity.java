package com.lostandfound.app;

import java.util.concurrent.ExecutionException;

import util.ProfileHandler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import entities.ReadEntity;
import entities.UpdateEntity;
import exception.ExceptionHandler;

public class EditUserScreenActivity extends Activity {
	
	private String nil = new String("nil");
	
	private TextView setUpdateUsername;

	private ImageButton changeProfilePic;
	private Button updateProfileButton;
	
	private EditText edit_user_phoneNumber;
	private EditText edit_user_address;
	private EditText updateName;
	private EditText updateEmail;
	
	private UpdateEntity update;
	private ReadEntity display;
	
	private String phoneNumber = new String();
	private String address = new String();
	private String email = new String();
	private String name = new String();
	
	private String pictureUri = null;
	
	private UpdateUserProfile updateProfile;
	
	private static int req_code = 0;
	
	private Uri selectedImageUri;
	private Intent intent;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_user_screen);
	
		updateProfileButton = (Button)findViewById(R.id.updateProfileButton);
		changeProfilePic = (ImageButton)findViewById(R.id.changeProfilePic);
		
		edit_user_phoneNumber = (EditText)findViewById(R.id.updatePhoneNumber);
		setUpdateUsername = (TextView)findViewById(R.id.setUpdateUsername);
		edit_user_address = (EditText)findViewById(R.id.updateAddress);
		updateEmail = (EditText)findViewById(R.id.updateEmail);
		updateName = (EditText)findViewById(R.id.updateName);
		
		display = new ProfileHandler();
		
		String username = new String(display.getUsername());
		
		phoneNumber = new String(display.getUserPhoneNumber());
		address = new String(display.getUserAddress());
		email = new String(display.getUserEmail());
		name = new String(display.getUserName());
	
		edit_user_phoneNumber.setText((phoneNumber.compareTo(nil) != 0) ? phoneNumber : "");
		edit_user_address.setText((address.compareTo(nil) != 0) ? address : "");
		updateEmail.setText((email.compareTo(nil) != 0) ? email : "");
		updateName.setText((name.compareTo(nil) != 0) ? name : "");
		setUpdateUsername.setText(username);
		/* */
		
		changeProfilePic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Select file to upload"), req_code);
			}
		});

		updateProfileButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				try {
					name = updateName.getText().toString();
					email = updateEmail.getText().toString();
					address = edit_user_address.getText().toString();
					phoneNumber = edit_user_phoneNumber.getText().toString();
					
					updateProfile = new UpdateUserProfile(name, email, address, phoneNumber, pictureUri);
					updateProfile.execute();
					String msg = new String(updateProfile.get());
					if (msg.compareTo("Exception") == 0) {
						throw new ExceptionHandler(404, "Network Connection Error");
					}
					
					Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
					intent = new Intent(getApplicationContext(), UserScreenActivity.class);
					intent.putExtra(ItemScreenActivity.type, ItemScreenActivity.own);
					startActivity(intent);
					finish();
					
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExceptionHandler e) {
					String err = new String(e.exceptionHandle());
					Toast.makeText(getApplicationContext(), 
							err, Toast.LENGTH_LONG).show();
				}
			}
		});
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (resultCode == RESULT_OK) {
			selectedImageUri = data.getData();
			
			if (requestCode == req_code) {
				
				String[] projection = {MediaStore.Images.Media.DATA };
				Cursor cursor = getContentResolver().query(selectedImageUri, projection, null, null, null);
				int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
				cursor.moveToFirst();
				pictureUri = cursor.getString(column_index);
				
				Toast.makeText(getApplicationContext(), pictureUri, Toast.LENGTH_LONG).show();
			}
		}
	}
	
	class UpdateUserProfile extends AsyncTask<String, String, String> {

		private ProgressDialog pDialog;
		
		private String pictureUri = new String();
		private String phoneNumber = new String();
		private String address = new String();
		private String email = new String();
		private String name = new String();
		
		public UpdateUserProfile(String name, String email, String address, String phoneNumber, String pictureUri) {
			this.pictureUri = pictureUri;
			this.phoneNumber = phoneNumber;
			this.address = address;
			this.email = email;
			this.name = name;
		}
		
		@Override
		protected void onPreExecute() {
			pDialog = new ProgressDialog(EditUserScreenActivity.this);
			pDialog.setTitle("Updating User Data");
			pDialog.setMessage("Please Wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			update = new ProfileHandler();
			String msg = new String();
			try {
				msg = update.updateUserProfile(name, email, address, phoneNumber, pictureUri);
				
			} catch (Exception e) {
				msg = new String("Exception");
				Toast.makeText(getApplicationContext(), 
					ExceptionHandler.networkError, Toast.LENGTH_LONG).show();
			}
			return msg;
		}
		
		@Override
		protected void onPostExecute(String msg) {
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
		}
	}
}
