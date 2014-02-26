import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


public class MyClient{
	
	Socket client;
	 DataInputStream input;
	 DataOutputStream output;
	 HashMap<Integer, ArrayList<String>> messageBuff;
	 HashMap<Integer, ArrayList<String>> outMessages;
	 int processNo;
	 private volatile MessageRead read;
	 private volatile MessageWrite write;
	 Integer clock;
	 
	
	
//	public static void main(String[] args) {
//		
//		MyClient mainclass = new MyClient("localhost",8000);
//        mainclass.readWrite();
//		
//	
//	}
	
	public MyClient(String machine,int port,HashMap<Integer, ArrayList<String>>  messageBuff,HashMap<Integer, ArrayList<String>>  outMessages,int processNo,Integer clock) {
		this.messageBuff = messageBuff;
		this.outMessages =outMessages;
		this.processNo = processNo;
		connectTo(machine,port);
		this.clock = clock;
		
		read = new MessageRead(messageBuff, input, processNo,clock);
		read.start();
		
		write = new MessageWrite(outMessages, output, processNo,clock);
		write.start();
	}
	
	public void terminate(){
		try {
			read=null;
			write=null;
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void connectTo(String machine,int port){
		try {
			System.out.println("Attempting to establish a connection to machine " + machine + " on port " + port);
	    	client = new Socket(machine, port);
	    	System.out.println("Established a connection to machine " + machine + " on port " + port);
	    	 input = new DataInputStream(client.getInputStream());
	    	 output = new DataOutputStream(client.getOutputStream());
	    	 //output.writeUTF("First Temp Message from client creating output stream " + processNo);
	    }
	    catch (IOException e) {
	        System.out.println(e);
	    }
	}
	
	
}
