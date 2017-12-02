package Steps;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.json.JSON;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CommonUtils {

	static HttpResponse response;
	
	@Before
	public static void Onintialize(){
		System.out.println("Running API Test");
	}

	/*
	 * GET CAllS for the request
	 */
	@Test
	public static HttpResponse GET(String BaseURL) throws Exception {

		// Getting the response
		response = createWebResourceBuilder(BaseURL);
		return response;
	}

	public static HttpResponse GET(String EndpntURI, String resource, String id)
			throws Exception {

		response = createWebResourceBuilder(EndpntURI, resource, id);
		return response;
	}

	private static HttpResponse createWebResourceBuilder(String endpnt)
			throws Exception {

		response = Request.Get(endpnt).execute().returnResponse();
		return response;
	}

	private static HttpResponse createWebResourceBuilder(String baseURI,
			String resource, String id) throws Exception {

		String endpnt = baseURI + "" + resource + "/" + id;
		response = Request.Get(endpnt).execute().returnResponse();
		return response;
	}

	public static HttpResponse POST(String baseURL, String resource, JSON json)
			throws Exception {

		response = createWebResourceBuilder(baseURL, resource, json);
		return response;

	}

	public static HttpResponse UPDATE(String baseURL, String resource,
			String validID, JSON json) throws Exception {

		response = createWebResourceBuilder(baseURL, resource, validID, json);
		return response;

	}

	public static HttpResponse UPDATEValidate(String baseURL, String resource,
			String validID, String xml) throws Exception {

		String endpnt = baseURL + "" + resource + "/" + validID;
		response = Request.Put(endpnt).addHeader("app-header", "example")
				.bodyString(xml, ContentType.APPLICATION_JSON).execute()
				.returnResponse();

		System.out.println(response);
		return response;

	}

	public static Map<String, String> readDatafromXMLFile(String type)
			throws Exception {

		Map<String, String> userdet = new HashMap<String, String>();

		File file = new File(System.getProperty("user.dir") + "\\TestData\\"
				+ type + "\\testdata.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(file);

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getChildNodes();
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				userdet.put("endpoint",
						eElement.getElementsByTagName("endpoint").item(0)
								.getTextContent().toString());
				userdet.put("resource",
						eElement.getElementsByTagName("resource").item(0)
								.getTextContent().toString());
				userdet.put("vaildId", eElement.getElementsByTagName("vaildId")
						.item(0).getTextContent().toString());
			}
		}
		return userdet;

	}

	public static HttpResponse createWebResourceBuilder(String baseURL,
			String resource, JSON bodyParameter) throws Exception {

		String endpnt = baseURL + "" + resource;
		System.out.println(bodyParameter.toString());

		response = Request
				.Post(endpnt)
				.addHeader("app-header", "example")
				.bodyString(bodyParameter.toString(),
						ContentType.APPLICATION_JSON).execute()
				.returnResponse();

		System.out.println(response);
		return response;

	}

	private static HttpResponse createWebResourceBuilder(String baseURL,
			String resource, String validID, JSON bodyParameter)
			throws Exception {

		String endpnt = baseURL + "" + resource + "/" + validID;
		response = Request
				.Put(endpnt)
				.addHeader("app-header", "example")
				.bodyString(bodyParameter.toString(),
						ContentType.APPLICATION_JSON).execute()
				.returnResponse();

		System.out.println(response);
		return response;

	}

	public static HttpResponse createWebResourceBuilder_array(String baseURL,
			String resource, String xml) throws Exception {

		String endpnt = baseURL + "" + resource;

		response = Request.Post(endpnt).addHeader("app-header", "example")
				.bodyString(xml, ContentType.APPLICATION_JSON).execute()
				.returnResponse();

		System.out.println(response);
		return response;

	}

	public static HttpResponse DELETE(String EndpntURI, String resource,
			String id) throws Exception {

		response = DeleteSong(EndpntURI, resource, id);
		return response;
	}

	private static HttpResponse DeleteSong(String baseURI, String resource,
			String id) throws Exception {

		String endpnt = baseURI + "" + resource + "/" + id;
		response = Request.Delete(endpnt).execute().returnResponse();
		System.out.println(response);
		return response;
	}

	public String getValidID(String baseURL, String resource, int arrsize)
			throws Exception {
		String endpnt = baseURL + "" + resource;
		String validID = null;

		response = CommonUtils.GET(endpnt);

		String json = EntityUtils.toString(response.getEntity(), "UTF-8");
		try {
			JSONParser parser = new JSONParser();
			Object resultObject = parser.parse(json);

			if (resultObject instanceof JSONArray) {
				JSONArray array = (JSONArray) resultObject;
				for (Object object : array) {
					JSONObject obj = (JSONObject) object;
					validID = obj.get("_id").toString();
					response = CommonUtils.GET(baseURL, resource, validID);
					json = EntityUtils.toString(response.getEntity(), "UTF-8");
					if (json.equals("No video")) {
						break;
					} else {
						resultObject = parser.parse(json);
						System.out.println(resultObject);
						JSONObject obj1 = (JSONObject) resultObject;
						resultObject = obj1.get("videos");
						if (resultObject instanceof JSONArray) {
							array = (JSONArray) resultObject;
							int arr = array.size();
							if (arr == arrsize) {
								break;
							} else if (arr > 0) {
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new ClientProtocolException("Malformed XML document", e);
		}
		return validID;
	}
	
	@After
	public void teardown(){
		System.out.println("Execution completed");
	}

}
