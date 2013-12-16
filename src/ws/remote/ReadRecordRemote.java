package ws.remote;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import util.ProfileHandler;
import entities.CreateEntity;
import entities.UpdateEntity;

class ReadRecordRemote extends WebServiceHandler {
	
	private JSONObject json;
	
	private List<NameValuePair> params;
	private StringBuilder sb; 
	private String url;
	private CreateEntity createProfile;
	private UpdateEntity updateProfile;
	
	static String update = new String("update");
	static String load = new String("load");
	static String search = new String("search");
	static String found = new String("found");
	static String claim = new String("claim");

	/**
	 * Checks the server to see if Username is available. If a 
	 * username with same string pattern exists the method returns
	 * false. User then has to pick another username
	 * @param username
	 * @return
	 * @throws Exception */
	protected boolean checkIfUsernameAvailable(String username) throws Exception {
		
		sb = new StringBuilder();
		sb.append(WebServiceHandler.DOMAIN);
		sb.append(WebServiceHandler.FOLDER);
		sb.append(WebServiceHandler.CHECK_USERNAME);
		
		url = new String(sb.toString());
		
		params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));

		json = super.makeHttpRequestGetResponse(url, WebServiceHandler.POST, params);
		return (json.getString("username").compareToIgnoreCase("unavailable") != 0) ;
	}
	
	/**
	 * Sends Entered Credentials to the server and if Login successful
	 * user is taken to the MainMenuActivity screen
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception */
	protected String attemptUserLogin(String username, String password) throws Exception {
		
		sb = new StringBuilder();
		sb.append(WebServiceHandler.DOMAIN);
		sb.append(WebServiceHandler.FOLDER);
		sb.append(WebServiceHandler.LOGIN_USER);
		
		url = new String(sb.toString());
		
		params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		
		json = super.makeHttpRequestGetResponse(url, "POST", params);
		
		return json.getString("message");
	}
	
	/** Extracts all data about a specific user from the database 
	 * and saves it in the UserProfile Entities Object Class
	 * @throws Exception */
	protected void extractAndStoreUserProfile(String username, boolean mine) throws Exception {
		
		createProfile = new ProfileHandler();
		
		sb = new StringBuilder();
		sb.append(WebServiceHandler.DOMAIN);
		sb.append(WebServiceHandler.FOLDER);
		sb.append(WebServiceHandler.GET_USER_PROFILE);
		
		url = new String(sb.toString());
		
		params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		
		json = super.makeHttpRequestGetResponse(url, POST, params);
		
		String id = new String(json.getString("id"));
		String name = new String(json.getString("name"));
		String email = new String(json.getString("email"));
		String address = new String(json.getString("address"));
		String phoneNumber = new String(json.getString("phone_number"));
		String created_at = new String(json.getString("created_at"));
		
		createProfile.loadAUserProfile(id, name, email, username, address, phoneNumber, created_at, mine);
	}
	
	/** 
	 * @param owned_by
	 * @param name
	 * @param update
	 * @throws Exception */
	protected void getItemProfile(String ownedBy, String itemName, String type) throws Exception {
		
		sb = new StringBuilder();
		sb.append(WebServiceHandler.DOMAIN);
		sb.append(WebServiceHandler.FOLDER);
		sb.append(WebServiceHandler.GET_ITEM_REPORT);
		
		url = new String(sb.toString());
		
		String id; 
		String name;
		String location; 
		String description; 
		String status;
		String reported_lost; 
		String reported_found; 
		String owned_by;
		String claimed_by;
		String found_by;
		String created_at; 
		
		if (type.compareTo(update) == 0 || type.compareTo(found) == 0) {	
			
			params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("owned_by", ownedBy));
			params.add(new BasicNameValuePair("name", itemName));
			
			json = super.makeHttpRequestGetResponse(url, POST, params);

			id = new String(json.getString("id"));
			name = new String(json.getString("name"));
			location = new String(json.getString("location"));
			description = new String(json.getString("description"));
			status = new String(json.getString("status"));
			reported_lost = new String(json.getString("reported_lost"));
			reported_found = new String(json.getString("reported_found"));
			owned_by = new String(json.getString("owned_by"));
			claimed_by = new String(json.getString("claimed_by"));
			found_by = new String(json.getString("found_by"));
			created_at = new String(json.getString("created_at"));
			
			if (type.compareTo(update) == 0) {
				updateProfile = new ProfileHandler();
				updateProfile.completeMyItem(id, name, location, description, status,
					reported_lost, reported_found, found_by, owned_by, created_at);

			} else if (type.compareTo(found) == 0) {
				createProfile = new ProfileHandler();
				createProfile.addMockItemProfile(id, name, location, description, status,
						reported_lost, reported_found, found_by, claimed_by, owned_by, created_at);
			
			} else if (type.compareTo(claim) == 0) {
				
				createProfile = new ProfileHandler();
				createProfile.addMockItemProfile(id, name, location, description, status,
						reported_lost, reported_found, found_by, claimed_by, owned_by, created_at);
			}
			
		} else if (type.compareTo(load) == 0 || type.compareTo(search) == 0) {

			createProfile = new ProfileHandler();

			int numberOfItems = 0;
			
			if (ownedBy != null) {
				numberOfItems = getNumberOfItems(ownedBy, false, false);
				
			} else if (type.compareTo(load) == 0) {
				numberOfItems = getNumberOfItems(null, true, false);
				
			} else if (type.compareTo(search) == 0) {
				numberOfItems = getNumberOfItems(itemName, false, true);
			}
					
			sb = new StringBuilder();
			sb.append(WebServiceHandler.DOMAIN);
			sb.append(WebServiceHandler.FOLDER);
			sb.append(WebServiceHandler.GET_ITEM_REPORT);
			
			url = new String(sb.toString());

			params = new ArrayList<NameValuePair>();
			
			if (ownedBy != null) {
				params.add(new BasicNameValuePair("owned_by", ownedBy));
			} else if (type.compareTo(load) == 0) {
				params.add(new BasicNameValuePair("timestamp", "yes"));
			} else if (type.compareTo(search) == 0){
				params.add(new BasicNameValuePair("string", itemName));
			}
			
			
			id = new String("0");
			
			for (int i=0; i < numberOfItems; i++) {
				params.add(new BasicNameValuePair("above", id));
				
				json = super.makeHttpRequestGetResponse(url, POST, params);

				id = new String(json.getString("id"));
				name = new String(json.getString("name"));
				location = new String(json.getString("location"));
				description = new String(json.getString("description"));
				status = new String(json.getString("status"));
				reported_lost = new String(json.getString("reported_lost"));
				reported_found = new String(json.getString("reported_found"));
				owned_by = new String(json.getString("owned_by"));
				claimed_by = new String(json.getString("claimed_by"));
				found_by = new String(json.getString("found_by"));
				created_at = new String(json.getString("created_at"));
			
				if (ownedBy != null) {
					createProfile.loadAnItemProfile(id, name, location, description, status,
						reported_lost, reported_found, found_by, claimed_by, owned_by, created_at);
				
				} else if (itemName != null) {
					createProfile.addMockItemProfile(id, name, location, description, status,
							reported_lost, reported_found, found_by, claimed_by, owned_by,
							created_at);
				}
			}
		}
	}
	
	private int getNumberOfItems(String username, boolean recent, boolean search) throws Exception {
		
		sb = new StringBuilder();
		sb.append(WebServiceHandler.DOMAIN);
		sb.append(WebServiceHandler.FOLDER);
		sb.append(WebServiceHandler.NUMBER_OF_ITEMS);
		
		url = new String(sb.toString());
		
		params = new ArrayList<NameValuePair>();
		
		if (username != null && !search) {
			params.add(new BasicNameValuePair("username", username));
		} else if (username == null && recent) {
			params.add(new BasicNameValuePair("timestamp", "yes"));
		} else if (search) {
			params.add(new BasicNameValuePair("string", username));
		}
		
		json = super.makeHttpRequestGetResponse(url, POST, params);
		return json.getInt("items");
	}
}
