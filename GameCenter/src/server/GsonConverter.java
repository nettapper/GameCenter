package server;

import com.google.gson.Gson;

public class GsonConverter {
	Gson gson = new Gson();
	public String StringArrayToGson(String[] str){
		String json = gson.toJson(str);
		System.out.println(json);
		return json;
	}
	public String[] GsonToStringArray(String str){
		String[] obj = gson.fromJson(str, String[].class);
		System.out.println(obj);
		return obj;
	}
}
