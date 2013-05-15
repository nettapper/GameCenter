package client;

import com.google.gson.Gson;

public class GsonConverter {
	
	private static Gson gson = new Gson();
	
	/**
	 * Converts Object[] to a json formated String
	 * 
	 * @param array To be converted
	 * 
	 * @return String In the from of json
	 */
	public static String objectArrayToGson(Object[] array) {
		
		return gson.toJson(array);
		
	}
	
	/**
	 * Converts String[] to a json formated String
	 * 
	 * @param array String array to be converted
	 * 
	 * @return String In the from of json
	 */
	public static String stringArrayToGson(String[] array) {
		
		return gson.toJson(array);
		
	}
	
	/**
	 * Converts json formated String back to an Object[]
	 * 
	 * Note, in Java, an array in the returned Object[] will be an ArrayList
	 * 
	 * @param gsonString To be converted
	 * 
	 * @return Object[] all of type object
	 */
	public static Object[] gsonToObjectArray(String gsonString) {
		
		return gson.fromJson(gsonString, Object[].class);
		
	}
	/**
	 * Converts json formated String back to an String[]
	 * 
	 * @param gsonString To be converted
	 * 
	 * @return String[] converted back
	 */
	public static String[] gsonToStringArray(String gsonString) {
		
		return gson.fromJson(gsonString, String[].class);
		
	}
}
