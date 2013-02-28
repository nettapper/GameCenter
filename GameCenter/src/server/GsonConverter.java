package server;

import com.google.gson.Gson;

public class GsonConverter {
	
	private static Gson GSON = new Gson();
	
	public static String objectArrayToGson(Object[] array) {
		
		return GSON.toJson(array);
		
	}
	
	public static String stringArrayToGson(String[] array) {
		
		return GSON.toJson(array);
		
	}
	
	public static Object[] gsonToObjectArray(String gson) {
		
		return GSON.fromJson(gson, Object[].class);
		
	}
	
	public static String[] gsonToStringArray(String gson) {
		
		return GSON.fromJson(gson, String[].class);
		
	}
}
