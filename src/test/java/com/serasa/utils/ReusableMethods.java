package com.serasa.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serasa.steps.Hooks;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class ReusableMethods {
	 
	final static Logger logger = LogManager.getLogger(ReusableMethods.class);
	private static LinkedHashMap<String, String> userParameters = new LinkedHashMap<String, String>();
	
	public static XmlPath rawToXML(ExtractableResponse<Response> response) {
		//Convert response to string
		String responseString = response.asString();
		logger.info(responseString);
		
		//Convert string to XML
		XmlPath responseXml = new XmlPath(responseString);
		return responseXml;
	}
	
	public static JsonPath rawToJson(ExtractableResponse<Response> response) {
		//Convert response to string
		String responseString = response.asString();
		logger.info("String response: " + responseString);
		Hooks.responseJson = responseString;
		//Convert string to JSON
		JsonPath responseJson = new JsonPath(responseString);
		return responseJson;
	}
	
	//utilizando biblioteca jackson para realizar converção do objeto para Json
	public static String converterObjetoParaJson(Object obj)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(df);
		try {
			String jsonBody = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
			System.out.println(jsonBody);
			return jsonBody;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String converterArrayParaJson(List<?> objs)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(df);
		try {
			String jsonBody = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(objs);
			System.out.println(jsonBody);
			return jsonBody;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}
	
    public static String replaceVariablesValues(String text) throws Throwable {
        String rx = "(\\$\\{[^}]+\\})";

        StringBuffer sb = new StringBuffer();
        Pattern p = Pattern.compile(rx);
        Matcher m = p.matcher(text);

        while (m.find()) {
            // Avoids throwing a NullPointerException in the case that you
            // Don't have a replacement defined in the map for the match
            String repString = userParameters.get(m.group(1));
            if (repString != null)
                m.appendReplacement(sb, repString);
        }
        m.appendTail(sb);
        return sb.toString();   
    }
    
    public static void tryValidating(Object expectedObject, Object actualObject) {
		try {
			assertEquals(expectedObject, actualObject);
			logger.info(expectedObject + " é igual a " + actualObject);
			Hooks.scenario.log("Resultado esperado do Gherkin: " + expectedObject + " é igual ao Response da API: " + actualObject);
		} catch (AssertionError e) {
			logger.error("####### " + expectedObject + " é diferente de " + actualObject + "#######");
			Hooks.scenario.log("ERROR ####### Resultado esperado do Gherkin: " + expectedObject + " é diferente do Response da API: " + actualObject + "####### ERROR");
			fail();
		}
	}
}
