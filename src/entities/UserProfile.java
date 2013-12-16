package entities;

import java.util.ArrayList;

class UserProfile {
	
	private ArrayList<ItemProfile> items = new ArrayList<ItemProfile>();
	
	private String id = new String();
	private String name = new String();
	private String email = new String();
	private String username = new String();
	private String password = new String();
	private String address = new String();
	private String phoneNumber = new String();
	private String profile_pic_url = new String(); 
	private String last_logged_in = new String();
	private String created_at = new String();
	
	// Sets the User Id (represented as an int)
	protected void setId(String id) {
		this.id = id;
	}
	
	// Gets the User Id (probably not necessarily needed)
	protected String getId() {
		return id;
	}
	
	// Sets the Users Name identifier
	protected void setName(String name) {
		this.name = name;
	}
	
	// Returns the Users Name
	protected String getName() {
		return name;
	}
	
	// Sets the Users Email Address
	protected void setEmail(String email) {
		this.email = email;
	}
	
	// Gets the Users Email Address
	protected String getEmail() {
		return email;
	}
	
	// Sets the username unique identifier
	protected void setUsername(String username) {
		this.username = username;
	}
	
	// Returns the username unique identifier
	protected String getUsername() {
		return username;
	}
	
	// Sets the Users password
	protected void setPassword(String password) {
		this.password = password;
	}
	
	// Gets the Users password (for comparison purposes)
	protected String getPassword() {
		return password;
	}
	
	// Sets the Users Street Address
	protected void setAddress(String address) {
		this.address = address;
	}
	
	// Returns the Users Street Address
	protected String getAddress() {
		return address;
	}
	
	// Sets the Users Phone Number
	protected void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	// Returns the Users Phone Number
	protected String getPhoneNumber() {
		return phoneNumber;
	}
	
	// Sets the Users Profile Pic Image Url (Maybe for Caching usages)
	protected void setProfilePicUrl(String profile_pic_url) {
		this.profile_pic_url = profile_pic_url;
	}
	
	// Returns the Profile Pic Image Url (Local Storage Usage)
	protected String getProfilePicUrl() {
		return profile_pic_url;
	}
	
	// Sets the Time the user was last logged on the application
	protected void setLastLoggedIn(String last_logged_in) {
		this.last_logged_in = last_logged_in;
	}
	
	// Gets the Time the user was last logged on the application
	protected String getLastLoggedIn() {
		return last_logged_in;
	}
	
	// Sets the time the user created this account
	protected void setCreatedAt(String created_at) {
		this.created_at = created_at;
	}
	
	// Returns the time the user created this account
	protected String getCreatedAt() {
		return created_at;
	}
	
	// Adds an Item to the static ArrayList of Items in a user inventory
	protected void addItem(ItemProfile item) {
		items.add(item);
	}
	
	// Returns the id of the specified Item
	protected String getItemId(int index) {
		return items.get(index).getId();
	}
	
	// Sets the id of the specified item
	protected void setItemId(int index, String id) {
		items.get(index).setId(id);
	}
	
	// Returns the specified Items name
	protected String getItemName(int index) {
		return items.get(index).getName();
	}
	
	// Sets a specified Item name
	protected void setItemName(int index, String name) {
		items.get(index).setName(name);
	}
	
	// Returns the specified Items location
	protected String getItemLocation(int index) {
		return items.get(index).getLocation();
	}
	
	// Sets a specified item Location
	protected void setItemLocation(int index, String location) {
		items.get(index).setLocation(location);
	}
	
	// Returns the specified Items description
	protected String getItemDescription(int index) {
		return items.get(index).getDescription();
	}
	
	// Sets a specified items description
	protected void setItemDescription(int index, String description) {
		items.get(index).setDescription(description);
	}
	
	// Returns the status of an item either Lost or Found
	protected String getItemStatus(int index) {
		return items.get(index).getStatus();
	}
	
	// Sets the specified items status
	protected void setItemStatus(int index, String status) {
		items.get(index).setStatus(status);
	}
	
	// Returns the time an item was reported Lost
	protected String getItemReportedLost(int index) {
		return items.get(index).getReportedLost();
	}
	
	// Sets when the specified item was reported lost
	protected void setItemReportedLost(int index, String reported_lost) {
		items.get(index).setReportedLost(reported_lost);
	}
	
	// Returns the time an item was reported found
	protected String getItemReportedFound(int index) {
		return items.get(index).getReportedFound();
	}
	
	// Sets when the specified item was reported found
	protected void setItemReportedFound(int index, String reported_found) {
		items.get(index).setReportedFound(reported_found);
	}
	
	// Returns the username of the user who owns the item
	protected String getItemOwnedBy(int index) {
		return items.get(index).getOwnedBy();
	}
	
	// Sets the username of the user who owns the item
	protected void setItemOwnedBy(int index, String owned_by) {
		items.get(index).setOwnedBy(owned_by);
	}
	
	// Sets the username of the user who claimed a specified item
	protected void setItemClaimedBy(int index, String claimed_by) {
		items.get(index).setClaimedBy(claimed_by);
	}
	
	// Returns the username of the user who claimed a specified item
	protected String getItemClaimedBy(int index) {
		return items.get(index).getClaimedBy();
	}
	
	// Returns the username of the user who found the item 
	protected String getItemFoundBy(int index) {
		return items.get(index).getFoundBy();
	}
	
	// Sets the username of the user who found the item
	protected void setItemFoundBy(int index, String found_by) {
		items.get(index).setFoundBy(found_by);
	}
	
	// Sets when the specified item report was created
	protected void setItemCreatedAt(int index, String created_at) {
		items.get(index).setCreatedAt(created_at);
	}
	
	// Deletes an item from the ArrayList
	protected void deleteItem(int index) {
		items.remove(index);
	}
	
	// Deletes an item referenced by its id
	protected void deleteItemById(String id) {
		
		int itemSize = items.size();
		for (int i=0; i < itemSize; i++) {
			
			if (items.get(i).getId().compareTo(id) == 0) {
				items.remove(i);
				break;
			}
		}
	}
	
	// Clear all items in the ArrayList (usually used at Login)
	protected void clearAllItems() {
		items.clear();
	}
	/**
	 * Returns an item from the static ArrayList with the specified
	 * name. If no names are matched the method returns null
	 * @param itemName
	 * @return */
	protected ItemProfile getItem(String itemName) {
		int index = getItemIndex(itemName);
		ItemProfile item = index != -1 ? items.get(index) : null;
		return item;
	}
	
	/**
	 * Returns all Items in the ArrayList
	 * @return */
	protected ArrayList<ItemProfile> getAllItems() {
		return items;
	}
	
	/**
	 * Method checks if Item with a given name exists in the ArrayList
	 * If name exists it returns true otherwise false
	 * @param itemName
	 * @return */
	protected boolean doesItemExist(String itemName) {
		return (getItemIndex(itemName) != -1); 
	}
	
	/**
	 * Returns the index of an item in the ArrayList.
	 * If Item name isn't found method returns -1
	 * @param itemName
	 * @return */
	protected int getItemIndex(String itemName) {
		
		int numberOfItems = items.size();
		int index = -1;
		for (int i=0; i < numberOfItems; i++) {
			
			if (itemName.compareToIgnoreCase(items.get(i).getName()) == 0) {
				index = i;
				break;
			}
		}
		return index;
	}
}
