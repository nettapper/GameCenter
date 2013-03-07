package client;

public class Packager {
	
	/*
	 * Standard Form : Object Array
	 * [0] = String path
	 * [1] = String description
	 * [2] = Object return type/value
	 * [3..n] = Object arguments for path
	 */
	
	public static Object[] toStandardForm(String path, Object[] args) {
		return toStandardForm(path, "", "", args);
	}
	
	public static Object[] toStandardForm(String path, String desc, Object returnVal, Object[] args) {
		Object[] pack = new Object[3 + args.length];
		pack[0] = path;
		pack[1] = desc;
		pack[2] = returnVal;
		for(int i = 0; i < args.length; i++) {
			pack[i + 3] = args[i];
		}
		return pack;
	}
	
	public static String getPath(Object[] pack) {
		if(((String) pack[0]).substring(0, 1).equalsIgnoreCase("/")) {
			return (String) pack[0];
		} else {
			return (String) ("/" + pack[0]);
		}
	}
	
	public static String getDescription(Object[] pack) {
		return (String) pack[1];
	}
	
	public static Object getReturnValue(Object[] pack) {
		return pack[2];
	}
	
	public Object[] getArgs(Object[] pack) {
		Object[] args = new Object[pack.length - 3];
		for(int i = 3; i < pack.length; i++) {
			args[i - 3] = pack[i];
		}
		return args;
	}
}
