import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class MessageWrite extends Thread{
	
	 DataOutputStream output =null;
	 HashMap<Integer, ArrayList<String>> outMessages;
	 int processNo;
	 Integer clock;
	 
	 public MessageWrite(HashMap<Integer, ArrayList<String>>  outMessages,DataOutputStream output , int processNo,Integer clock) {
		 //System.out.println("Inside messsga write ");
		this.outMessages = outMessages;
		this.output = output;
		this.processNo = processNo;
		this.clock = clock;
	}

	 public void run(){
			try {
				//System.out.println("Inside run out message write");
				while(true){
					//System.out.println("Inside run while out message write");
					if(outMessages.get(processNo).size()>0){
						//System.out.println("Thread: Inside message write thread found message " + outMessages.get(processNo).size());
						String line = outMessages.get(processNo).get(0);
						//System.out.println("Thread: writing line '" +line +"' to output stream of process "+ processNo);
						output.writeUTF(line);
						//System.out.println("Thread: wrote line '" +line +"' to output stream of process "+ processNo);
//						System.out.println(" clock value before incrementing in message write "+ clock );
//						clock++;
//						System.out.println(" clock value after incrementing in message write "+ clock );
						outMessages.get(processNo).remove(0);
						//System.out.println("Thread: Inside message write message size after removing 0 " + outMessages.get(processNo).size());
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
