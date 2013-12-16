package ws.remote;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

class CreateRecordRemote extends WebServiceHandler {
	
	private String success = new String("success");
	private List<NameValuePair> params;
	private StringBuilder sb;
	private String url;

	/**
	 * Creates a new User account with the following parameters
	 * @param email
	 * @param username
	 * @param password
	 * @return String extracted from JSON Object
	 * @throws Exception used to detect if a Network Connection is stable
	 */
	public String createNewUserAccount(String email, String username, String password) throws Exception {
		
		sb = new StringBuilder();
		sb.append(WebServiceHandler.DOMAIN);
		sb.append(WebServiceHandler.FOLDER);
		sb.append(WebServiceHandler.REGISTER_USER);
		
		url = new String(sb.toString());
		
		params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));

		JSONObject json = super.makeHttpRequestGetResponse(url, POST, params);
		
		return json.getString("message");
	}
	/**
	 * Creates a new Item report record in the remote database
	 * @param name
	 * @param location
	 * @param description
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public String createNewItemProfile(String username, String name, String location, String description, String status) throws Exception {
		
		sb = new StringBuilder();
		sb.append(WebServiceHandler.DOMAIN);
		sb.append(WebServiceHandler.FOLDER);
		sb.append(WebServiceHandler.REPORT_ITEM);
		
		url = new String(sb.toString());
		
		params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("location", location));
		params.add(new BasicNameValuePair("description", description));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("status", status));
		
		JSONObject json = super.makeHttpRequestGetResponse(url, POST, params);
		return (json.getInt(success) == 1) ? success : null;
	}
}
