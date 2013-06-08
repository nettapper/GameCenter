package client;

import com.google.gson.Gson;

public class GsonConverter {
	
	private static Gson gson = new Gson();
	
	/**
	 * Converts a Pack to a json formated String
	 * 
	 * @param array To be converted
	 * 
	 * @return String In the form of json
	 */
	public static String packToGson(Pack pack) {
		
		return gson.toJson(pack);
		
	}
	
	/**
	 * Converts json formated String back to an Pack object
	 * 
	 * Note, in Java, an array in the returned Pack will be an ArrayList
	 * 
	 * @param gsonString To be converted
	 * 
	 * @return Pack all of type object
	 */
	public static Pack gsonToPack(String gsonString) {
		
		return gson.fromJson(gsonString, Pack.class);
		
	}
}
