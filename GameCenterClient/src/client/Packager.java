/**
 * Standard Form : Object Array
 * [0] = String path
 * [1] = String description
 * [2] = Object[] return type/value
 * [3] = Object[] of arguments for path
 */
package client;

public class Packager {
	
	/**
	 * Changes the path and arguments to match Standard Form (above)
	 * 
	 * @param path The path connecting to
	 * @param args The arguments Object / Object[] to the server
	 * 
	 * @return Object[] Formatted into Standard Form
	 */
	public static Object[] toStandardForm(String path, Object args) {
		
		return toStandardForm(path, "", "", args);
	}
	
	/**
	 * Changes the path, description, method return value, and arguments to match Standard Form (above)
	 * 
	 * @param path The path connecting to
	 * @param desc The method description for the game
	 * @param returnVal The method return type for the game
	 * @param args The arguments Object / Object[] to the server
	 * 
	 * @return Object[] Formatted into Standard Form
	 */
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
	
	/**
	 * Gets the path from the Standard From Object[]
	 * 
	 * @param pack Standard From Object[]
	 * 
	 * @return String The path
	 */
	public static String getPath(Object[] pack) {
		
		if(((String) pack[0]).substring(0, 1).equalsIgnoreCase("/")) {
			return ((String) pack[0]).substring(1);
		} else {
			return (String) pack[0];
		}
	}
	
	/**
	 * Gets the description from the Standard From Object[]
	 * 
	 * @param pack Standard From Object[]
	 * 
	 * @return String The description
	 */
	public static String getDescription(Object[] pack) {
		
		return (String) pack[1];
	}
	
	/**
	 * Gets the return value from the Standard From Object[]
	 * 
	 * @param pack Standard From Object[]
	 * 
	 * @return Object The return value
	 */
	public static Object getReturnValue(Object[] pack) {
		
		return pack[2];
	}
	
	/**
	 * Gets the arguments from the Standard From Object[]
	 * 
	 * @param pack Standard From Object[]
	 * 
	 * @return Object The arguments
	 */
	public static Object getArgs(Object[] pack) {
		
		return pack[3];
	}
}
