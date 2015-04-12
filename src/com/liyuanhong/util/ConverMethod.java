package com.liyuanhong.util;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;


public class ConverMethod {
	public static int PRESS = 1;
	public static int DRAG = 2;
	public static int TOUCH = 3;
	public static int TYPE = 4;
	public static int WAIT = 5;
	
	public static String toConvert(String line){
		String str = line;
		String typeStr = "";
		String jsonString = "";
		int index = str.indexOf("|");
		
		typeStr = str.substring(0, index);
		jsonString = str.substring(index + 1, str.length());
		
		line = getConvertString(typeStr, jsonString);
		return line;
	}
	
	public static int toMap(String typeStr){		
		if(typeStr.equals("PRESS")){
			return 1;
		}else if(typeStr.equals("DRAG")){
			return 2;
		}else if(typeStr.equals("TOUCH")){
			return 3;
		}else if(typeStr.equals("TYPE")){
			return 4;
		}else if(typeStr.equals("WAIT")){
			return 5;
		}		
		return -1;
	}
	
	public static String getConvertString(String typeStr,String jsonString){
		String converString = "";
		switch(toMap(typeStr)){
		case 1 : converString = processPress(jsonString);return converString;		
		case 2 : converString = processDrug(jsonString);return converString;		
		case 3 : converString = processTouch(jsonString);return converString;		
		case 4 : converString = processType(jsonString);return converString;		
		case 5 : converString = processWait(jsonString);return converString;	
		default : return "ERROR";
		}
	}
	
	public static JSONObject  getTheJson(String jsonString){
		JSONObject json = null;
		String tempStr = jsonString.replace("(", "\"(");
		tempStr = tempStr.replace(")", ")\"");
		try {
			json = new JSONObject(tempStr);
		} catch (JSONException e) {
			e.printStackTrace();
		}	
		return json;
	}
	
	public static String processPress(String jsonString){
		String convertString = "device.press(";
		String name = "";
		String type = "";
		String theKey = "";
		JSONObject json = getTheJson(jsonString);
		for(Iterator<String> ite = json.keys();ite.hasNext();){
			theKey = ite.next();			
			try{
				if(theKey.equals("name")){
					name = json.getString("name");	
				}else if(theKey.endsWith("type")){
					type = json.getString("type");
				}
			}catch(Exception e){}
		}
		if(name.equals("HOME")){
			name = "KEYCODE_HOME";
		}else if(name.equals("MENU")){
			name = "KEYCODE_MENU";
		}else if(name.equals("SEARCH")){
			name = "KEYCODE_SEARCH";
		}
		if(type.equals("downAndUp")){
			type = "DOWN_AND_UP";
		}else if(type.equals("down")){
			type = "DOWN";
		}else if(type.equals("up")){
			type = "UP";
		}
		convertString = convertString + "\"" + name + "\"," + "\"" + type + "\")";
		return convertString;
	}
	
	public static String processDrug(String jsonString){
		String convertString = "device.drag(";
		String start = "";
		String end = "";
		float duration = 0f;
		int steps = 0;
		String theKey = "";
		JSONObject json = getTheJson(jsonString);
		for(Iterator<String> ite = json.keys();ite.hasNext();){
			theKey = ite.next();
			try{
				if(theKey.equals("start")){
					start = json.getString("start");
				}else if(theKey.equals("end")){
					end = json.getString("end");
				}else if(theKey.equals("duration")){
					duration = Float.parseFloat(json.getString("duration"));
				}else if(theKey.equals("steps")){
					steps = json.getInt("steps");
				}
			}catch(Exception e){}
		}	
		convertString = convertString + start + "," + end + "," + duration + "," + steps + ")";
		return convertString;
	}
	
	public static String processTouch(String jsonString){
		String convertString = "device.touch(";
		int x = 0;
		int y = 0;
		String type = "";
		String theKey = "";
		JSONObject json = getTheJson(jsonString);
		for(Iterator<String> ite = json.keys();ite.hasNext();){
			theKey = ite.next();
			try{
				if(theKey.equals("x")){
					x = json.getInt("x");
				}else if(theKey.equals("y")){
					y = json.getInt("y");
				}else if(theKey.equals("type")){
					type = json.getString("type");
				}
			}catch(Exception e){}
		}
		if(type.equals("downAndUp")){
			type = "DOWN_AND_UP";
		}else if(type.equals("down")){
			type = "DOWN";
		}else if(type.equals("up")){
			type = "UP";
		}
		convertString = convertString + x + "," + y + "," + "\"" + type + "\"" + ")";
		return convertString;
	}
	
	public static String processType(String jsonString){
		String convertString = "device.type(";
		String message = "";
		String theKey = "";		
		JSONObject json = getTheJson(jsonString);
		for(Iterator<String> ite = json.keys();ite.hasNext();){
			theKey = ite.next();
			try{
				if(theKey.equals("message")){
					message = json.getString("message");
				}
			}catch(Exception e){}
		}
		convertString = convertString + "\"" + message + "\"" + ")";
		return convertString;
	}
	
	public static String processWait(String jsonString){
		String convertString = "MonkeyRunner.sleep(";
		float seconds = 0f;
		String theKey = "";
		JSONObject json = getTheJson(jsonString);
		for(Iterator<String> ite = json.keys();ite.hasNext();){
			theKey = ite.next();
			try{
				if(theKey.equals("seconds")){
					seconds = Float.parseFloat(json.getString("seconds"));
				}
			}catch(Exception e){}
		}
		convertString = convertString + seconds + ")";
		return convertString;
	}
}
