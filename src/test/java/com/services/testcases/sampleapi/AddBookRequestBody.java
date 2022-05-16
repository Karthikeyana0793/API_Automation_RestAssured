package com.services.testcases.sampleapi;

import com.google.gson.JsonObject;
import com.services.beans.AddBookData;
import com.services.util.Util;

public class AddBookRequestBody {

	public static JsonObject buildAddBookRequestJson(AddBookData addBookData ) {
		
		JsonObject addBookJson =  new JsonObject();
		
		addBookJson = Util.generateDataForJSONRequest(addBookJson, "name",addBookData.getName());
		addBookJson = Util.generateDataForJSONRequest(addBookJson, "isbn",addBookData.getIsbn());
		addBookJson = Util.generateDataForJSONRequest(addBookJson, "aisle",addBookData.getAisle());
		addBookJson = Util.generateDataForJSONRequest(addBookJson, "author",addBookData.getAuthor());
		
		return addBookJson;
	}
}
