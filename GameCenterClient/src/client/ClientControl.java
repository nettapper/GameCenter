package client;

import client.EasyClient;
import client.NewClient;

public class ClientControl {
	protected static final String ipaddress = "127.0.0.1";
	protected static final String port = "65535";
	protected static final String[] path = {"hi","help"};
	protected static int indexNum = 0;
	protected static String data = "lol lol lol im better than you!";
	
	public static void main(String[] args) {
		NewClient client = new NewClient();
		while(indexNum < path.length){
			client.connect("http://"+ipaddress+":"+port+"/"+path[indexNum], data);
			indexNum++;
		}
	}
}
