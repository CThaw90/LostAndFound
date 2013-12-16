package ws;

public interface ReadInterface {

	public boolean checkLocalLogDatabase();
	
	public String loginWithCredentials(String username, String password) throws Exception;
	
	public void getUserProfile(String username, boolean mine) throws Exception;
	
	public void completeItemProfile(String owned_by, String name) throws Exception;
	
	public void getAllItems(String username) throws Exception;
	
	public void getRecentItemReports() throws Exception;
	
	public void searchItemReports(String string) throws Exception;
	
	public void replaceWithFoundItemProfile(String owned_by, String name) throws Exception;
}
