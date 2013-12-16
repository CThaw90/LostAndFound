package entities;

class ItemProfile {
	
	private String id = new String();
	private String name = new String();
	private String location = new String();
	private String description = new String();
	private String picture_url = new String();
	private String status = new String();
	private String reported_lost = new String();
	private String reported_found = new String();
	private String owned_by = new String();
	private String claimed_by = new String();
	private String found_by = new String();
	private String created_at = new String();
	
	// Sets the items id value
	protected void setId(String id) {
		this.id = id;
	}
	
	// Returns the items id value
	protected String getId() {
		return id;
	}
	
	// Sets the items name value
	protected void setName(String name) {
		this.name = name;
	}
	
	// Returns the items name value
	protected String getName() {
		return name;
	}
	
	// Sets the items location value
	protected void setLocation(String location) {
		this.location = location;
	}
	
	// Returns the items location value
	protected String getLocation() {
		return location;
	}
	
	// Sets the items Description value
	protected void setDescription(String description) {
		this.description = description;
	}
	
	// Returns the items Description Value
	protected String getDescription() {
		return description;
	}
	
	// Sets the items status value either found or lost
	protected void setStatus(String status) {
		this.status = status;
	}
	
	// Returns the items status value either lost or found
	protected String getStatus() {
		return status;
	}
	
	// Sets the url where the item picture is located
	protected void setPictureUrl(String picture_url) {
		this.picture_url = picture_url;
	}
	
	// Returns the url where the item picture is located
	protected String getPictureUrl() {
		return picture_url;
	}
	
	// Sets when the item was reported lost
	protected void setReportedLost(String reported_lost) {
		this.reported_lost = reported_lost;
	}
	
	// Returns when the item was reported lost
	protected String getReportedLost() {
		return reported_lost;
	}
	
	// Sets when an item was reported found
	protected void setReportedFound(String reported_found) {
		this.reported_found = reported_found;
	}
	
	// Returns when an item was reported found
	protected String getReportedFound() {
		return reported_found;
	}
	
	// Sets the username of the owner of the item
	protected void setOwnedBy(String owned_by) {
		this.owned_by = owned_by;
	}
	
	// Returns the username of the owner of the item
	protected String getOwnedBy() {
		return owned_by;
	}
	
	// Sets the username of the user who claimed the item
	protected void setClaimedBy(String claimed_by) {
		this.claimed_by = claimed_by;
	}
	
	// Returns the username of the user who claimed the item
	protected String getClaimedBy() {
		return claimed_by;
	}
	
	// Sets the username of the finder of the item
	protected void setFoundBy(String found_by) {
		this.found_by = found_by;
	}
	
	// Returns the username of the finder of the item
	protected String getFoundBy() {
		return found_by;
	}
	
	// Sets the time this item report was created
	protected void setCreatedAt(String created_at) {
		this.created_at = created_at;
	}
	
	// Returns the time this item report was created
	protected String getCreatedAt() {
		return created_at;
	}
}
