import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.io.*;
import java.net.*;
import com.sun.nio.sctp.*;
import java.nio.*;

public class MyClient{
	SctpChannel sctpChannel ;
	//Socket client;
	 //DataInputStream input;
	 //DataOutputStream output;
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
		
		read = new MessageRead(messageBuff, sctpChannel, processNo,clock);
		read.start();
		
		write = new MessageWrite(outMessages, sctpChannel, processNo,clock);
		write.start();
	}
	
	public void terminate(){
		/*try {
			read=null;
			write=null;
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	
	void connectTo(String machine,int port){
		try {
			System.out.println("Attempting to establish a connection to machine " + machine + " on port " + port);
			
	    	//client = new Socket(machine, port);
	    	SocketAddress socketAddress = new InetSocketAddress(machine,
					port);
	    	sctpChannel = SctpChannel.open();
	    	sctpChannel.bind(new InetSocketAddress(port+1000));
			sctpChannel.connect(socketAddress);
	    	System.out.println("Established a connection to machine " + machine + " on port " + port);
	    	// input = new DataInputStream(client.getInputStream());
	    	 //output = new DataOutputStream(client.getOutputStream());
	    	 //output.writeUTF("First Temp Message from client creating output stream " + processNo);
	    }
	    catch (IOException e) {
	        System.out.println(e);
	    }
	}
	
	
}
