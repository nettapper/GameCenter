package client;

import java.util.ArrayList;

public class Pack {
	
	private String path;
	private String desc;
	
	private ArrayList<Object> args;
	private ArrayList<Object> returnValue;
	
	private String userSessionID;
	private String userGameID;
	
	public Pack(String path, String userSessionID) {
		this(path, "", new ArrayList<Object>(), new ArrayList<Object>(), userSessionID, "");
	}
	
	public Pack(String path, ArrayList<Object> args, String userSessionID) {
		this(path, "", args, new ArrayList<Object>(), userSessionID, "");
	}
	
	public Pack(String path, String desc, ArrayList<Object> args, ArrayList<Object> returnVal, String userSessionID, String userGameID) {
		this.path = path;
		this.desc = desc;
		
		this.args = args;
		this.returnValue = returnVal;
		
		this.userSessionID = userSessionID;
		this.userGameID = userGameID;
	}
	
	public void clear() {
		path = "";
		desc = "";
		
		clearArgs();
		clearReturnValue();
		
		userSessionID = "";
		userGameID = "";
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public ArrayList<Object> getArgs() {
		return args;
	}
	
	public void setArgs(ArrayList<Object> args) {
		this.args = args;
	}
	
	public Object getArgAt(int index) {
		return args.get(index);
	}
	
	public void setArgAt(int index, Object arg) {
		args.add(index, arg);
	}
	
	public ArrayList<Object> getReturnValue() {
		return returnValue;
	}
	
	public void clearArgs() {
		args.clear();
	}
	
	public void setReturnValue(Object val) {
		
		if(val instanceof ArrayList) {
			this.returnValue = (ArrayList<Object>) val;
		} else {
			this.returnValue.add(val);
		}
	}
	
	public Object getReturnValueAt(int index) {
		return returnValue.get(index);
	}
	
	public void setReturnValueAt(int index, Object val) {
		returnValue.add(index, val);
	}
	
	public void clearReturnValue() {
		returnValue.clear();
	}
	
	public String getUserSessionID() {
		return userSessionID;
	}
	
	public void setUserSessionID(String id) {
		this.userSessionID = id;
	}
	
	public String getUserGameID() {
		return userGameID;
	}
	
	public void setUserGameID(String id) {
		this.userGameID = id;
	}
	
	public String toString() {
		return userSessionID + " -  Path: " + path;
	}
}
