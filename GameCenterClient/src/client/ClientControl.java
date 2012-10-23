package client;

import client.EasyClient;

public class ClientControl {
	protected static final String ipaddress = "127.0.0.1";
	protected static final String port = "65535";
	protected static final String[] path = {"hi","help"};
	protected static int indexNum = 0;
	
	public static void main(String[] args) {
		EasyClient client = new EasyClient();
		while(indexNum < path.length){
		client.Connect("http://"+ipaddress+":"+port+"/"+path[indexNum]);
		indexNum++;
		}
	}
}
