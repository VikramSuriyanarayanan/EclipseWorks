import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


public class MyServer{
	 ServerSocket server;
	 Socket socket;
	 DataInputStream input =null;
	 DataOutputStream output =null;
	 HashMap<Integer, ArrayList<String>> messageBuff;
	 HashMap<Integer, ArrayList<String>> outMessages;
	 int processNo;
	 private volatile Thread read;
	 private volatile Thread write;
	 
	
	MyServer(int portNo,HashMap<Integer, ArrayList<String>>  messageBuff,HashMap<Integer, ArrayList<String>> outMessages,int processNo,Integer clock){
		this.messageBuff = messageBuff;
		this.outMessages =outMessages;
		this.processNo = processNo;
		startServer(portNo);
		
		read = new MessageRead(messageBuff, input, processNo,clock);
		read.start();
		
		write = new MessageWrite(outMessages, output, processNo,clock);
		write.start();
		
	}
	
	
	void startServer(int portNo){
		
		try {
			server = new ServerSocket(portNo);
			
			System.out.println("Waiting for client on port " + portNo);
			socket = server.accept();
			System.out.println("Accepted connection on port "+portNo + " from "+socket.getRemoteSocketAddress());
			
			
			input = new DataInputStream(socket.getInputStream());
	    	 output = new DataOutputStream(socket.getOutputStream());
	    	 
	    	 //output.writeUTF("First Temp Message from server after creating output stream");

	    }
	      catch (IOException e) {
	      System.out.println(e);
	    }
		
	}
	
	public void terminate(){
		try {
			read=null;
			write=null;
			socket.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
