package com.lostandfound.app;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import util.MockItemProfile;
import util.ProfileHandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import entities.*;
import exception.ExceptionHandler;

public class ItemScreenActivity extends Activity {
	
	static String item_missing = new String("Item is still missing");
	static String location = new String("Location ");
	static String profile = new String("profile");
	static String found = new String("found");
	static String claim = new String("claim");
	static String key = new String("ItemId");
	static String mock = new String("mock");
	static String type = new String("type");
	static String own = new String("own");
	static String nil = new String("nil");
	
	private ImageView itemProfileImage;
	
	private TextView itemProfileName;
	private TextView itemProfileStatus;
	private TextView itemProfileLocationTitle;
	private TextView itemProfileClaimedByTitle;
	private TextView itemProfileFoundByTitle;
	private TextView itemProfileLocation;
	private TextView itemProfileDescription;
	private TextView itemProfileReportCreated;
	private TextView itemProfileFoundBy;
	private TextView itemProfileOwnedBy;
	private TextView itemProfileClaimedByNoOne;
	
	private Button itemProfileFinishButton;
	private Button itemProfileViewUserButton;
	private Button itemProfileEditButton;
	private Button itemProfileDeleteButton;
	private Button itemProfileEmailButton;
	private Button itemProfileFoundButton;
	private Button itemProfileWhoFoundButton;
	private Button itemProfileClaimedByButton;
	private Button itemProfileClaimItButton;
	
	private AlertDialog.Builder aBuilder;
	
	private ArrayList<MockItemProfile> mip;
	
	private String remove = new String("remove");
	private String id = new String("0");
	private ItemScreenInteraction isi;
	private CreateEntity loadUser;
	private UpdateEntity updateReport;
	private ReadEntity display;
	private DeleteEntity delete;
	
