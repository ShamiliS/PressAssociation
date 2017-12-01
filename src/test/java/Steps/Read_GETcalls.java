package Steps;

import java.util.Map;

import org.apache.http.HttpResponse;

import cucumber.api.java.en.Given;

public class Read_GETcalls extends CommonUtils {

	static HttpResponse response;
	Map<String, String> userDetails;

	/**
	 * Triggering the GET method and storing the response in variable
	 * 'APIresponse'
	 * 
	 * @throws Exception
	 */

	@Given("^rest endpoint URL and API resource for \"(.*?)\"$")
	public void rest_endpoint_URL_and_API_resource_for(String type)
			throws Exception {

		userDetails = CommonUtils.readDatafromXMLFile(type);

		String baseURL = userDetails.get("endpoint");
		String resource = userDetails.get("resource");
		String endpnt = baseURL + "" + resource;
		response = CommonUtils.GET(endpnt);
	}

	@Given("^rest endpoint URL, API resource and valid ID for \"(.*?)\"$")
	public void rest_endpoint_URL_API_resource_and_valid_ID_for(String type)
			throws Throwable {
		userDetails = CommonUtils.readDatafromXMLFile(type);

		String baseURL = userDetails.get("endpoint");
		String resource = userDetails.get("resource");
		String validID;

		validID = getValidID(baseURL, resource, 1);

		String endpnt = baseURL + "" + resource + "/" + validID;
		System.out.println(endpnt);
		response = CommonUtils.GET(endpnt);
	}

}
