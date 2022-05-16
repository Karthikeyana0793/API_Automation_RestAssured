package com.services.testcases.sampleapi;

import org.apache.poi.ss.usermodel.Sheet;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.services.beans.AddBookData;
import com.services.util.ExcelDataExtractor;
import com.services.util.HitService;
import com.services.util.PropertyHandler;

import io.restassured.response.Response;

public class AddBookTest {
	
	private String inputTestDataSheet = PropertyHandler.getProperty("Add_Book_Sheet");
	AddBookData addBookDataBeans = new AddBookData();
	
	String endURL = "/Library/Addbook.php";
	
	@DataProvider(name="AddBookDataProvider")
	public Object[][] getaddBookData() throws InterruptedException{
		
		Sheet inputSheet = ExcelDataExtractor.getSheet(inputTestDataSheet);
		int totalRows = inputSheet.getLastRowNum();
		
		Object[][] addBookDataObj = new Object[totalRows][];
		
		for(int rowId=1; rowId<totalRows; rowId++) {
			addBookDataBeans = ExcelDataExtractor.getAddBookData(inputSheet, rowId);
			addBookDataObj[rowId][0] = addBookDataBeans;
		}
		return addBookDataObj;
	}
	
	
	@Test(dataProvider="AddBookDataProvider")
	public void addBookTests(AddBookData addBookData) {
		JsonObject jsonRequest = AddBookRequestBody.buildAddBookRequestJson(addBookData);
		Response response = HitService.getJSONResponse(jsonRequest.toString(), endURL);
		String msg = response.getBody().jsonPath().get("Msg");
		String id = response.getBody().jsonPath().get("ID");
		Assert.assertTrue(msg.equalsIgnoreCase("successfully added"), "Book not added successfully");
		Assert.assertTrue(id.equalsIgnoreCase("bcd227"), "Book ID mismatched");
	}
}
