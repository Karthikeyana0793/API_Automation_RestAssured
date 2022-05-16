package com.services.testcases.sampleapi;

import org.apache.poi.ss.usermodel.Sheet;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.google.gson.JsonObject;
import com.services.beans.AddBookData;
import com.services.util.ExcelDataExtractor;
import com.services.util.HitService;
import com.services.util.PropertyHandler;

import io.restassured.response.Response;

public class AddBookTestDifferent {
	
	private String inputTestDataSheet = PropertyHandler.getProperty("Add_Book_Sheet");
	AddBookData addBookData = new AddBookData();
	SoftAssert softAssert = new SoftAssert();
	String endURL = "/Library/Addbook.php";
	
	@Test
	public void addBookTests() {
		
		Sheet inputSheet = ExcelDataExtractor.getSheet(inputTestDataSheet);
		int totalRows = inputSheet.getLastRowNum();
		
		for(int rowId=1; rowId<=totalRows; rowId++) {
			addBookData = ExcelDataExtractor.getAddBookData(inputSheet, rowId);
			JsonObject jsonRequest = AddBookRequestBody.buildAddBookRequestJson(addBookData);
			System.out.println(jsonRequest.toString());
			Response response = HitService.getJSONResponse(jsonRequest.toString(), endURL);
			System.out.println(response.asString());
			String msg = response.getBody().jsonPath().get("msg");
			String id = response.getBody().jsonPath().get("ID");
			softAssert.assertTrue(msg.equalsIgnoreCase("successfully added"), "Book not added successfully");
			softAssert.assertTrue(id.equalsIgnoreCase("bcd227"), "Book ID mismatched");
		}
		
	}
}
