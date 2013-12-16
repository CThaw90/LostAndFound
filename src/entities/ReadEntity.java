package entities;

import java.util.ArrayList;

import util.MockItemProfile;
import util.MockUserProfile;

public interface ReadEntity {

	public String getUserName();
	public String getUserEmail();
	public String getUsername();
	public String getUserAddress();
	public String getUserPhoneNumber();

	public String getItemId(int index);
	public String getItemName(int index);
	public String getItemLocation(int index);
	public String getItemDescription(int index);
	public String getItemStatus(int index);
	public String getItemFoundBy(int index);
	public String getItemClaimedBy(int index);
	public String getItemOwnedBy(int index);
	public String getItemReportedLost(int index);
	public String getItemReportedFound(int index);
	public String getItemCreatedAt(int index);
	public MockUserProfile getLoadedMockUserProfile();
	public ArrayList<MockItemProfile> getAllMyItemsData();
	public ArrayList<MockItemProfile> getLoadedMockItemProfiles();
}
