package entities;

import java.util.ArrayList;

import util.DatabaseHandler;
import util.MockItemProfile;
import util.MockUserProfile;
import ws.CreateInterface;
import ws.DeleteInterface;
import ws.ReadInterface;
import ws.UpdateInterface;

public abstract class ProxyProfile {
	
	private final String INVALID = new String("Invalid Username or Password!");
	private final String VALID = new String("Welcome to LostAndFound!");
	private static UserProfile MAIN_USER = new UserProfile();
	
	private static MockUserProfile otherUsers; 
	private ItemProfile item;
	
	private static ArrayList<MockItemProfile> itemsData;
	private static ArrayList<MockItemProfile> reports;
	private MockItemProfile mip;
	
	private static boolean CLEAR_TO_CREATE;
	
	private String nil = new String("nil");
	
	private CreateInterface cI;
	private ReadInterface rI;
	private UpdateInterface uI;
	private DeleteInterface dI;

	/**
	 * Creates a User Profile Entity for looking up a record or saving
	 * a record in the local and remote database
	 * @param name
	 * @param email
	 * @param username
	 * @param password
	 */
	public void createBasicUserProfile(String email, String username, String password) throws Exception {

		cI = new DatabaseHandler();
		if (cI.doesUsernameExist(username)) {
			CLEAR_TO_CREATE = false;
			
		} else {
			
			CLEAR_TO_CREATE = true;
			MAIN_USER = new UserProfile();
			MAIN_USER.clearAllItems();
			MAIN_USER.setUsername(username);
			MAIN_USER.setPassword(password);
			MAIN_USER.setEmail(email);
			MAIN_USER.setPhoneNumber(nil);
			MAIN_USER.setAddress(nil);
			MAIN_USER.setName(nil);
		}
	}
	
	/**
	 * Sets more detailed information about the user
	 * @param address
	 * @param phoneNumber
	 * @param profilePicUrl
	 */
	public void createDetailedUserProfile(String address, String phoneNumber, 
			String profilePicUrl) {
		
		MAIN_USER.setAddress(address);
		MAIN_USER.setPhoneNumber(phoneNumber);
		MAIN_USER.setProfilePicUrl(profilePicUrl);
	}
	
	/**
	 * Saves the new User account to the remote database or returns
	 * the Username already Exists return string
	 * @return status of the transaction in String
	 * @throws Exception
	 */
	public String saveUserProfile() throws Exception {
		
		String returnString = new String();
		returnString = (CLEAR_TO_CREATE) ? cI.createUserAccount(MAIN_USER.getEmail(), 
				MAIN_USER.getUsername(), MAIN_USER.getPassword()) : "Username already Exists!";
		return returnString;
	}
	/**
	 * Creates a basic USerProfile entity to login with
	 * @param username
	 * @param password
	 */
	public void createBasicLoginProfile(String username, String password) {
		MAIN_USER = new UserProfile();
		MAIN_USER.clearAllItems();
		MAIN_USER.setUsername(username);
		MAIN_USER.setPassword(password);
	}
	
	/**
	 * Attempts to log a user into the Application with 
	 * the credentials data they entered at the LoginActivity
	 * screen
	 * @return
	 * @throws Exception
	 */
	public String tryLoginProfile() throws Exception {
		rI = new DatabaseHandler();
		String result = new String(rI.loginWithCredentials(MAIN_USER.getUsername(), MAIN_USER.getPassword()));
		
		if (result.compareToIgnoreCase(VALID) == 0) {
			rI.getUserProfile(MAIN_USER.getUsername(), true);
			
		} else if (result.compareToIgnoreCase(INVALID) == 0) {
			MAIN_USER.setUsername("");
			MAIN_USER.setPassword("");
		}
		return result;
	}
	
