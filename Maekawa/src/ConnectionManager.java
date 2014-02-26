import java.util.ArrayList;
import java.util.HashMap;


public class ConnectionManager {
	
	Integer clock;
	int processNo;
	String[] netIps;
	int [] netPorts; 
	int totalProcess;
	HashMap<Integer, ArrayList<String>> messageBuff;
	HashMap<Integer, ArrayList<String>> outMessages;
	ArrayList<MyServer>  servers =new ArrayList<MyServer>();
	ArrayList<MyClient> clients =new ArrayList<MyClient>();
	public ConnectionManager(String[] netIps, int[]netPorts,int processNo, int totalProcess,HashMap<Integer, ArrayList<String>>  messageBuff,HashMap<Integer, ArrayList<String>>  outMessages,Integer clock) {
		//System.out.println("Inside Connection manager process No is "+ processNo);
		this.netIps = netIps;
		this.netPorts = netPorts;
		this.processNo=processNo;
		this.totalProcess=totalProcess;
		this.messageBuff=messageBuff;
		this.outMessages = outMessages;
		this.clock = clock;
		
		MyServer server;
		MyClient client;
		
		
		int cliInd = processNo-1;
		int cliINcr = totalProcess-2;
		for(int i=0;i<processNo;i++){
			client = new MyClient(netIps[i], netPorts[cliInd], messageBuff, outMessages, i,clock);
			clients.add(client);
			cliInd = cliInd + cliINcr;
			cliINcr--;
		}
		
		int srvrStrt = (  ((totalProcess)*(totalProcess-1))  -	((totalProcess-processNo)*(totalProcess-processNo-1))  )/2;
		int SrvrCount=0;
		
		for(int i=processNo+1;i<totalProcess;i++){
			System.out.println("Starting server for process "+ i + " at port "+netPorts[srvrStrt+SrvrCount]);
			server = new MyServer(netPorts[srvrStrt+SrvrCount], messageBuff, outMessages, i,clock);
			SrvrCount++;
			servers.add(server);
		}
		
	}

	
	public void terminate(){
		
		for(int i=0;i<clients.size();i++){
			clients.get(i).terminate();
		}
		
		for(int i=0;i<servers.size();i++){
			servers.get(i).terminate();
		}
		
	}
	
	
}
