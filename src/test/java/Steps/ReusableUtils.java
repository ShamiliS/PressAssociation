package Steps;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

public class ReusableUtils extends CommonUtils {

	List<Map<String, String>> resdetails;

	public static void validateJSONschema(String schemaname, String filename)
			throws Exception {

		File schemaFile = new File("resources" + File.separator
				+ schemaname + ".json");
		File jsonFile = new File("resources" + File.separator + filename
				+ ".json");

		boolean jsonResponse = ValidationUtils
				.isJsonValid(schemaFile, jsonFile);
		System.out.println(jsonResponse);

		Assert.assertEquals("Validation of JSON response is successful", true,
				jsonResponse);
	}

	@Then("^I validate JSON reponse$")
	public void i_validate_JSON_reponse() throws Throwable {

		String json = EntityUtils.toString(response.getEntity(), "UTF-8");
		System.out.println(json);
		String key = null;
		JSONObject jObject = null;
		JSONArray jArray;

		try {
			jArray = new JSONArray(json);
			for (int i = 0; i < jArray.length(); i++) {
				jObject = (JSONObject) jArray.getJSONObject(i);
				Iterator iter = jObject.keys();
				while (iter.hasNext()) {
					key = (String) iter.next();
					try {
						if (!(jObject.getString(key)).equals("")) {
							Assert.assertEquals("Key " + key + "is not empty",
									"", "");
						}
					} catch (Exception e) {
						Integer version = jObject.getInt(key);
						if (version == null) {
							Assert.assertEquals("Key " + key + "is not empty",
									"", "");
						}
					}
				}
			}
		} catch (Exception e) {
			try {
				jObject = new JSONObject(json);
				Iterator iter = jObject.keys();
				while (iter.hasNext()) {
					key = (String) iter.next();
					try {
						if (!(jObject.getString(key)).equals("")) {
							Assert.assertEquals("Key " + key + "is not empty",
									"", "");
						}
					} catch (Exception e1) {
						Integer version = jObject.getInt(key);
						if (version == null) {
							Assert.assertEquals("Key " + key + "is not empty",
									"", "");
						}
					}
				}
			} catch (Exception ex) {
				try {

				} catch (Exception exp) {
					throw new ClientProtocolException("Malformed XML document",
							exp);
				}
			}
		}
	}

	@Then("^I compare resonse with valid schema \"(.*?)\"$")
	public void i_compare_resonse_with_valid_schema(String Schema)
			throws Throwable {
		String json = EntityUtils.toString(response.getEntity(), "UTF-8");
		System.out.println(json);
		FileWriter JSON_Response = new FileWriter("resources" + File.separator
				+ "JSON_Response" + ".json");

		JSON_Response.write(json);
		JSON_Response.close();

		ReusableUtils.validateJSONschema(Schema, "JSON_Response");
	}

	@Then("^I validate reponse code and message$")
	public void I_validate_reponse_code_and_message(DataTable data)
			throws Throwable {

		resdetails = data.asMaps(String.class, String.class);

		String resCode = resdetails.get(0).get("Response Code");
		int code = Integer.parseInt(resCode);
		String resMsg = resdetails.get(0).get("Response Message");

		StatusLine statusLine = response.getStatusLine();

		// Validate the Response Code
		Assert.assertEquals("Response Code is Valid", code,
				statusLine.getStatusCode());

		// Validate the Response Message
		Assert.assertEquals("Response Message", resMsg,
				statusLine.getReasonPhrase());
	}

	@And("^I validate the content of the response$")
	public void I_validate_the_content_of_the_response() throws Throwable {
		String json = EntityUtils.toString(response.getEntity(), "UTF-8");

		System.out.println(json);
	}

}