	/**
	 * Updates the current User Profile stored in static memory
	 * @param name
	 * @param email
	 * @param address
	 * @param phoneNumber
	 */
	public String updateUserProfile(String name, String email, String address, 
			String phoneNumber, String pictureUri) throws Exception {
		MAIN_USER.setPhoneNumber((phoneNumber.length() > 0) ? phoneNumber : nil);
		MAIN_USER.setAddress((address.length() > 0) ? address : nil);
		MAIN_USER.setEmail((email.length() > 0) ? email : nil);
		MAIN_USER.setName((name.length() > 0) ? name : nil);
		
		uI = new DatabaseHandler();
		return uI.updateUserProfile(name, email, getUsername(), address, phoneNumber, pictureUri);
	}
	
	/**
	 * Creates a basic Item Profile to add to the ArrayList of 
	 * items in the UserProfile class. Returns exists if an item
	 * of the same name already exists.
	 * @param name
	 * @param location
	 * @param description
	 * @param status
	 * @return
	 * @throws Exception */
	public String createItemProfile(String name, String location, String description, String status) throws Exception {
		String ret = new String(MAIN_USER.doesItemExist(name) ? "Item Report Exists!" : "Item Report Created!");
		
		if (ret.compareToIgnoreCase("Item Report Exists!") != 0) {
			item = new ItemProfile();
			item.setName(name);
			item.setLocation(location);
			item.setDescription(description);
			item.setStatus(status);
			item.setFoundBy(status.compareTo("Lost") != 0 ? MAIN_USER.getUsername() : "nil");
			item.setOwnedBy(MAIN_USER.getUsername());
			MAIN_USER.addItem(item);
			// TODO Code algorithms for queries to the backend server
			cI = new DatabaseHandler();
			ret = cI.createRemoteItemRecord(MAIN_USER.getUsername(), name, location, description, status);
			
			if (ret != null) {
				rI = new DatabaseHandler();
				rI.completeItemProfile(MAIN_USER.getName(), name);
				
				ret = new String("Item Report Created!");
				
			} else { throw new Exception(); }
		}
		return ret;
	}
	
	/**
	 * Loads all the User data from the remote database
	 * on initial login
	 * @param username
	 * @return
	 * @throws Exception*/
	public String loadAllUserData(String username) throws Exception {
		rI = new DatabaseHandler();
		rI.getUserProfile(username, true);
		rI.getAllItems(username);
		return new String("completed!");
	}
	
	public void loadAUserProfile(String id, String name, String email, String username, String address, 
			String phone_number, String created_at, boolean mine) throws Exception {
		
		if (mine) {
			MAIN_USER.setId(id);
			MAIN_USER.setName(name);
			MAIN_USER.setEmail(email);
			MAIN_USER.setUsername(username);
			MAIN_USER.setAddress(address);
			MAIN_USER.setPhoneNumber(phone_number);
			MAIN_USER.setCreatedAt(created_at);
			
		} else {
			otherUsers = new MockUserProfile();
			otherUsers.id = id;
			otherUsers.name = name;
			otherUsers.email = email;
			otherUsers.username = username;
			otherUsers.address = address;
			otherUsers.phoneNumber = phone_number;
			otherUsers.created_at = created_at;
		}
	}
	
	public void loadAUserProfile(String username) throws Exception {
		rI = new DatabaseHandler();
		rI.getUserProfile(username, false);
	}
	
	public void loadAnItemProfile(String id, String name, String location, String description, String status,
			String reported_lost, String reported_found, String found_by, String claimed_by, String owned_by, 
			String created_at) throws Exception {
		
		item = new ItemProfile();
		item.setId(id);
		item.setName(name);
		item.setLocation(location);
		item.setDescription(description);
		item.setStatus(status);
		item.setReportedLost(reported_lost);
		item.setReportedFound(reported_found);
		item.setFoundBy(found_by);
		item.setClaimedBy(claimed_by);
		item.setOwnedBy(owned_by);
		item.setCreatedAt(created_at);
		
		MAIN_USER.addItem(item);
	}
	
