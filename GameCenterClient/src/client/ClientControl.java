package client;

import client.EasyClient;

public class ClientControl {
	public static void main(String[] args) {
		EasyClient client = new EasyClient();
		client.Connect("http://127.0.0.1:65535/hi");
	}
}
