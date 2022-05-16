/**
 * 
 */
package com.services.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyHandler {

	static String fileName = "config.properties";

	private static Properties properties;

	public PropertyHandler(){
	}

	public void finalize() throws Throwable {
	}

	public static String getProperty(String key){
		try{        

			InputStream is = PropertyHandler.class.getResourceAsStream("/"+fileName);
			if(is==null)
			{
				throw new FileNotFoundException("path mentioned is wrong");
			}
			properties=new Properties();
			properties.load(is);
		}catch(FileNotFoundException fnfe){

		}catch(IOException ie){

		}
		String value=properties.getProperty(key);
		if(value==null ){
			return null;
		}else{
			return value.trim();
		}
	}   

}
