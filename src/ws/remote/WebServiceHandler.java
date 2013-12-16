package ws.remote;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

/**
 * This WebServiceHandler.java class is responsible for sending data to the
 * online Webserver and distributing the Http Responses to the appropriate
 * class entities in the form of a JSONObject class
 * @author Chris
 *
 */

abstract class WebServiceHandler {
	
	protected static boolean debugOn = false;
	
	protected static final String DOMAIN = new String("http://www.askmusicians.x10host.com/");
	protected static final String FOLDER = new String("LostAndFoundApp/");
	
	protected static final String NUMBER_OF_ITEMS = new String("number_of_items.php");
	protected static final String GET_USER_PROFILE = new String("get_user_profile.php");
	protected static final String CHECK_USERNAME = new String("check_username.php");
	protected static final String GET_ITEM_REPORT = new String("get_item_report.php");
	protected static final String DELETE_ITEM = new String("delete_item_report.php");
	protected static final String UPDATE_ITEM = new String("update_item_report.php"); /* NEW */
	protected static final String REPORT_ITEM = new String("report_item.php"); /* NEW */
	protected static final String UPLOAD_IMG = new String("upload_image.php");
	protected static final String REGISTER_USER = new String("register.php");
	protected static final String UPDATE_INFO = new String("update_info.php");
	protected static final String LOGIN_USER = new String("login_user.php");
	protected static final String EDIT_ITEM = new String("edit_item.php"); /* NEW */
	
	protected static final String POST = new String("POST");
	protected static final String GET = new String("GET");
	
	private InputStream inputStream;
	
	protected JSONObject makeHttpRequestGetResponse(String url, String method, 
			List<NameValuePair> params) throws Exception {
		
		JSONObject jsonResponse = null;
		
		if (method.compareToIgnoreCase(POST) == 0) {
			
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			inputStream = httpEntity.getContent();
			
		} else if (method.compareToIgnoreCase(GET) == 0) {
			
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,
				"iso-8859-1"), 8);
		
		StringBuilder sb = new StringBuilder();
		String line = new String();
		
		while ((line = reader.readLine()) != null) {
		//	if (line.contains("{") || line.contains("}")) {
			sb.append(line);
		//	}
		}
		
		inputStream.close(); 
		
		jsonResponse = new JSONObject(sb.toString());
		
		return jsonResponse;
	}
}
