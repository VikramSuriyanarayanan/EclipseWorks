import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class sock1 {

	/**
	 * Socket programming One			
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		// socket example Project

		Socket chatSocket = new Socket("127.0.0.1",4243); 
		System.out.println("Client socket created");
		InputStreamReader stream = new InputStreamReader(chatSocket.getInputStream());
		BufferedReader reader = new BufferedReader (stream);
		System.out.println("Client reading server messages");
		String message = reader.readLine();
		System.out.println("SERVER MESSAGE: "+message);
		reader.close();
			
		//************ Client responding to the server ***********
	/*	PrintWriter writerClient = new PrintWriter(chatSocket.getOutputStream());
		writerClient.println("Thank You , connection established - from client");
		reader.close();
		writerClient.close();
	*/	
	}

}
