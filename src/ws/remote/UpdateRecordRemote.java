package ws.remote;

import java.util.ArrayList;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class UpdateRecordRemote extends WebServiceHandler {

	private JSONObject json;
	
	private List<NameValuePair> params;
	private StringBuilder sb;

	/**
	 * Pushes the new User Profile Information to the server
	 * and finalizes updates to the remote database
	 * @param name
	 * @param email
	 * @param username
	 * @param address
	 * @param phoneNumber
	 * @return
	 * @throws Exception
	 */
	protected String pushUserProfileRemote(String name, String email, String username, 
			String address, String phoneNumber, String pictureUri) throws Exception {
		
		sb = new StringBuilder();
		sb.append(WebServiceHandler.DOMAIN);
		sb.append(WebServiceHandler.FOLDER);
		sb.append(WebServiceHandler.UPDATE_INFO);
		
		String url = new String(sb.toString());
		
		params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("address", address));
		params.add(new BasicNameValuePair("number", phoneNumber));
		
		if (pictureUri != null) {
			
		}
		
		json = super.makeHttpRequestGetResponse(url, "POST", params);
		
		return json.getString("message");
	}
	
	protected void pushItemUpdateRemote(String id, String name, String location, 
			String description, boolean found, boolean claimed) throws Exception {
		
		sb = new StringBuilder();
		sb.append(WebServiceHandler.DOMAIN);
		sb.append(WebServiceHandler.FOLDER);
		sb.append(WebServiceHandler.UPDATE_ITEM);
		
		String url = new String(sb.toString());
		
		if (found) {
			
			params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", id));
			params.add(new BasicNameValuePair("found_by", name));
			params.add(new BasicNameValuePair("status", name));
			
			json = super.makeHttpRequestGetResponse(url, POST, params);
			
			if (json.getInt("success") != 1) {
				throw new Exception("JSON gave unsuccessful error");
			}
			
		} else if (claimed) {
			
			params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", id));
			params.add(new BasicNameValuePair("status", name));
			params.add(new BasicNameValuePair("claimed_by", name));
			
			json = super.makeHttpRequestGetResponse(url, POST, params);
			
			if (json.getInt("success") != 1) {
				throw new Exception("JSON gave unsuccessful error");
			}
			
		} else {
			
			params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", id));
			params.add(new BasicNameValuePair("name", name));
			params.add(new BasicNameValuePair("location", location));
			params.add(new BasicNameValuePair("description", description));
			
			json = super.makeHttpRequestGetResponse(url, POST, params);
			
			if (json.getInt("success") != 1) {
				throw new Exception("JSON gave unsuccessful error");
			}
		}
	}
}
