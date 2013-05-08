package client;

public class Packager {
	
	/*
	 * Standard Form : Object Array
	 * [0] = String path
	 * [1] = String description
	 * [2] = Object[] return type/value
	 * [3] = Object[] of arguments for path
	 */
	
	public static Object[] toStandardForm(String path, Object args) {
		return toStandardForm(path, "", "", args);
	}
	
	public static Object[] toStandardForm(String path, String desc, Object returnVal, Object args) {
		Object[] pack = new Object[4];
		
		if(path.substring(0, 1).equalsIgnoreCase("/")) {
			pack[0] = path.substring(1);
		} else {
			pack[0] = path;
		}
		
		pack[1] = desc;
		pack[2] = returnVal;
		pack[3] = args;
		return pack;
	}
	
	public static String getPath(Object[] pack) {
		if(((String) pack[0]).substring(0, 1).equalsIgnoreCase("/")) {
			return ((String) pack[0]).substring(1);
		} else {
			return (String) pack[0];
		}
	}
	
	public static String getDescription(Object[] pack) {
		return (String) pack[1];
	}
	
	public static Object getReturnValue(Object[] pack) {
		return pack[2];
	}
	
	public static Object getArgs(Object[] pack) {
		return pack[3];
	}
}
