import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class MessageRead extends Thread{
	 DataInputStream input =null;
	 HashMap<Integer, ArrayList<String>> messageBuff;
	 int processNo;
	 Integer clock;

	
	public MessageRead(HashMap<Integer, ArrayList<String>> messageBuff,DataInputStream input,int processNo,Integer clock) {
		//System.out.println("Inside messsga read ");
		this.input = input;
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
				line = input.readUTF();
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
}
