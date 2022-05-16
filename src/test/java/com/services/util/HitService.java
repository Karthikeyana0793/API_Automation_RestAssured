package com.services.util;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.config.ConnectionConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class HitService {

	static private RestAssuredConfig config = null;
	ConnectionConfig connectionConfig = new ConnectionConfig();
	static SoftAssert softAssert = new SoftAssert();

	static {
		RestAssured.baseURI = PropertyHandler.getProperty("base.url");
	}

	/**
	 * hit the service with out access token
	 * 
	 * @param jsonRequestBody
	 * @param endURL
	 * @return
	 */
	public static Response getJSONResponse(String jsonRequestBody, String endURL) {
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(jsonRequestBody);
		Response response = request.given().config(config).post(endURL);
		softAssert.assertEquals(response.getStatusCode(), 200, "Response is not 200");
		return response;
	}

	public static Response getJSONResponseGet(String endURL) {
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		Response response = request.given().config(config).get(endURL);
		Assert.assertEquals(response.getStatusCode(), 200, "Response is not 200");
		return response;
	}

	public static String getResponseValue(Response response, String responseParam) {
		Object responseParamValue = response.jsonPath().get(responseParam);
		Assert.assertTrue(null != responseParamValue, responseParam + " is not found");
		String temp = responseParamValue.toString();
		return temp;
	}


	/**
	 * Capturing jsonarray from response
	 * 
	 * @param response
	 * @param responseParam
	 * @return
	 */
	public static JsonArray getResponseJsonArray(Response response, String responseParam) {
		try {

			JsonObject jsonObjectResponse = Util.gsonObj.fromJson(response.asString(), JsonObject.class);
			JsonArray jsonArray = jsonObjectResponse.get(responseParam).getAsJsonArray();

			return jsonArray;
		} catch (Exception e) {
			Reporter.log(e.toString());
			return null;
		}
	}

	public static String extractValueFromJsonArray(JsonArray jsonArray, int index, String responseParam) {

		JsonObject jsonObjArrayResponse = null;
		try {
			if (null != jsonArray) {

				jsonObjArrayResponse = Util.gsonObj.fromJson(jsonArray.get(index), JsonObject.class);
			}

			return Util.gsonObj.fromJson(jsonObjArrayResponse.get(responseParam), String.class);
		} catch (Exception e) {
			Reporter.log(e.toString());
			return null;
		}
	}
}
