import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
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

public class MyServer{
	SctpServerChannel sctpServerChannel;
	SctpChannel sctpChannel;
	 //ServerSocket server;
	 //Socket socket;
	 //DataInputStream input =null;
	 //DataOutputStream output =null;
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
		
		read = new MessageRead(messageBuff, sctpChannel, processNo,clock);
		read.start();
		
		write = new MessageWrite(outMessages, sctpChannel, processNo,clock);
		write.start();
		
	}
	
	
	void startServer(int portNo){
		
		try {
			sctpServerChannel = SctpServerChannel.open();
			InetSocketAddress serverAddr = new InetSocketAddress(portNo);
			sctpServerChannel.bind(serverAddr);
			//server = new ServerSocket(portNo);
			
			System.out.println("Waiting for client on port " + portNo);
			sctpChannel= sctpServerChannel.accept();
			//socket = server.accept();
			System.out.println("Accepted connection on port "+portNo);
			
			
			//input = new DataInputStream(socket.getInputStream());
	    	//output = new DataOutputStream(socket.getOutputStream());
	    	 
	    	 //output.writeUTF("First Temp Message from server after creating output stream");

	    }
	      catch (IOException e) {
	      System.out.println(e);
	    }
		
	}
	
	public void terminate(){
		/*try {
			read=null;
			write=null;
			socket.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	
}
