import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.net.*;
import com.sun.nio.sctp.*;
import java.nio.*;

public class MessageRead extends Thread{
	 //DataInputStream input =null;
	SctpChannel sctpChannel;
	 HashMap<Integer, ArrayList<String>> messageBuff;
	 int processNo;
	 Integer clock;

	
	public MessageRead(HashMap<Integer, ArrayList<String>> messageBuff,SctpChannel sctpChannel,int processNo,Integer clock) {
		//System.out.println("Inside messsga read ");
		this.sctpChannel=sctpChannel;
		//this.input = input;
		this.messageBuff = messageBuff;
		this.processNo = processNo;
		this.clock = clock;
		
	}
	
	public void run(){
		try {
			String line;
			//System.out.println("Inside run out message read");
			while(true){
				//System.out.println("Thread: waiting to read message from process " + processNo);
				ByteBuffer byteBuffer = ByteBuffer.allocate(300);
				sctpChannel.receive(byteBuffer, null, null);
				line = byteToString(byteBuffer).trim();
				byteBuffer.clear();
				//line = input.readUTF();
				//System.out.println("Thread: read line '" +line+"'  from process "+ processNo);
				messageBuff.get(processNo).add(line);
//				System.out.println(" clock value before incrementing in message read "+ clock );
//				clock++;
//				System.out.println(" clock value after incrementing in message read "+ clock );
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static synchronized String byteToString(ByteBuffer byteBuffer)
	{
		byteBuffer.position(0);
		byteBuffer.limit(100);
		byte[] bufArr = new byte[byteBuffer.remaining()];
		byteBuffer.get(bufArr);
		return new String(bufArr);
	}
}