	private Intent intent;
	private int index = 0;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_screen);

		itemProfileFinishButton = (Button)findViewById(R.id.finishItemScreen);
		itemProfileViewUserButton = (Button)findViewById(R.id.itemProfileViewUserButton);
		itemProfileEditButton = (Button)findViewById(R.id.itemProfileEditButton);
		itemProfileDeleteButton = (Button)findViewById(R.id.itemProfileDeleteButton);
		itemProfileEmailButton = (Button)findViewById(R.id.itemProfileEmailButton);
		itemProfileWhoFoundButton = (Button)findViewById(R.id.itemProfileWhoFoundButton);
		itemProfileClaimedByButton = (Button)findViewById(R.id.itemProfileClaimedByButton);
		itemProfileClaimItButton = (Button)findViewById(R.id.itemProfileClaimItButton);
		itemProfileFoundButton = (Button)findViewById(R.id.itemProfileFoundButton);
		
		Bundle extras = getIntent().getExtras();

		if (extras != null && extras.containsKey(key) && extras.containsKey(type)) {
			index = extras.getInt(key);
			setDisplay(extras.getInt(key), extras.getString(type));
			type = extras.getString(type);
		}
		
		showAppropriateButtons();
		addButtonListeners();
	}
	
	public void onResume() {
		super.onResume();
		setDisplay(index, type);
	}
	
	private void viewUserProfile(String userProfile) {
		
		loadUser = new ProfileHandler();
		isi = new ItemScreenInteraction();
		isi.execute(profile, userProfile);
		
		try {
			isi.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		intent = new Intent(getApplicationContext(), UserScreenActivity.class);
		intent.putExtra(type, mock);
		startActivity(intent);
	}
	
	private void setDisplay(int index, String type) {
		
		itemProfileImage = (ImageView)findViewById(R.id.itemProfileImage);
		itemProfileImage.setImageResource(R.drawable.ic_launcher);
	
		itemProfileName = (TextView)findViewById(R.id.itemProfileName);
		itemProfileStatus = (TextView)findViewById(R.id.itemProfileStatus);
		itemProfileFoundBy = (TextView)findViewById(R.id.itemProfileFoundBy);
		itemProfileOwnedBy = (TextView)findViewById(R.id.itemProfileOwnedBy);
		itemProfileLocation = (TextView)findViewById(R.id.itemProfileLocation);
		itemProfileDescription = (TextView)findViewById(R.id.itemProfileDescription);
		itemProfileReportCreated = (TextView)findViewById(R.id.itemProfileReportCreated);
		itemProfileLocationTitle = (TextView)findViewById(R.id.itemProfileLocationTitle);
		itemProfileClaimedByTitle = (TextView)findViewById(R.id.itemProfileClaimedByTitle);
		itemProfileFoundByTitle = (TextView)findViewById(R.id.itemProfileFoundByTitle);
		itemProfileClaimedByNoOne = (TextView)findViewById(R.id.itemProfileClaimedByNoOne);
		
		display = new ProfileHandler();
		
		if (type.compareTo(mock) == 0) {
			
			mip = new ArrayList<MockItemProfile>(display.getLoadedMockItemProfiles());
			id = mip.get(index).id;
			
			itemProfileName.setText(mip.get(index).name);
			itemProfileStatus.setText(mip.get(index).status);
			
			itemProfileFoundBy.setText((mip.get(index).found_by.compareTo(nil) != 0) ?
				mip.get(index).found_by : item_missing);
			
			itemProfileOwnedBy.setText(mip.get(index).owned_by);			
			itemProfileLocation.setText(mip.get(index).location);
			itemProfileDescription.setText(mip.get(index).description);
			itemProfileReportCreated.setText(mip.get(index).created_at);
			
			itemProfileLocationTitle.setText(location + mip.get(index).status);
			itemProfileClaimedByButton.setText(mip.get(index).claimed_by);
			itemProfileWhoFoundButton.setText(mip.get(index).found_by);
			
		} else if (type.compareTo(own) == 0) {

			id = display.getItemId(index);
			itemProfileName.setText(display.getItemName(index));
			itemProfileStatus.setText(display.getItemStatus(index));

			itemProfileFoundBy.setText((display.getItemFoundBy(index).compareTo(nil) != 0) ?
					display.getItemFoundBy(index) : item_missing);
		
			itemProfileOwnedBy.setText(display.getItemOwnedBy(index));
			itemProfileLocation.setText(display.getItemLocation(index));
			itemProfileDescription.setText(display.getItemDescription(index));
			itemProfileReportCreated.setText(display.getItemCreatedAt(index));
			itemProfileLocationTitle.setText(location + display.getItemStatus(index));
			
			itemProfileClaimedByButton.setText(display.getItemClaimedBy(index));
			itemProfileWhoFoundButton.setText(display.getItemFoundBy(index));
		}
	}

	/**
	 * Function determines which views to show on the activity screen. Depending
	 * on the elevated privileges of the user viewing the activity. */
	private void showAppropriateButtons() {
		
		if (display.getUsername().compareTo(itemProfileOwnedBy.getText().toString()) == 0) {
			
			itemProfileFoundButton.setVisibility(View.INVISIBLE);
			itemProfileEmailButton.setVisibility(View.INVISIBLE);
			itemProfileViewUserButton.setVisibility(View.INVISIBLE);
			itemProfileClaimItButton.setVisibility(View.INVISIBLE);
			
		} else {
			
			itemProfileEditButton.setVisibility(View.INVISIBLE);
			itemProfileDeleteButton.setVisibility(View.INVISIBLE);
			itemProfileEmailButton.setVisibility(View.INVISIBLE);
		}
		
		String ownedBy = new String(itemProfileOwnedBy.getText().toString());
		String foundBy = new String(itemProfileFoundBy.getText().toString());
		String status = new String(itemProfileStatus.getText().toString());

		if (foundBy.compareToIgnoreCase(ownedBy) != 0 || status.compareToIgnoreCase(found) != 0) {
			
			itemProfileClaimedByTitle.setVisibility(View.INVISIBLE);
			itemProfileClaimedByNoOne.setVisibility(View.INVISIBLE);
			itemProfileClaimItButton.setVisibility(View.INVISIBLE);
			
		} else {
			itemProfileWhoFoundButton.setVisibility(View.INVISIBLE);
			itemProfileFoundByTitle.setVisibility(View.INVISIBLE);
			itemProfileFoundBy.setVisibility(View.INVISIBLE);
			itemProfileFoundButton.setVisibility(View.INVISIBLE);
		}
		
		if (itemProfileClaimedByButton.getText().toString().compareTo(nil) == 0) {
			itemProfileClaimedByButton.setVisibility(View.INVISIBLE);
		} else {
			itemProfileClaimedByNoOne.setVisibility(View.INVISIBLE);
			itemProfileClaimItButton.setVisibility(View.INVISIBLE);
		}
		
		if (foundBy.compareTo(ownedBy) == 0 || foundBy.compareTo(item_missing) == 0) {
			itemProfileWhoFoundButton.setVisibility(View.INVISIBLE);

		} else {
			itemProfileFoundBy.setVisibility(View.INVISIBLE);
		}
	}
	
	private void addButtonListeners() {
		
		itemProfileFinishButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (type.compareTo(own) == 0) {
					intent = new Intent(getApplicationContext(), MyItemsListActivity.class);
					startActivity(intent);
				}
				
				finish();
			}
		});
		
		itemProfileEmailButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), 
						itemProfileClaimedByButton.getText().toString(), Toast.LENGTH_LONG).show();
			}
		});
		
		itemProfileViewUserButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				viewUserProfile(itemProfileOwnedBy.getText().toString());
			}
		});
		
		itemProfileWhoFoundButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				viewUserProfile(itemProfileFoundBy.getText().toString());
			}
		});
		
		itemProfileEditButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				intent = new Intent(getApplicationContext(), EditItemScreenActivity.class);
				intent.putExtra(profile, index);
				startActivity(intent); 
			}
		});
		
		itemProfileDeleteButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				try {
					aBuilder = new AlertDialog.Builder(ItemScreenActivity.this);
					aBuilder.setTitle("Delete Item?");
					aBuilder.setMessage("Are you sure you want to delete this item?");
					aBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							intent = new Intent(ItemScreenActivity.this, MyItemsListActivity.class);
							startActivity(intent);
							isi = new ItemScreenInteraction();
							delete = new ProfileHandler();
							isi.execute(remove);
							
							// Wait for the execution to finish
							try {
								isi.get();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ExecutionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							String text = new String("Item Deleted");
							Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
							
							finish();
						}
					});
					
					aBuilder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					}).show();
					
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), 
							ExceptionHandler.networkError, Toast.LENGTH_LONG).show();
				}
			}
		});
		
		itemProfileFoundButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {

				aBuilder = new AlertDialog.Builder(ItemScreenActivity.this);
				aBuilder.setTitle("Notify User");
				aBuilder.setMessage("Did you really find this item?");
				aBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						try {
							updateReport = new ProfileHandler();
							isi = new ItemScreenInteraction();
							isi.execute(found);
						
							try {
								isi.get();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ExecutionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
							Toast.makeText(getApplicationContext(), 
									"User Notified!", Toast.LENGTH_LONG).show();
							
							finish();
							
						} catch (Exception e) {
							Toast.makeText(getApplicationContext(), 
								ExceptionHandler.networkError, Toast.LENGTH_LONG).show();
						}
					}
				});
				
				aBuilder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Do Nothing
						dialog.dismiss();
					}
				}).show();
			}
		});
		
		itemProfileClaimItButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				updateReport = new ProfileHandler();
				isi = new ItemScreenInteraction();
				isi.execute(claim, id, display.getUsername());
			}
		});
		
		itemProfileClaimedByButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				viewUserProfile(itemProfileClaimedByButton.getText().toString());
			}
		});
	}
	
	private class ItemScreenInteraction extends AsyncTask<String, String, String> {
		
		private String returnString = new String("OK");

		@Override
		protected String doInBackground(String... params) {
			try {
				if (params[0].compareTo(found) == 0) {
					updateReport.updateItemReportToFound(id);
				
				} else if (params[0].compareTo(remove) == 0) {
					delete.deleteItem(id);
					
				} else if(params[0].compareTo(profile) == 0) {
					
					String username = new String(params[1]);
					loadUser.loadAUserProfile(username);
					
				} else if (params[0].compareTo(claim) == 0) {
					
					String username = new String(params[2]);
					String id = new String(params[1]);
					
					updateReport.updateItemReportToClaimed(id, username);
				}
				
			} catch (Exception e) {
				returnString = new String("Exception");
			}
			
			return returnString;
		}
	}
}
