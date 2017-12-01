package Steps;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.io.IOUtils;

import cucumber.api.java.en.Given;

public class Create_POSTcalls extends CommonUtils {

	String file;
	Map<String, String> userDetails;

	/**
	 * POST call for Create Song
	 */
	@Given("^I POST a request for \"(.*?)\"$")
	public void i_POST_a_request_for(String type) throws Throwable {
		file = System.getProperty("user.dir") + "\\TestData\\" + type
				+ "\\createdata.xml";
		userDetails = CommonUtils.readDatafromXMLFile(type);

		String baseURL = userDetails.get("endpoint");
		String resource = userDetails.get("resource");

		InputStream in = new FileInputStream(new File(file));
		String xml = IOUtils.toString(in);
		XMLSerializer xmlSerializer = new XMLSerializer();
		JSON json = xmlSerializer.read(xml);

		response = CommonUtils
				.createWebResourceBuilder(baseURL, resource, json);
	}

	@Given("^I POST a request for JSON \"(.*?)\"$")
	public void i_POST_a_request_for_JSON(String type) throws Throwable {
		file = System.getProperty("user.dir") + "\\TestData\\" + type
				+ "\\createdata.json";
		userDetails = CommonUtils.readDatafromXMLFile(type);

		String baseURL = userDetails.get("endpoint");
		String resource = userDetails.get("resource");

		InputStream in = new FileInputStream(new File(file));
		String xml = IOUtils.toString(in);

		response = CommonUtils.createWebResourceBuilder_array(baseURL,
				resource, xml);
	}
}
