package entities;

public interface CreateEntity {

	public void createBasicUserProfile(String email, String username, String password) throws Exception;
	
	public String saveUserProfile() throws Exception;
	
	public void createBasicLoginProfile(String username, String password);
	
	public void loadAUserProfile(String username) throws Exception;
	
	public String tryLoginProfile() throws Exception;
	
	public String createItemProfile(String name, String location, String description, String status) throws Exception;
	
	public String loadAllUserData(String username) throws Exception;
	
	public void loadAUserProfile(String id, String name, String email, String username, String address,
			String phone_number, String created_at, boolean mine) throws Exception;
	
	public void loadAnItemProfile(String id, String name, String location, String description, String status,
			String reported_lost, String reported_found, String found_by, String claimed_by, String owned_by, 
			String created_at) throws Exception;
	
	public void addMockItemProfile(String id, String name, String location, String description, 
			String status, String reported_lost, String reported_found, String found_by, 
			String claimed_by, String owned_by, String created_at);
	
	public void loadMockItemProfiles(boolean loadRecentReports, String string) throws Exception;
}
