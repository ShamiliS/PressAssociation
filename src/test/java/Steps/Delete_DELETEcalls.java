package Steps;

import java.util.Map;

import cucumber.api.java.en.Given;

public class Delete_DELETEcalls extends CommonUtils {

	Map<String, String> userDetails;
	static String validID = null;

	/**
	 * Delete specific song
	 */
	@Given("^I delete a request for \"(.*?)\"$")
	public void i_delete_a_request(String type) throws Throwable {
		userDetails = CommonUtils.readDatafromXMLFile(type);

		String baseURL = userDetails.get("endpoint");
		String resource = userDetails.get("resource");

		validID = getValidID(baseURL, resource,0);
		response = CommonUtils.DELETE(baseURL, resource, validID);
	}
}
