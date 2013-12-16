package ws;

import android.content.Context;

public interface CreateInterface {
	
	public void createNewLocalDatabase(Context context, String createString);

	public boolean doesUsernameExist(String username) throws Exception;
	
	public String createUserAccount(String email, String username, String password)throws Exception;
	
	public String createRemoteItemRecord(String username, String name, String location, String description, String status)throws Exception;
}
