package com.services.util;

import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Util {

	public static Gson gsonObj = new Gson();

	/**
	 * 
	 * @param jsonObject
	 * @param parameter
	 * @param paramValue
	 * @return
	 */
	public static JsonObject generateDataForJSONRequest(JsonObject jsonObject, String parameter, String prameterValue) {

		if(null!=prameterValue) {
			prameterValue =  prameterValue.trim();
			if(prameterValue.equalsIgnoreCase("BLANK")) {
				jsonObject.addProperty(parameter,"");
			}else if(prameterValue.equalsIgnoreCase("NO_PRAM")) {
				return jsonObject;
			}else {
				jsonObject.addProperty(parameter, PropertyHandler.getProperty(parameter));
			}
		}else {
			jsonObject.addProperty(parameter, prameterValue);
		}

		return jsonObject;
	}

	/**
	 * 
	 * @param jsonArray
	 * @param excelCellRef
	 * @param prameterValue
	 * @return
	 */
	public static JsonArray generateDataForJSONArray(JsonArray jsonArray, String excelCellRef, String prameterValue) {

		if(null!=prameterValue) {
			prameterValue =  prameterValue.trim();
			if(prameterValue.equalsIgnoreCase("BLANK")) {
				jsonArray.add("");
			}else if(prameterValue.equalsIgnoreCase("NO_PRAM") || prameterValue.equalsIgnoreCase("NO_VAL")){
				return jsonArray;
			}else {
				jsonArray.add(PropertyHandler.getProperty(excelCellRef));
			}
		}else {
			jsonArray.add(prameterValue);
		}

		return jsonArray;
	}

	/**
	 * 
	 * @return - root directory path
	 */
	public static String getRootDirectoryPath() {
		return System.getProperty("user.dir");
	}

}