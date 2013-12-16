	package ws.remote;

public class RemoteDbClass {

	/**
	 * Returns True if the Username Entered by the user is available for use
	 * @param username -- the user name being queried
	 * @return
	 * @throws Exception
	 */
	public boolean checkUsernames(String username) throws Exception {
		boolean ret = new ReadRecordRemote().checkIfUsernameAvailable(username);
		return ret;
	}
	
	/**
	 * Method is responsible for creating the initial user account 
	 * with the following parameters. User will add more detailed 
	 * information after the initial sign in.
	 * @param email
	 * @param username
	 * @param password
	 */
	public String createUserAccount(String email, String username, String password) throws Exception {
		return (new CreateRecordRemote().createNewUserAccount(email, username, password));
	}
	
	/**
	 * Logs user into their account. If Credentials are valid the user is taken
	 * to the MainMenuActivity screen.
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public String userAccountLogin(String username, String password) throws Exception {
		return new ReadRecordRemote().attemptUserLogin(username, password);
	}
	
	public void extractAndStoreUserProfile(String username, boolean mine) throws Exception {
		new ReadRecordRemote().extractAndStoreUserProfile(username, mine);
	}
	
	public String pushUpdatedUserProfile(String name, String email, String username, 
			String address, String phoneNumber, String pictureUri) throws Exception {
		
		return new UpdateRecordRemote().pushUserProfileRemote(name, email, username, 
				address, phoneNumber, pictureUri);
	}
	
	public String newItemReport(String username, String name, String location, String description, String status) throws Exception {
		return new CreateRecordRemote().createNewItemProfile(username, name, location, description, status);
	}
	
	public void completeItemProfile(String owned_by, String name) throws Exception {
		new ReadRecordRemote().getItemProfile(owned_by, name, ReadRecordRemote.update);
	}
	
	public void loadAllItemsToProfile(String owned_by) throws Exception {
		new ReadRecordRemote().getItemProfile(owned_by, null, ReadRecordRemote.load);
	}
	
	public void loadRecentItemReports() throws Exception {
		new ReadRecordRemote().getItemProfile(null, "time", ReadRecordRemote.load);
	}
	
	public void searchItemReports(String string) throws Exception {
		new ReadRecordRemote().getItemProfile(null, string, ReadRecordRemote.search);
	}
	
	public void updateItemReport(String id, String name, String location, 
			String description, boolean found, boolean claimed) throws Exception {
		new UpdateRecordRemote().pushItemUpdateRemote(id, name, location, description, found, claimed);
	}
	
	public void getUpdatedItemReport(String ownedBy, String itemName) throws Exception {
		new ReadRecordRemote().getItemProfile(ownedBy, itemName, ReadRecordRemote.found);
	}
	
	public void deleteAnItem(String id) throws Exception {
		new DeleteRecordRemote().deleteItem(id);
	}
}
