package Steps;

import cucumber.api.java.en.Given;

import java.io.*;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

public class Update_UpdateCalls extends CommonUtils {

	String file;
	Map<String, String> userDetails;

	/**
	 * PUT Request to update song
	 */
	@Given("^I update a request for \"(.*?)\" and \"(.*?)\"$")
	public void i_update_a_request_for(String type, String xmlfile)
			throws Throwable {
		file = System.getProperty("user.dir") + "\\TestData\\" + type + "\\"
				+ xmlfile + ".xml";
		userDetails = CommonUtils.readDatafromXMLFile(type);
		InputStream input = null;

		String baseURL = userDetails.get("endpoint");
		String resource = userDetails.get("resource");
		String validID = userDetails.get("vaildId");
		XMLSerializer xmlSerializer;
		JSON json;

		input = new FileInputStream(new File(file));
		String xml = IOUtils.toString(input);
		xmlSerializer = new XMLSerializer();
		json = xmlSerializer.read(xml.toString());

		System.out.println("JSON format : " + json);

		response = CommonUtils.UPDATE(baseURL, resource, validID, json);
	}

	@Given("^I update a request for invaliddata \"(.*?)\" and \"(.*?)\"$")
	public void i_update_a_request_for_invaliddata(String type, String xmlfile)
			throws Throwable {
		file = System.getProperty("user.dir") + "\\TestData\\" + type + "\\"
				+ xmlfile + ".json";
		userDetails = CommonUtils.readDatafromXMLFile(type);
		InputStream input = null;

		String baseURL = userDetails.get("endpoint");
		String resource = userDetails.get("resource");
		String validID = getValidID(baseURL, resource, 0);

		input = new FileInputStream(new File(file));
		String xml = IOUtils.toString(input);

		response = CommonUtils.UPDATEValidate(baseURL, resource, validID, xml);
	}

}
