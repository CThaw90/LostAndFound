package ws.remote;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

class DeleteRecordRemote extends WebServiceHandler {
	
	private String id = new String("id");
	private List<NameValuePair> params;
	private StringBuilder sb; 
	private String url;

	protected void deleteItem(String id) throws Exception {
		
		sb = new StringBuilder();
		sb.append(WebServiceHandler.DOMAIN);
		sb.append(WebServiceHandler.FOLDER);
		sb.append(WebServiceHandler.DELETE_ITEM);
		
		url = new String(sb.toString());
		
		params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(this.id, id));
		
		super.makeHttpRequestGetResponse(url, POST, params);
	}
}
