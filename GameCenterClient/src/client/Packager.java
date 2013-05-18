/**
 * Standard Form : Object Array
 * [0] = String path
 * [1] = String description
 * [2] = Object[] return type/value
 * [3] = Object[] of arguments for path
 * [4] = String userSessionID (*must be present for connection with any path other than '/genSessionID')
 */
package client;

import java.util.ArrayList;

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
		
		return toStandardForm(path, "", "", args, "");
	}
	
	/**
	 * Changes the path, arguments and userSessionID to match Standard Form (above)
	 * 
	 * @param path The path connecting to
	 * @param args The arguments Object / Object[] to the server
	 * @param userSessionID The unique user session id retrieved from the server
	 * 
	 * @return Object[] Formatted into Standard Form
	 */
	public static Object[] toStandardForm(String path, Object args, String userSessionID) {
		
		return toStandardForm(path, "", "", args, userSessionID);
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
		
		return toStandardForm(path, desc, returnVal, args, "");
	}
	
	/**
	 * Changes the path, description, method return value, and arguments to match Standard Form (above)
	 * 
	 * @param path The path connecting to
	 * @param desc The method description for the game
	 * @param returnVal The method return type for the game
	 * @param args The arguments Object / Object[] to the server
	 * @param userSessionID The unique user session ID retrieved from the server
	 * 
	 * @return Object[] Formatted into Standard Form
	 */
	public static Object[] toStandardForm(String path, String desc, Object returnVal, Object args, String userSessionID) {
		
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
	 * Gets the path from the Standard Form Object[]
	 * 
	 * @param pack Standard Form Object[]
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
	 * Gets the description from the Standard Form Object[]
	 * 
	 * @param pack Standard Form Object[]
	 * 
	 * @return String The description
	 */
	public static String getDescription(Object[] pack) {
		
		return (String) pack[1];
	}
	
	/**
	 * Gets the return value from the Standard Form Object[]
	 * 
	 * @param pack Standard Form Object[]
	 * 
	 * @return Object The return value
	 */
	public static Object getReturnValue(Object[] pack) {
		
		if(pack[2] instanceof ArrayList) {
			return ((ArrayList) pack[2]).toArray(new Object[((ArrayList) pack[2]).size()]); // Change ArrayList to Object[]
		} else {
			return pack[2];
		}
	}
	
	/**
	 * Gets the arguments from the Standard Form Object[]
	 * 
	 * @param pack Standard Form Object[]
	 * 
	 * @return Object The arguments
	 */
	public static Object getArgs(Object[] pack) {
		
		if(pack[3] instanceof ArrayList) {
			return ((ArrayList) pack[3]).toArray(new Object[((ArrayList) pack[3]).size()]); // Change ArrayList to Object[]
		} else {
			return pack[3];
		}
	}
	
	/**
	 * Gets the user session ID from the Standard Form Object[]
	 * 
	 * @param pack Standard Form Object[]
	 * 
	 * @return String The description
	 */
	public static String getUserSessionID(Object[] pack) {
		
		return (String) pack[4];
	}
}
