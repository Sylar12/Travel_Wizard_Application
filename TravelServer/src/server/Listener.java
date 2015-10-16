package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/*The listen is used to wait for a connection request from app. When it accept a connection, it create a new socket
 * to communicate with the app, which is done in controller class*/
public class Listener {
	private ServerSocket serversocket;
	
	public Listener(int port) {
		while(true) {
		try {
			serversocket = new ServerSocket(port);
			InetAddress IP=InetAddress.getLocalHost();
			System.out.println("IP of my system is := "+IP.getHostAddress());
			break;
		} catch (IOException e) {
			System.err.println("error when create serversocket");
		}
		}
		System.out.println("Created server socket on port:" + port);
	}
	
	public void listen() {
		while (true) {
			Socket socket = null;
			try {
				socket = serversocket.accept();
				System.out.println("a socket connect!");
			} catch (IOException e) {
				System.err.println("Wrong when create socket");
			}
			new Thread(new Controller(socket)).start();
			System.out.println("create a new controller");
		}
	}
	
	
	public static void main(String args[]) {
		Listener listener = new Listener(44400);
		listener.listen();
	}
}