	/**
	 * Completes the item profile of a recently reported item. 
	 * Adding reported_found, reported_lost, owned_by, found_by
	 * created_at
	 * @param id
	 * @param name
	 * @param location
	 * @param description
	 * @param status
	 * @param reported_lost
	 * @param reported_found
	 * @param found_by
	 * @param owned_by
	 * @param created_at */
	public void completeMyItem(String id, String name, String location, String description, String status,
			String reported_lost, String reported_found, String found_by, String owned_by, String created_at) {
		
		int index = MAIN_USER.getItemIndex(name);
		index  = (index >= 0) ? index : 0;
		
		MAIN_USER.setItemId(index, id);
		MAIN_USER.setItemReportedFound(index, reported_found);
		MAIN_USER.setItemReportedLost(index, reported_lost);
		MAIN_USER.setItemOwnedBy(index, owned_by);
		MAIN_USER.setItemFoundBy(index, found_by);
		MAIN_USER.setItemCreatedAt(index, created_at);
	}
	
	// Returns the Users name
	public String getUserName() {
		return MAIN_USER.getName();
	}
	
	// Returns the Users Email Address
	public String getUserEmail() {
		return MAIN_USER.getEmail();
	}
	
	// Returns the Users username
	public String getUsername() {
		return MAIN_USER.getUsername();
	}
	
	// Returns the Users Street Address
	public String getUserAddress() {
		return MAIN_USER.getAddress();
	}
	
	// Returns the Users Phone Number
	public String getUserPhoneNumber() {
		return MAIN_USER.getPhoneNumber();
	}
	
	// Returns the Id of an item
	public String getItemId(int index) {
		return MAIN_USER.getItemId(index);
	}
	
	// Returns a given Item name
	public String getItemName(int index) {
		return MAIN_USER.getItemName(index);
	}
	
	// Returns a given Item Location
	public String getItemLocation(int index) {
		return MAIN_USER.getItemLocation(index);
	}
	
	// Returns a given Item Description
	public String getItemDescription(int index) {
		return MAIN_USER.getItemDescription(index);
	}
	
	// Returns a given Item Status either lost or found
	public String getItemStatus(int index) {
		return MAIN_USER.getItemStatus(index);
	}
	
	// Returns when an item was reported lost
	public String getItemReportedLost(int index) {
		return MAIN_USER.getItemReportedLost(index);
	}
	
	// Returns when an item was reported found
	public String getItemReportedFound(int index) {
		return MAIN_USER.getItemReportedFound(index);
	}
	
	// Returns the username of the user who found the item
	public String getItemFoundBy(int index) {
		return MAIN_USER.getItemFoundBy(index);
	}
	
	// Returns the username of the user who owns the item
	public String getItemOwnedBy(int index) {
		return MAIN_USER.getItemOwnedBy(index);
	}
	
	// Returns the username of the user who claimed the item
	public String getItemClaimedBy(int index) {
		return MAIN_USER.getItemClaimedBy(index);
	}
	
	// Returns the time this item report was created
	public String getItemCreatedAt(int index) {
		return MAIN_USER.getCreatedAt();
	}
	
	/**
	 * Returns all important data to an Activity via a
	 * MockItemProfile class 
	 * @return  */
	public ArrayList<MockItemProfile> getAllMyItemsData() {
		
		if (itemsData != null) {
			itemsData.clear();
		}
		
		int numberOfItems =  MAIN_USER.getAllItems().size();
		itemsData = new ArrayList<MockItemProfile>();
		
		for (int i=0; i < numberOfItems; i++) {
			mip = new MockItemProfile();
			mip.name = getItemName(i);
			mip.location = getItemLocation(i);
			mip.description = getItemDescription(i);
			mip.status = getItemStatus(i);
			mip.reported_lost = getItemReportedLost(i);
			mip.reported_found = getItemReportedFound(i);
			mip.claimed_by = getItemClaimedBy(i);
			mip.found_by = getItemFoundBy(i);
			mip.owned_by = getItemOwnedBy(i);
			itemsData.add(mip);
		}
		
		return itemsData;
	}
	
