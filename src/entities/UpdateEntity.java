package entities;

public interface UpdateEntity {
	
	public String updateUserProfile(String name, String email, String address, 
			String phoneNumber, String pictureUri) throws Exception;
	
	public void completeMyItem(String id, String name, String location, String description, 
			String status, String reported_lost, String reported_found, String found_by, 
			String owned_by, String created_at);

	public void updateItemReportToFound(String id) throws Exception;
	
	public void updateItemReport(String name, String location, String description, int index) throws Exception;
	
	public void updateItemReportToClaimed(String id, String username) throws Exception;
}
