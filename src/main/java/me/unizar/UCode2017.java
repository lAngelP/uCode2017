package me.unizar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import me.unizar.packet.ManagerPacket;
import me.unizar.sql.MySQLConnection;
import me.unizar.test.PHPProtocol;

public class UCode2017 {

	public static Logger LOGGER = Logger.getLogger("uCode2017");
	private static MySQLConnection SQL;

	static final boolean SSL = System.getProperty("ssl") != null;
	public static final int PORT = 8023;

	public static void main(String[] args) throws Exception {
		// Configure SSL.
		SQL = new MySQLConnection("sv20.internaldub.com", "ucode", "6866a861f78",
				"ucode2017");
		ManagerPacket.register();
		try ( 
			    ServerSocket serverSocket = new ServerSocket(PORT);
			    Socket clientSocket = serverSocket.accept();
			    PrintWriter out =
			        new PrintWriter(clientSocket.getOutputStream(), true);
			    BufferedReader in = new BufferedReader(
			        new InputStreamReader(clientSocket.getInputStream()));
				){
			     System.err.println("Client on");
			    // Initiate conversation with client
			    PHPProtocol phpP = new PHPProtocol();
			  boolean error = false;
			  char[] buf = new char[4096];

			    while (!error && (in.read(buf, 0, 4096) > 0)) {
			    	System.out.println("In");
			    	error = phpP.channelRead(out, new String(buf));
			    }
			    clientSocket.close();
			    System.out.println("Close");
		}
	}

	public static MySQLConnection getSql() {
		return SQL;
	}

	public static Logger getLogger() {
		return LOGGER;
	}
}