	/**
	 * Adds a full MockItemProfile to the ArrayList in preparation
	 * for passing all the data to the waiting Activities
	 * @param id
	 * @param name
	 * @param location
	 * @param description
	 * @param status
	 * @param reported_lost
	 * @param reported_found
	 * @param found_by
	 * @param owned_by
	 * @param created_at
	 */
	public void addMockItemProfile(String id, String name, String location, String description, 
			String status, String reported_lost, String reported_found, String found_by, 
			String claimed_by, String owned_by, String created_at) {
		
		if (reports == null) {
			reports = new ArrayList<MockItemProfile>();
		}
		
		mip = new MockItemProfile();
		mip.id = id;
		mip.name = name;
		mip.location = location;
		mip.description = description;
		mip.status = status;
		mip.reported_lost = reported_lost;
		mip.reported_found = reported_found;
		mip.found_by = found_by;
		mip.claimed_by = claimed_by;
		mip.owned_by = owned_by;
		mip.created_at = created_at;
		
		reports.add(mip);
	}
	
	/**
	 * Loads the static ArrayList MockItemProfile for a listing
	 * display on either of the List activities
	 * @param loadRecentReports
	 * @param loadTextSimilarity
	 * @throws Exception */
	public void loadMockItemProfiles(boolean loadRecentReports, String string) throws Exception {
		
		if (reports != null) { reports.clear(); }
		rI = new DatabaseHandler();
		
		if (loadRecentReports){ rI.getRecentItemReports(); } 
		else { 
			rI.searchItemReports(string); 
		}
	}
	
	/**
	 * Returns the loaded ArrayList of MockItemProfiles to the
	 * user
	 * @return */
	public ArrayList<MockItemProfile> getLoadedMockItemProfiles() {
		return (reports != null) ? reports : new ArrayList<MockItemProfile>();
	}
	
	public MockUserProfile getLoadedMockUserProfile() {
		return otherUsers;
	}
	
	/**
	 * Updates an item record and reports it found
	 * @param id
	 * @throws Exception
	 */
	public void updateItemReportToFound(String id) throws Exception {
		
		int mipSize = reports.size();
		
		for (int i=0; i < mipSize; i++) {
			
			if (reports.get(i).id.compareTo(id) == 0) {
				mip = reports.get(i);
				reports.remove(i);
				break;
			}
		}
		
		uI = new DatabaseHandler();
		uI.updateItemReport(id, MAIN_USER.getUsername(), null, null, true, false);
		
		rI = new DatabaseHandler();
		rI.replaceWithFoundItemProfile(mip.owned_by, mip.name);
	}
	
	public void updateItemReportToClaimed(String id, String username) throws Exception {
		
		int mipSize = reports.size();
		
		for (int i=0; i < mipSize; i++) {
			
			if (reports.get(i).id.compareTo(id) == 0) {
				mip = reports.get(i);
				reports.remove(i);
				break;
			}
		}
		
		uI = new DatabaseHandler();
		uI.updateItemReport(id, MAIN_USER.getUsername(), null, null, false, true);
		
		rI = new DatabaseHandler();
		rI.replaceWithFoundItemProfile(mip.owned_by, mip.name);
	}
	
	public void updateItemReport(String name, String location, String description, int index) throws Exception {
		
		item = MAIN_USER.getAllItems().get(index);
		MAIN_USER.getAllItems().get(index).setName(name);
		MAIN_USER.getAllItems().get(index).setLocation(location);
		MAIN_USER.getAllItems().get(index).setDescription(description);
		
		uI = new DatabaseHandler();
		uI.updateItemReport(item.getId(), name, location, description, false, false);
	}
	
	/**
	 * Deletes an item referenced by its unique string id
	 * @param id
	 * @throws Exception
	 */
	public void deleteItem(String id) throws Exception {
		MAIN_USER.deleteItemById(id);
		dI = new DatabaseHandler();
		dI.deleteAnItem(id);
	}
}
