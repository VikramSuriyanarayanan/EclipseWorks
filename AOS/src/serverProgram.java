import java.io.*;
import java.net.*;


public class serverProgram {

	/**
	 * SERVER program
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// Server Program
		
		ServerSocket serversock = new ServerSocket(4243);
		System.out.println("Server socket created");
		Socket sock = serversock.accept();
		System.out.println("Server is waiting");
		PrintWriter writer = new PrintWriter(sock.getOutputStream());
		writer.println("Hi from Server");
		writer.close();
		
		
	/*	//************* Reading what client has sent ***************
		InputStreamReader streamServer = new InputStreamReader(sock.getInputStream());
		BufferedReader readerServer = new BufferedReader (streamServer);
		String messageFromClients = readerServer.readLine();
		System.out.println("MESSAGE FROM CLIENT 1 : "+ messageFromClients);
		writer.close();
		readerServer.close();
		*/
	}

}
