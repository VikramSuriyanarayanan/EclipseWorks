import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;

import com.sun.nio.sctp.SctpChannel;


/*
 * request - 0
 * reply - 1
 * failed - 2
 * release - 3
 * inquire - 4 
 * yield - 5
 */


public class ProcessSteps {
	Scanner reader;
	Integer clock= new Integer(0);
	int processNo;
	String[] netIps;
	int [] netPorts; 
	int totalProcess;
	HashMap<Integer, ArrayList<String>> messageBuff = new HashMap<Integer, ArrayList<String>>();
	HashMap<Integer, ArrayList<String>> outMessages = new HashMap<Integer, ArrayList<String>>();
	HashMap<Integer, ArrayList<String>> statMessges = new HashMap<Integer, ArrayList<String>>();
	int[] statuses;
	long strtTime = 0L;
	
	
	ConnectionManager manager;
	String inputFilePath;
	PriorityQueue<Request> reqQueue;
	
	int totCritReqs;
	int intReqTime;
	int critSecTime;
	int [][] quorum = null;
	Request prevReq =null;
	boolean gotfail=false;
	PriorityQueue<Request> enqProcess = new PriorityQueue<Request>();
	boolean statStrt =false;
	boolean inquireSent=false;
	
	
	int totReqSent=0;
	int totRepRec=0;
	int totRelSent=0;
	int totFailRec=0;
	int totEnqRec=0;
	int totYldSent =0;
	
	
	public ProcessSteps(String[] netIps, int[]netPorts,int processNo, int totalProcess,String inputFile) {
		try{
			//System.out.println("Inside Process Steps construct");
			this.netIps = netIps;
			this.netPorts = netPorts;
			this.processNo=processNo;
			this.totalProcess=totalProcess;
			this.inputFilePath=inputFile;
			
			
			for(int i=0;i<totalProcess;i++){
				
				messageBuff.put(new Integer(i), new ArrayList<String>());
				outMessages.put(new Integer(i), new ArrayList<String>());
				statMessges.put(new Integer(i), new ArrayList<String>());
				
			}
			messageBuff.put(new Integer(processNo), outMessages.get(processNo));
			
			manager = new ConnectionManager(netIps,netPorts,processNo,totalProcess,messageBuff,outMessages,clock); 
			
			//System.out.println("Thread sleeping for waiting connections to complete");
			Thread.currentThread().sleep(1000);
			//System.out.println("Thread wake up after sleeping");
			
			initFileRead();
			statuses = new int[totalProcess];
			for(int i=0;i<statuses.length;i++){
				statuses[i]=-1;
			}
			
			Comparator<Request> comparator = new ReqPriorityCompartor();
			reqQueue = new PriorityQueue<Request>(10, comparator);
			comparator = new ReqPriorityCompartor();
			enqProcess = new PriorityQueue<Request>(10, comparator);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void terminate(){
		manager.terminate();
	}
	
	private void initFileRead() {

		try{
			System.out.println("Looking for properties file in the path: "+inputFilePath + "\n\n\n");
				File file = new File(inputFilePath);
				reader = new Scanner(file);
				String line;
				if(reader.hasNextLine()){
					line = reader.nextLine();
					//System.out.println("First line of commands: " + line);
					if(totalProcess != (Integer.parseInt(line.substring(line.indexOf('=')+1).trim())+1 ) ){
						System.out.println("Error: input file incosistent with config file !!");
						System.exit(0);
					}
				}
				
				if(reader.hasNextLine()){
					line = reader.nextLine();
					//System.out.println("First line of commands: " + line);
					totCritReqs = Integer.parseInt(line.substring(line.indexOf('=')+1).trim());
				}
				
				if(reader.hasNextLine()){
					line = reader.nextLine();
					//System.out.println("First line of commands: " + line);
					intReqTime = Integer.parseInt(line.substring(line.indexOf('=')+1).trim());
				}
				
				
				if(reader.hasNextLine()){
					line = reader.nextLine();
					//System.out.println("First line of commands: " + line);
					critSecTime = Integer.parseInt(line.substring(line.indexOf('=')+1).trim());
				}
				
				
				
				while(reader.hasNextLine()){
					line = reader.nextLine();
					if(line.startsWith("R"+processNo)){
						
						line = line.substring(line.indexOf('=')+1).trim();
						String [] quoProcess = line.split(",");
						quorum = new int[quoProcess.length][2];
						for(int i=0;i<quoProcess.length;i++){
							quorum[i][0] = Integer.parseInt(quoProcess[i]);
							quorum[i][1] = 0;
						}
					}
				}
				
				if(quorum == null  && (processNo!=totalProcess-1)){
					System.out.println("Not able to find quorum for process"+ processNo);
					System.exit(0);
				}
				
				//printQuorumSts();
				

		}catch(FileNotFoundException e){
			System.out.println("Not able to open  properties file in the path: "+inputFilePath);
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void printQuorumSts(){
		if(quorum!=null){
			System.out.print("Quorum and  statues are-");
			for (int i = 0; i < quorum.length; i++) {
				System.out.print(quorum[i][0] + ":" + quorum[i][1]+",");
			}
			System.out.println();
		}
	}

	void recordStatMessage(String line, int pid){
		
	}
	
	
	public void checkInputMessages(){
		
		
		String line="";
		String [] tokens;
		int pid=-1;
		int clockInt =-1;
		int reqType =-1;
		for(int i=0;i<messageBuff.size()-1;i++){
			//System.out.println("checking input messages");
			while(messageBuff.get(i).size()>0){
				//printQuorumSts();
				
				pid=-1;
				clockInt =-1;
				reqType =-1;
				
				line = messageBuff.get(i).get(0);
				messageBuff.get(i).remove(0);
				
					if(line.startsWith("Stats") || statuses[i]==0)
					{
						
						statuses[i]=0;
						statMessges.get(i).add(line);
						if (line.startsWith("End")) {
							statuses[i]=1;
						}
						//System.out.println("Stat message from process " + i + " msg is : '" + line + "' ");
					}else{
					//System.out.println("Recieved message from process " + i + " msg is : '" + line + "' ");
					tokens = line.split(" ");
					
					pid = Integer.parseInt(tokens[0]);
					clockInt = Integer.parseInt(tokens[1]);
					reqType = Integer.parseInt(tokens[2]);
					
					incrClock(clockInt);
					
					switch(reqType){
					
					case 0:
							reqQueue.add(new Request(pid, clockInt));
							printQuee();
							break;
					case 1:
							//printQuorumSts();
						    for(int z=0;z<quorum.length;z++){
						    	if(quorum[z][0] ==pid){
						    		quorum[z][1] = 1;
						    		//System.out.println("Got Reply from process " + pid + " msg is " + line);	
						    	}
						    }
						    //printQuorumSts();
						    break;
					case 2:
							gotfail = true;
							break;
					case 3:
							prevReq = null;
							enqProcess.remove(new Request(pid, -1));
							//inquireSent = false;
							//gotfail=false;
							break;
					case 4:
							enqProcess.add(new Request(pid, clockInt));
					case 5:
							System.out.println("Got yield from process "+pid);
							if(prevReq!=null){
								prevReq.setResponded(false);
								reqQueue.add(prevReq);
								prevReq=null;
								inquireSent = false;
								printQuee();
							}
						    break;
						
					default:
							System.out.println("Cant understand request ' "+ line+" '");
							System.exit(0);
							break;
					}
					
					//System.out.println("Clock value set to "+ clock);
					
					switch (reqType) {
					case 1:
						System.out.println("Got Reply from "+ pid);
						totRepRec++;
						break;
						
					case 2:
						System.out.println("Got fail from "+ pid);
						totFailRec++;
						break;
						
					case 4:
						System.out.println("Got Enquire from "+pid);
						totEnqRec++;
						break;
	
					default:
						break;
					}
					//printQuorumSts();
				}
					
					
			}
		}
		
		if(prevReq==null && !reqQueue.isEmpty()){
			Request nextReq = reqQueue.remove();
			sendMsg(nextReq.getProcessNo(), 1); // sending reply
			prevReq = nextReq;
			printQuee();
		}
		
		if(!reqQueue.isEmpty()){
			
			Iterator<Request> itr = reqQueue.iterator();
			
			for(;itr.hasNext();){
				Request nextReq = itr.next();
					if(!nextReq.isResponded()){
						nextReq.setResponded(true);
						if(nextReq.getTimeStamp()<prevReq.getTimeStamp() || (  (nextReq.getTimeStamp()==prevReq.getTimeStamp())&& (nextReq.getProcessNo()<prevReq.getProcessNo()) ) ){
							if(!inquireSent){
								//System.out.println("**************************SENDING ENQUIRE*************************");
								System.out.println("Sending Enquire to process "+ prevReq.getProcessNo());
								sendMsg(prevReq.getProcessNo() , 4);//sending inquire got another request with higher priority
								inquireSent = true;
							}
						}else{
							sendMsg(nextReq.getProcessNo(), 2); // sending failed
					}
				}
			}
		}
		
		
		if(gotfail && !enqProcess.isEmpty() ){
			Request nxtEnq = enqProcess.remove();
			for(int z=0;z<quorum.length;z++){
		    	if(quorum[z][0] ==nxtEnq.getProcessNo()){
		    		quorum[z][1] = 0;
		    		System.out.println("Sending yield to process "+ nxtEnq.getProcessNo());
		    		sendMsg(nxtEnq.getProcessNo(), 5);//sending yield
		    		//printQuorumSts();
		    		break;
		    	}
		    }
			
		}
		//printQuorumSts();
	}

	
	public void printOutMessage(){
		System.out.println("Printing out put message buffer");
		for(int i=0;i<messageBuff.size();i++){
			
			ArrayList<String> list = messageBuff.get(i);
			System.out.println("Printing Output Message buffer for process "+i + " size is "+ list.size());
			for(int j=0;j<list.size();i++){
				System.out.println(list.get(j));
			}
			System.out.println();
		}
	}
	
	
	void sendMsg(int reqProNo,int reqType){
		String line ="";
		//incrClock(clock);
		
		line = processNo+" "+clock+" "+reqType;
		//System.out.println("Writing message '"+line+"' to out going messages queue of process " + reqProNo);
		ArrayList<String> list = outMessages.get(reqProNo);
		list.add(line);
		
		switch (reqType) {
		case 0:
			System.out.println("Sending request to "+reqProNo);
			totReqSent++;
			break;
			
		case 3:
			System.out.println("Sending release to "+reqProNo);
			totRelSent++;
			break;
			
		case 5:
			//System.out.println("Sending yiled to "+reqProNo);
			totYldSent++;
			break;

		default:
			break;
		}
		
		
		outMessages.put(reqProNo, list);
		//System.out.println("Added message '"+line+"' to out going messages queue of process " + reqProNo);
		//printOutMessage();
		
	}
	
	
	
	public void incrClock(int clockInt) {
		
		if(clockInt>clock){
			clock = new Integer(clockInt+1);
		}else{
			clock = new Integer(clock+1);
		}
		
		//System.out.println("Clock value set to: "+ clock);
	}
	
	
	
	
	public void strtExec(){
		
		if(processNo == totalProcess-1){
			csNode();
		}else{
			clientNode();
		}
		
	}
	
	void clientNode(){
		System.out.println("##### Starting  programm execution #####\n");
		strtTime = System.currentTimeMillis();
		for(int i=0;i<totCritReqs;i++){
			gotfail = false;
			//enqProcess = new PriorityQueue<Request>();
				checkInputMessages();
			for(int j=0;j<quorum.length;j++){
				sendMsg(quorum[j][0], 0); // sending requests
				quorum[j][1] =0;
//				try {
//					Thread.currentThread().sleep((long)(2000*Math.random()));
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
			}
			
			incrClock(clock);
			
			System.out.println("Sent all reuests for critical section request "+i);
			
			while(true){
				checkInputMessages();
				if(gotAllPerms()){
					execCritSec(i);
					break;
				}
			}
			
			for(int j=0;j<quorum.length;j++){
				sendMsg(quorum[j][0], 3); // sending releases
				gotfail=false;
				inquireSent=false;
			}
			
			try{
			//Thread.currentThread().sleep((long)(intReqTime*Math.random()));
			Thread.currentThread().sleep((long)(intReqTime));
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("Completed critical section request "+i+"\n");
			
			
			
		}
		
		
		sendStats();
		
		System.out.println("\n##### Completed Mekawa Mutual Exclusion detection algorithm succesfully #####");
		long endTime = System.currentTimeMillis();
		long time = (endTime-strtTime)/1000;
		System.out.println("\n\nTotal time taken to execute the programm  "+ time + " secs");

		
		if(processNo==0){
			int count=0;
			while(true){
				checkInputMessages();
				 count=0;
				for(int p=0;p<statuses.length-1;p++){
					 count+=statuses[p];
				}
				
				if(count==totalProcess-1){
					printStatuses();
					break;
				}
			}
		}
		
		while(true){
			checkInputMessages();
		}
		
	}
	
	void printStatuses(){
		
		System.out.println("Printing Statistics:\n");
		
		for(int i=0;i<statMessges.size()-1;i++){
			ArrayList<String> list = statMessges.get(i);
			while(list.size()>0){
				System.out.println(list.remove(0));
			}
			
			statuses[i]=-1;
		}
		
	}
	
	
	void sendStats(){
		
		System.out.println("Sending stat messages");
		ArrayList<String> list = outMessages.get(0);
		
		list.add("Stats of process "+processNo+":");
		list.add("Total REQUESTs sent:"+totReqSent);
		list.add("Total REPLYs recieved:"+totRepRec);
		list.add("Total RELEASEs sent:"+totRelSent);
		list.add("Total FAILs recieved:"+totFailRec);
		list.add("Total ENQURIEs recieved:"+totEnqRec);
		list.add("Total YIELDs sent:"+totYldSent);
		list.add("End of statistics for process "+processNo+"\n");
		
		
	}
	
	
	boolean gotAllPerms(){
		//System.out.println("Checking if got all permissions");

		if(prevReq!=null && prevReq.getProcessNo()!=processNo){
			return false;
		}
		
		//System.out.println("Previous release is done");
		for(int i=0;i<quorum.length;i++){
			//System.out.println("Permission from " +quorum[i][0] +" is "+ quorum[i][1]);
			if(quorum[i][1]!=1){
				//System.out.println("Checking permissions dint get permission from " +quorum[i][1] );
				return false;
			}
		}
		System.out.println("Got all Permissions");
		return true;
	}
	
	void execCritSec(int i){
		reqCrit(i);
		
		while(true){
			if(gotAckCsNode()){
				break;
			}
		}
	}
	
	boolean gotAckCsNode(){
		if(messageBuff.get(totalProcess-1).size()>0){
			messageBuff.get(totalProcess-1).remove(0);
			incrClock(clock);
			return true;
		}else{
			return false;
		}
	}
	
	void reqCrit(int reqNo){
		String line ="";
		incrClock(clock);
		
		System.out.println("Inside critical section");
		
		line = processNo+" "+reqNo+" "+critSecTime;
		//System.out.println("Writing message '"+line+"' to out going messages queue of process " + optVal);
		ArrayList<String> list = outMessages.get(totalProcess-1);
		list.add(line);
		
		/*System.out.println("printing buffer of process " + optVal);
		for(int i=0;i<list.size();i++){
			System.out.print(list.get(i)+" ");
		}
		System.out.println();*/
		
		//System.out.println("Added message '"+line+"' to out going messages queue of CS Node " + (totalProcess-1));
		//printOutMessage();
		
	}
	
	public class CritSec extends Thread{
		int pid=-1;
		int reqId =-1;
		int sleepTime =-1;
		
		

		String line="";
		String finalLine="";
		String [] tokens;
		boolean done = false;

		public CritSec(String line){
			this.line=line;
			
		}
		
		@SuppressWarnings("deprecation")
		public void run(){
			try
			{
				if(!done){
				
					PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("logs.txt", true)));
					//line = messageBuff.get(i).get(0);
				//	messageBuff.get(i).remove(0);
					
					tokens = line.split(" ");
					
					pid = Integer.parseInt(tokens[0]);
					reqId = Integer.parseInt(tokens[1]);
					sleepTime = Integer.parseInt(tokens[2]);
					System.out.println("Recieved message from process " + pid + " msg is : '" + line + "' ");
					
					line = pid +","+reqId+",";
					finalLine = line+" Enter "+getCurDate();
					out.println(finalLine);
					System.out.println("wrote: '"+finalLine+"' into log file");
					out.close();
					Thread.currentThread().sleep(sleepTime);
					out = new PrintWriter(new BufferedWriter(new FileWriter("logs.txt", true)));
					finalLine = line+" Leave "+getCurDate();
					out.println(finalLine);
					out.close();
					System.out.println("wrote: '"+finalLine+"' into log file");
					line = processNo +" ACK";
					ArrayList<String> list = outMessages.get(pid);
					System.out.println("Sending ack to process "+ pid + " msg is : '" + line + "' ");
					list.add(line);
					
					done=true;
					
				}
				
			}catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	void csNode(){
		while(true){
			try {
				 
	 
				File file = new File("logs.txt");
	 
				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}
				
				
				
				int pid=-1;
				int reqId =-1;
				int sleepTime =-1;
				for(int i=0;i<messageBuff.size();i++){
					//System.out.println("checking input messages");
					while(messageBuff.get(i).size()>0){
						
						Thread t = new CritSec(messageBuff.get(i).remove(0));
						t.start();
					}	
				}
						
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	String getCurDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	void printQuee(){
		System.out.print("Request quee is: ");
		if(prevReq!= null){
			System.out.print("Current req {"+prevReq.getProcessNo()+","+prevReq.getTimeStamp()+"} - ");
		}else{
			System.out.print("Current req {null,null} - ");
		}
		
		if(!reqQueue.isEmpty()){
			
			Iterator<Request> itr = reqQueue.iterator();
			System.out.print("Quee");
			for(;itr.hasNext();){
				Request nextReq = itr.next();
				System.out.print(" {"+nextReq.getProcessNo()+","+nextReq.getTimeStamp()+"}");
			}
				
		}
		
		System.out.println();
	}
			
}
