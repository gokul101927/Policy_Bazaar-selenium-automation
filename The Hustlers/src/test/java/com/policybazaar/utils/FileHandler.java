package com.policybazaar.utils;

import java.io.FileInputStream;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.policybazaar.baseclass.BaseClass;

public class FileHandler extends BaseClass {
	public static String jsonPath = "JsonPath";
	
	

	
	public static String getPropData(String key) {
		Properties prop = new Properties();
		InputStream readFile = null;

		try {

			readFile = new FileInputStream(".//Test data//config.properties");
			prop.load(readFile);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (readFile != null) {
				try {
					readFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return (prop.getProperty(key));
	}

	public static String getJson(String title, String key) throws IOException, org.json.simple.parser.ParseException {
		Object obj = new JSONParser().parse(new FileReader(getPropData(jsonPath)));
		JSONObject jo1 = (JSONObject) obj;

		JSONObject insuranceObject = (JSONObject) jo1.get(title);
		String name = (String) insuranceObject.get(key);
		return name;
	}

}
