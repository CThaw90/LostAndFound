package ws;

public interface UpdateInterface {

	public String updateUserProfile(String name, String email, String username, 
			String address, String phoneNumber, String pictureUri) throws Exception;
	
	public void updateItemReport(String id, String name, String location, String description, boolean found, boolean claimed) throws Exception;
}
