import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class project2 {
	int processNo;
	Scanner reader;
	String[] netIps;
	int [] netPorts; 
	int totPorts;
	int totalProcess;
	
public static void main(String args[]){

	long strtTime = System.currentTimeMillis();
	
		project2 mainClass = new project2();
		mainClass.execProg(args);
		
	long endTime = System.currentTimeMillis();
	long time = (endTime-strtTime)/1000;
	System.out.println("\n\nTotal time taken to execute the programm  "+ time + " secs");
	
	}


private void execProg(String[] args) {
		readInputs(args);
		if(args.length>0){
		ProcessSteps process = new ProcessSteps(netIps, netPorts, processNo, totalProcess, args[0]);
		
//		if(processNo != 0 && processNo !=totalProcess-1){
//			try{
//				Thread.currentThread().sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		
		process.strtExec();
		
//		try {
//			Thread.currentThread().sleep(15000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		process.terminate();
	}
}


		public void readInputs(String[] args ){
			System.out.println("Reading inputs");
		
		if(args.length<3){
			System.out.println("Please enter all arguments and execute the program again");
			return;
		}
		
		try{
			processNo = Integer.parseInt(args[2]);	
			//System.out.println("Got My process id " + processNo);
		}catch (Exception e) {
			System.out.println("Could not process id from "+ args[2]);
			return;
		}
		
		
		try{
			System.out.println("Looking for properties file in the path: "+args[1]);
				File inputFile = new File(args[1]);
				reader = new Scanner(inputFile);
				String line="";
				if(reader.hasNextLine()){
					try{
						line = reader.nextLine();
						totalProcess = Integer.parseInt(line);
						netIps = new String[totalProcess];
						totPorts = ((totalProcess)*(totalProcess-1)/2);
						netPorts = new int[totPorts];
						
						
						
					}catch (Exception e) {
						System.out.println("Could not parse int from "+ line);
						return;
					}	
				}
				
				int entriesRead=0;
				
					for(int i=0;i<totalProcess;i++) {
						if(reader.hasNextLine()){
							line = reader.nextLine();
							
							
							netIps[i] = line.trim();
							//System.out.println(netIps[i]);
							entriesRead++;
							//System.out.println("read from Properties file line "+ line+ " Entries read "+entriesRead);
						}
					}
				
					if(entriesRead != totalProcess){
						System.out.println("Enrror encountered while executing the program expected "+ totalProcess + " ips, read "+entriesRead);
						return;
					}
					
					entriesRead =0;
					
					line = reader.nextLine(); // leaving line after net ips
					
					
					
					for(int i=0;i<totPorts;i++) {
						if(reader.hasNextLine()){
							line = reader.nextLine();
							//System.out.println("read from Properties file line "+ line);
							
							netPorts[i] =Integer.parseInt(line);
							//System.out.println(netPorts[i]);
							entriesRead++;
						}
					}
					
					if(entriesRead != totPorts){
						System.out.println("Error encountered while executing the program expected "+ totPorts + " ports, read "+entriesRead);
						return;
					}
					
					System.out.println("Reading Properties file complete read " + netIps.length + " Ips " + netPorts.length + " ports");
					
		}catch(FileNotFoundException e){
			System.out.println("Not able to open  properties file in the path: "+args[0]);
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
