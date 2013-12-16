package ws;

import android.content.Context;

import android.util.Log;

import ws.remote.RemoteDbClass;

public abstract class DatabaseProxy {
	
	private final static String LogTag = new String("DatabaseProxy");
	private final static String DB_NAME = new String("lostandfound.db");
	
	private final static int DB_VERSION = 1;
	
	private DatabaseHelper dbHelper;

	public void createNewLocalDatabase(Context context, String createString) {
		Log.i(LogTag, "DatabaseHelper instantiated");
		dbHelper = new DatabaseHelper(context, DB_NAME, DB_VERSION);
		dbHelper.prepareTable(createString);
		dbHelper.openDatabase();
		dbHelper.closeDatabase();
	}
	
	// Checks if the user name entered by the user is available for use
	// Returns false if the user name does not exist and true if it does
	public boolean doesUsernameExist(String username) throws Exception {
		boolean ret = (new RemoteDbClass().checkUsernames(username)) ? false : true;
		return ret;
	}
	
	/**
	 * Creates a User Account that is saved to the Remote Database
	 * @param email
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception */
	public String createUserAccount(String email, String username, String password) throws Exception {
		return (new RemoteDbClass().createUserAccount(email, username, password));
	}
	
	// Returns the status of the db that tracks current usage
	public boolean checkLocalLogDatabase() {
		Log.i(LogTag, "Checking if a Local Database already exists!");
		return DatabaseHelper.checkLogDatabase(DB_NAME);
	}
	
	/**
	 * Logs User in with the Credentials given at the LoginActivity screen
	 * If the information entered is valid user is taken to the MainMenuActivity
	 * screen
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception */
	public String loginWithCredentials(String username, String password) throws Exception {
		return (new RemoteDbClass().userAccountLogin(username, password));
	}
	
	/**
	 * Extracts the User Profile from the Database and
	 * Stores it into the static User Entity variable
	 * @param username
	 * @throws Exception */
	public void getUserProfile(String username, boolean mine) throws Exception {
		new RemoteDbClass().extractAndStoreUserProfile(username, mine);
	}
	
	public void getAllItems(String username) throws Exception {
		new RemoteDbClass().loadAllItemsToProfile(username);
	}

	/**
	 * Updates the user profile with the passed parameter data
	 * @param name
	 * @param email
	 * @param username
	 * @param address
	 * @param phoneNumber
	 * @return
	 * @throws Exception */
	public String updateUserProfile(String name, String email, String username, 
			String address, String phoneNumber, String pictureUri) throws Exception {
		return new RemoteDbClass().pushUpdatedUserProfile(name, email, username, 
				address, phoneNumber, pictureUri);
	}
	
	/**
	 * Creates a new Item Profile in the remote database for
	 * the current user logged in
	 * @param name
	 * @param location
	 * @param description
	 * @param status
	 * @throws Exception */
	public String createRemoteItemRecord(String username, String name, String location, String description, String status) throws Exception {
		return new RemoteDbClass().newItemReport(username, name, location, description, status);
	}
	
	/**
	 * Extracts the rest of the item profile which was generated 
	 * in the database and updates the items ArrayList
	 * @param owned_by
	 * @param name
	 * @throws Exception
	 */
	public void completeItemProfile(String owned_by, String name) throws Exception {
		new RemoteDbClass().completeItemProfile(owned_by, name);
	}
	
	public void getRecentItemReports() throws Exception {
		new RemoteDbClass().loadRecentItemReports();
	}
	
	public void searchItemReports(String string) throws Exception {
		new RemoteDbClass().searchItemReports(string);
	}
	
	public void updateItemReport(String id, String name, String location, 
			String description, boolean found, boolean claimed) throws Exception {
		new RemoteDbClass().updateItemReport(id, name, location, description, found, claimed);
	}
	
	public void replaceWithFoundItemProfile(String owned_by, String name) throws Exception {
		new RemoteDbClass().getUpdatedItemReport(owned_by, name);
	}
	
	public void deleteAnItem(String id) throws Exception {
		new RemoteDbClass().deleteAnItem(id);
	}
}
